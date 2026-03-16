package seedu.address.model.resident;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Resident's unit number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUnitNumber(String)}
 */
public class UnitNumber {

    public static final String MESSAGE_CONSTRAINTS = "Unit Numbers can take any values, and it should not be blank";

    /*
     * The first character of the unit number must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code UnitNumber}.
     *
     * @param address A valid unit number.
     */
    public UnitNumber(String address) {
        requireNonNull(address);
        checkArgument(isValidUnitNumber(address), MESSAGE_CONSTRAINTS);
        value = address;
    }

    /**
     * Returns true if a given string is a valid unit number.
     */
    public static boolean isValidUnitNumber(String test) {
        return test.matches(VALIDATION_REGEX);
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
