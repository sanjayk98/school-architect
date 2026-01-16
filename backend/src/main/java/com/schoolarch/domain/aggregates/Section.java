package com.schoolarch.domain.aggregates;

import java.util.UUID;

public class Section {
    private UUID sectionId;
    private UUID offeringId;
    private String label;
    private int sectionCapacity;
    private String status;

    public Section(UUID sectionId, UUID offeringId, String label, int sectionCapacity, String status) {
        this.sectionId = sectionId;
        this.offeringId = offeringId;
        this.label = label;
        this.sectionCapacity = sectionCapacity;
        this.status = status;
    }

    public UUID getSectionId() { return sectionId; }
    public UUID getOfferingId() { return offeringId; }
    public String getLabel() { return label; }
    public int getSectionCapacity() { return sectionCapacity; }
    public String getStatus() { return status; }
}
