package com.schoolarch.domain.aggregates;

import com.schoolarch.domain.valueobjects.ValueObjects.Money;
import com.schoolarch.domain.exceptions.CapacityExceededException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InstructionalOffering {
    private UUID offeringId;
    private UUID batchId;
    private UUID siteId;
    private UUID gradeId;
    private Money fee;
    private int maxTotalCapacity;
    private final List<Section> sections = new ArrayList<>();

    public InstructionalOffering(UUID offeringId,
                                 UUID batchId,
                                 UUID siteId,
                                 UUID gradeId,
                                 Money fee,
                                 int maxTotalCapacity) {
        this.offeringId = offeringId;
        this.batchId = batchId;
        this.siteId = siteId;
        this.gradeId = gradeId;
        this.fee = fee;
        this.maxTotalCapacity = maxTotalCapacity;
    }

    public UUID getOfferingId() { return offeringId; }
    public UUID getBatchId() { return batchId; }
    public UUID getSiteId() { return siteId; }
    public UUID getGradeId() { return gradeId; }
    public int getMaxTotalCapacity() { return maxTotalCapacity; }
    public List<Section> getSections() { return sections; }

    public int getCurrentTotalCapacity() {
        return sections.stream().mapToInt(Section::getSectionCapacity).sum();
    }

    public Section addSection(String label, int capacity) {
        int newTotal = getCurrentTotalCapacity() + capacity;
        if (newTotal > maxTotalCapacity) {
            throw new CapacityExceededException(maxTotalCapacity, newTotal);
        }
        Section section = new Section(UUID.randomUUID(), offeringId, label, capacity, "PLANNED");
        sections.add(section);
        return section;
    }
}
