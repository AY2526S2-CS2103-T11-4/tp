package seedu.address.model.resident;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Resident's unit number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUnitNumber(String)}
 */
public class UnitNumber {

    public static final String MESSAGE_CONSTRAINTS = "Unit Numbers can take any values, it should not be blank "
             + "and cannot contain the forward slash (/) character";

    /*
     * The first character of the unit number must not be a whitespace (done via trim()),
     * otherwise " " (a blank string) becomes a valid input.
     * Also, no forward slash (/) is allowed - used as valid prefix
     */
    public static final String VALIDATION_REGEX = "^[^/]+$";

    public final String value;

    /**
     * Constructs an {@code UnitNumber}.
     *
     * @param unitNumber A valid unit number.
     */
    public UnitNumber(String unitNumber) {
        requireNonNull(unitNumber);
        checkArgument(isValidUnitNumber(unitNumber), MESSAGE_CONSTRAINTS);
        // remove leading and trailing space
        value = unitNumber.trim();
    }

    /**
     * Returns true if a given string is a valid unit number.
     */
    public static boolean isValidUnitNumber(String test) {
        // test after removing leading and trailing spaces
        return test.trim().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnitNumber)) {
            return false;
        }

        UnitNumber otherUnitNumber = (UnitNumber) other;
        return value.equals(otherUnitNumber.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
