package com.schoolarch.application.handlers;

import com.schoolarch.application.commands.CommandResult;
import com.schoolarch.application.commands.Commands.CreateBatchCommand;
import com.schoolarch.domain.aggregates.AcademicBatch;
import com.schoolarch.domain.repositories.Repositories.AcademicBatchRepository;
import com.schoolarch.domain.events.DomainEventPublisher;
import com.schoolarch.domain.events.BatchCreatedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;

@Service
public class CreateBatchCommandHandler {
    private final AcademicBatchRepository batchRepository;
    private final DomainEventPublisher eventPublisher;

    public CreateBatchCommandHandler(AcademicBatchRepository batchRepository,
                                    DomainEventPublisher eventPublisher) {
        this.batchRepository = batchRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public CommandResult<AcademicBatch> handle(CreateBatchCommand cmd) {
        AcademicBatch batch = AcademicBatch.create(cmd.year());
        AcademicBatch saved = batchRepository.save(batch);
        BatchCreatedEvent event = new BatchCreatedEvent(saved.getBatchId(), saved.getBatchYear(), Instant.now());
        eventPublisher.publish(event);
        return CommandResult.ok(saved);
    }
}
