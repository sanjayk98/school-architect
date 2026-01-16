package com.schoolarch.application.handlers;

import com.schoolarch.application.commands.CommandResult;
import com.schoolarch.application.commands.Commands.ConfigureOfferingCommand;
import com.schoolarch.domain.aggregates.InstructionalOffering;
import com.schoolarch.domain.repositories.Repositories.InstructionalOfferingRepository;
import com.schoolarch.domain.valueobjects.ValueObjects.Money;
import com.schoolarch.domain.events.DomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class ConfigureOfferingCommandHandler {
    private final InstructionalOfferingRepository offeringRepository;
    private final DomainEventPublisher eventPublisher;

    public ConfigureOfferingCommandHandler(InstructionalOfferingRepository offeringRepository,
                                          DomainEventPublisher eventPublisher) {
        this.offeringRepository = offeringRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public CommandResult<InstructionalOffering> handle(ConfigureOfferingCommand cmd) {
        InstructionalOffering offering = new InstructionalOffering(
            UUID.randomUUID(),
            cmd.batchId(),
            cmd.siteId(),
            cmd.gradeId(),
            new Money(cmd.feeAmount(), cmd.feeCurrency()),
            cmd.totalCapacity()
        );
        InstructionalOffering saved = offeringRepository.save(offering);
        return CommandResult.ok(saved);
    }
}
