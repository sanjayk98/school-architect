package com.schoolarch.domain.events;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
