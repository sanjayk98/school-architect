package com.schoolarch.domain.valueobjects;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.util.UUID;

public class ValueObjects {
    public record AgeRange(int min, int max, LocalDate cutoffDate) {}

    public record Money(BigDecimal amount, String currency) {
        public Money {
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Money amount cannot be negative");
            }
        }
    }

    public record StaffAllocation(UUID userId, String role, Instant assignedDate) {}

    public record WeekDefinition(
        int weekNumber,
        String weekType,
        LocalDate rangeStart,
        LocalDate rangeEnd,
        boolean isTestWeek,
        String weekName,
        Integer termNumber,
        boolean isFinalAssessmentWeek
    ) {}
}
