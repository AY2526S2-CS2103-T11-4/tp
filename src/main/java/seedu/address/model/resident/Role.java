package seedu.address.model.resident;

/**
 * Represents a Resident's role (by default all Residents are residents [hence; not explicitly stated]).
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}.
 */
public enum Role {
    NONE("None"),
    HA("House Assistant"),
    FH("Floor Head"),
    RA("Resident Assistant");

    public static final String MESSAGE_CONSTRAINTS = "Role should be one of HA, FH, RA.";

    public final String role;

    // NOT public (since not allowed)
    private Role(String role) {
        this.role = role;
    }

    /**
     * Returns true if a given role is a valid role.
     */
    public static boolean isValidRole(String role) {
        try {
            valueOf(role.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            // return false; instead of return the error message
            return false;
        }
    }

    @Override
    public String toString() {
        return this == NONE ? "resident" : role;
    }
}
