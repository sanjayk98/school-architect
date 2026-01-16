package com.schoolarch.application.commands;

import com.schoolarch.domain.valueobjects.ValueObjects.WeekDefinition;
import java.util.UUID;
import java.math.BigDecimal;
import java.util.List;

public class Commands {
    public record ConfigureOfferingCommand(
        UUID batchId,
        UUID siteId,
        UUID gradeId,
        BigDecimal feeAmount,
        String feeCurrency,
        int totalCapacity
    ) {}

    public record AddSectionCommand(
        UUID offeringId,
        String label,
        int capacity
    ) {}

    public record AssignStaffCommand(
        UUID sectionId,
        UUID userId,
        String role,
        String complianceStatus
    ) {}

    public record CreateBatchCommand(int year) {}
    public record DefineCalendarCommand(UUID batchId, List<WeekDefinition> weeks) {}
    public record MarkFSCPhaseCompleteCommand(UUID batchId, String phase) {}
    public record OpenRegistrationCommand(UUID batchId) {}

    public record RegisterSiteCommand(
        String name,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String postalCode,
        String country,
        boolean isOnline,
        String paymentConfigJson
    ) {}

    public record SetOnlineStatusCommand(UUID siteId, boolean isOnline) {}

    public record AddExternalSchoolCommand(
        String name,
        String city,
        String state,
        String schoolType
    ) {}
}
