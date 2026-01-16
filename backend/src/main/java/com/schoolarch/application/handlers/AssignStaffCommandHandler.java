package com.schoolarch.application.handlers;

import com.schoolarch.application.commands.CommandResult;
import com.schoolarch.application.commands.Commands.AssignStaffCommand;
import com.schoolarch.domain.aggregates.InstructionalOffering;
import com.schoolarch.domain.aggregates.Section;
import com.schoolarch.domain.repositories.Repositories.InstructionalOfferingRepository;
import com.schoolarch.domain.repositories.Repositories.SectionRepository;
import com.schoolarch.domain.repositories.Repositories.StaffAssignmentRepository;
import com.schoolarch.domain.repositories.Repositories.StaffAssignment;
import com.schoolarch.domain.repositories.Repositories.EligibleStaffListProjection;
import com.schoolarch.domain.events.DomainEventPublisher;
import com.schoolarch.domain.events.StaffAssignedEvent;
import com.schoolarch.domain.errors.DomainError;
import com.schoolarch.domain.exceptions.SafetyViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssignStaffCommandHandler {
    private final SectionRepository sectionRepository;
    private final InstructionalOfferingRepository offeringRepository;
    private final StaffAssignmentRepository staffAssignmentRepository;
    private final EligibleStaffListProjection eligibleStaffList;
    private final DomainEventPublisher eventPublisher;

    public AssignStaffCommandHandler(SectionRepository sectionRepository,
                                    InstructionalOfferingRepository offeringRepository,
                                    StaffAssignmentRepository staffAssignmentRepository,
                                    EligibleStaffListProjection eligibleStaffList,
                                    DomainEventPublisher eventPublisher) {
        this.sectionRepository = sectionRepository;
        this.offeringRepository = offeringRepository;
        this.staffAssignmentRepository = staffAssignmentRepository;
        this.eligibleStaffList = eligibleStaffList;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public CommandResult<StaffAssignment> handle(AssignStaffCommand cmd) {
        Optional<Section> sectionOpt = sectionRepository.findById(cmd.sectionId());
        if (sectionOpt.isEmpty()) {
            return CommandResult.fail(DomainError.UNKNOWN_SECTION);
        }
        Section section = sectionOpt.get();
        Optional<InstructionalOffering> offeringOpt = offeringRepository.findById(section.getOfferingId());
        if (offeringOpt.isEmpty()) {
            return CommandResult.fail(DomainError.UNKNOWN_OFFERING);
        }
        InstructionalOffering offering = offeringOpt.get();

        boolean eligible = eligibleStaffList.isUserEligibleForSection(cmd.sectionId(), cmd.userId());
        if (!eligible) {
            throw new SafetyViolationException(DomainError.USER_NOT_CLEARED.code(), DomainError.USER_NOT_CLEARED.message());
        }

        if ("VOLUNTEER".equalsIgnoreCase(cmd.role()) && !"CLEARED".equalsIgnoreCase(cmd.complianceStatus())) {
            throw new SafetyViolationException(DomainError.USER_NOT_CLEARED.code(), "Volunteer not cleared");
        }

        StaffAssignment assignment = new StaffAssignment();
        assignment.setStaffAssignmentId(UUID.randomUUID());
        assignment.setSectionId(cmd.sectionId());
        assignment.setUserId(cmd.userId());
        assignment.setRole(cmd.role());
        assignment.setComplianceStatus(cmd.complianceStatus());
        StaffAssignment saved = staffAssignmentRepository.save(assignment);

        StaffAssignedEvent event = new StaffAssignedEvent(
            saved.getStaffAssignmentId(),
            saved.getSectionId(),
            offering.getOfferingId(),
            offering.getBatchId(),
            offering.getSiteId(),
            saved.getUserId(),
            saved.getRole(),
            saved.getComplianceStatus(),
            Instant.now()
        );
        eventPublisher.publish(event);

        return CommandResult.ok(saved);
    }
}
