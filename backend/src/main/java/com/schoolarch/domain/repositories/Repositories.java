package com.schoolarch.domain.repositories;

import com.schoolarch.domain.aggregates.InstructionalOffering;
import com.schoolarch.domain.aggregates.Section;
import java.util.Optional;
import java.util.UUID;

public interface Repositories {
    interface InstructionalOfferingRepository {
        Optional<InstructionalOffering> findById(UUID offeringId);
        InstructionalOffering save(InstructionalOffering offering);
    }

    interface SectionRepository {
        Optional<Section> findById(UUID sectionId);
        Section save(Section section);
    }

    interface StaffAssignmentRepository {
        StaffAssignment save(StaffAssignment assignment);
    }

    interface EligibleStaffListProjection {
        boolean isUserEligibleForSection(UUID sectionId, UUID userId);
    }

    interface AcademicBatchRepository {
        Optional<AcademicBatch> findById(UUID batchId);
        AcademicBatch save(AcademicBatch batch);
    }

    // Placeholder for StaffAssignment entity
    class StaffAssignment {
        private UUID staffAssignmentId;
        private UUID sectionId;
        private UUID userId;
        private String role;
        private String complianceStatus;

        public UUID getStaffAssignmentId() { return staffAssignmentId; }
        public void setStaffAssignmentId(UUID id) { this.staffAssignmentId = id; }
        public UUID getSectionId() { return sectionId; }
        public void setSectionId(UUID id) { this.sectionId = id; }
        public UUID getUserId() { return userId; }
        public void setUserId(UUID id) { this.userId = id; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getComplianceStatus() { return complianceStatus; }
        public void setComplianceStatus(String status) { this.complianceStatus = status; }
    }
}
