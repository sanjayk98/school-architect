package com.schoolarch.domain.events;

import java.time.Instant;
import java.util.UUID;

public sealed interface DomainEvent permits
    SectionCreatedEvent,
    StaffAssignedEvent,
    OfferingCapacityReachedEvent,
    BatchCreatedEvent,
    RegistrationOpenedEvent,
    SiteRegisteredEvent,
    ExternalSchoolAddedEvent {
    Instant occurredAt();
}

record SectionCreatedEvent(
    UUID sectionId,
    UUID offeringId,
    UUID batchId,
    UUID siteId,
    UUID gradeId,
    String label,
    int sectionCapacity,
    String status,
    Instant occurredAt
) implements DomainEvent {}

record StaffAssignedEvent(
    UUID staffAssignmentId,
    UUID sectionId,
    UUID offeringId,
    UUID batchId,
    UUID siteId,
    UUID userId,
    String role,
    String complianceStatus,
    Instant occurredAt
) implements DomainEvent {}

record OfferingCapacityReachedEvent(
    UUID offeringId,
    int maxTotalCapacity,
    Instant occurredAt
) implements DomainEvent {}

record BatchCreatedEvent(
    UUID batchId,
    int year,
    Instant occurredAt
) implements DomainEvent {}

record RegistrationOpenedEvent(
    UUID batchId,
    Instant occurredAt
) implements DomainEvent {}

record SiteRegisteredEvent(
    UUID siteId,
    String name,
    Instant occurredAt
) implements DomainEvent {}

record ExternalSchoolAddedEvent(
    UUID externalSchoolId,
    String name,
    String city,
    String state,
    String schoolType,
    Instant occurredAt
) implements DomainEvent {}
