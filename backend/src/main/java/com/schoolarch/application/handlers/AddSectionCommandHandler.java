package com.schoolarch.application.handlers;

import com.schoolarch.application.commands.CommandResult;
import com.schoolarch.application.commands.Commands.AddSectionCommand;
import com.schoolarch.domain.aggregates.InstructionalOffering;
import com.schoolarch.domain.aggregates.Section;
import com.schoolarch.domain.repositories.Repositories.InstructionalOfferingRepository;
import com.schoolarch.domain.repositories.Repositories.SectionRepository;
import com.schoolarch.domain.events.DomainEventPublisher;
import com.schoolarch.domain.events.SectionCreatedEvent;
import com.schoolarch.domain.events.OfferingCapacityReachedEvent;
import com.schoolarch.domain.errors.DomainError;
import com.schoolarch.domain.exceptions.CapacityExceededException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Optional;

@Service
public class AddSectionCommandHandler {
    private final InstructionalOfferingRepository offeringRepository;
    private final SectionRepository sectionRepository;
    private final DomainEventPublisher eventPublisher;

    public AddSectionCommandHandler(InstructionalOfferingRepository offeringRepository,
                                   SectionRepository sectionRepository,
                                   DomainEventPublisher eventPublisher) {
        this.offeringRepository = offeringRepository;
        this.sectionRepository = sectionRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public CommandResult<Section> handle(AddSectionCommand cmd) {
        Optional<InstructionalOffering> offeringOpt = offeringRepository.findById(cmd.offeringId());
        if (offeringOpt.isEmpty()) {
            return CommandResult.fail(DomainError.UNKNOWN_OFFERING);
        }
        InstructionalOffering offering = offeringOpt.get();
        try {
            Section section = offering.addSection(cmd.label(), cmd.capacity());
            Section saved = sectionRepository.save(section);

            SectionCreatedEvent event = new SectionCreatedEvent(
                saved.getSectionId(),
                offering.getOfferingId(),
                offering.getBatchId(),
                offering.getSiteId(),
                offering.getGradeId(),
                saved.getLabel(),
                saved.getSectionCapacity(),
                saved.getStatus(),
                Instant.now()
            );
            eventPublisher.publish(event);

            if (offering.getCurrentTotalCapacity() >= offering.getMaxTotalCapacity()) {
                OfferingCapacityReachedEvent capEvent = new OfferingCapacityReachedEvent(
                    offering.getOfferingId(),
                    offering.getMaxTotalCapacity(),
                    Instant.now()
                );
                eventPublisher.publish(capEvent);
            }

            return CommandResult.ok(saved);
        } catch (CapacityExceededException ex) {
            return CommandResult.fail(DomainError.CAPACITY_FULL);
        }
    }
}
