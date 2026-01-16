package com.schoolarch.domain.errors;

public record DomainError(
    String code,
    String message,
    String suggestedAction
) {
    public static final DomainError USER_NOT_CLEARED =
        new DomainError("USER_NOT_CLEARED",
            "User is not cleared for this section",
            "select_different_staff");
    public static final DomainError CAPACITY_FULL =
        new DomainError("CAPACITY_FULL",
            "Section capacity is full",
            "retry_with_overflow");
    public static final DomainError FSC_INCOMPLETE =
        new DomainError("FSC_INCOMPLETE",
            "FSC checklist is incomplete",
            "complete_fsc_checklist");
    public static final DomainError UNKNOWN_SECTION =
        new DomainError("UNKNOWN_SECTION",
            "Section does not exist",
            "verify_section_id");
    public static final DomainError UNKNOWN_OFFERING =
        new DomainError("UNKNOWN_OFFERING",
            "Offering does not exist",
            "verify_offering_id");
    public static final DomainError UNKNOWN_BATCH =
        new DomainError("UNKNOWN_BATCH",
            "Batch does not exist",
            "verify_batch_id");
    public static final DomainError UNKNOWN_SITE =
        new DomainError("UNKNOWN_SITE",
            "Site does not exist",
            "verify_site_id");
}
