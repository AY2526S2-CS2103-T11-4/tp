package seedu.address.model.resident;

/**
 * Represents a Resident's role.
 * Residents without an explicitly assigned leadership role use {@link #NONE}.
 * When roles are sorted, they are ordered by precedence as {@link #HA}, {@link #FH}, {@link #RA},
 * then {@link #NONE}.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}.
 */
public enum Role {
    NONE("NONE", 3),
    HA("House Assistant", 0),
    FH("Floor Head", 1),
    RA("Resident Assistant", 2);

    public static final String MESSAGE_CONSTRAINTS = "Role should be one of HA, FH, RA.";

    public final String role;
    private final int sortRank;

    // NOT public (since not allowed)
    private Role(String role, int sortRank) {
        this.role = role;
        this.sortRank = sortRank;
    }

    /**
     * Returns the sort rank for this role.
     */
    public int getSortRank() {
        return sortRank;
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
        return role;
    }

}
