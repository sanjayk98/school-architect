package com.schoolarch.application.handlers;

import com.schoolarch.application.commands.CommandResult;
import com.schoolarch.application.commands.Commands.OpenRegistrationCommand;
import com.schoolarch.domain.aggregates.AcademicBatch;
import com.schoolarch.domain.repositories.Repositories.AcademicBatchRepository;
import com.schoolarch.domain.events.DomainEventPublisher;
import com.schoolarch.domain.events.RegistrationOpenedEvent;
import com.schoolarch.domain.errors.DomainError;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;

@Service
public class OpenRegistrationCommandHandler {
    private final AcademicBatchRepository batchRepository;
    private final DomainEventPublisher eventPublisher;

    public OpenRegistrationCommandHandler(AcademicBatchRepository batchRepository,
                                         DomainEventPublisher eventPublisher) {
        this.batchRepository = batchRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public CommandResult<AcademicBatch> handle(OpenRegistrationCommand cmd) {
        AcademicBatch batch = batchRepository.findById(cmd.batchId())
            .orElseThrow(() -> new RuntimeException("Batch not found"));
        if (!batch.isFSCChecklistComplete()) {
            return CommandResult.fail(DomainError.FSC_INCOMPLETE);
        }
        batch.openRegistration();
        AcademicBatch saved = batchRepository.save(batch);
        RegistrationOpenedEvent event = new RegistrationOpenedEvent(saved.getBatchId(), Instant.now());
        eventPublisher.publish(event);
        return CommandResult.ok(saved);
    }
}
