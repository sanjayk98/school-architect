package com.schoolarch.domain.aggregates;

import java.util.UUID;

public class AcademicBatch {
    private UUID batchId;
    private int batchYear;
    private boolean fscChecklistComplete;
    private boolean registrationOpen;

    public static AcademicBatch create(int year) {
        AcademicBatch batch = new AcademicBatch();
        batch.batchId = UUID.randomUUID();
        batch.batchYear = year;
        batch.fscChecklistComplete = false;
        batch.registrationOpen = false;
        return batch;
    }

    public UUID getBatchId() { return batchId; }
    public int getBatchYear() { return batchYear; }
    public boolean isFSCChecklistComplete() { return fscChecklistComplete; }
    public void openRegistration() { this.registrationOpen = true; }
    public boolean isRegistrationOpen() { return registrationOpen; }
    
    // For demonstration
    public void setFSCChecklistComplete(boolean complete) { this.fscChecklistComplete = complete; }
}
