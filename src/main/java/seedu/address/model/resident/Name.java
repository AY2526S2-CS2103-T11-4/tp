package seedu.address.model.resident;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Resident's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final int MAX_LENGTH = 100;
    public static final String MESSAGE_CONSTRAINTS =
            "Names must start with alphanumeric character and may only contain alphanumeric characters, spaces, hyphens"
            + ", periods and commas. It must not be blank and must not exceed " + MAX_LENGTH + " characters.";

    /*
     * The first character of the unit number must not be a whitespace (Enforced with trim()),
     * otherwise " " (a blank string) becomes a valid input.
     * Allows alphanumeric characters, spaces, and the symbols: , - .
     * Note: Forward Slash (/) not allowed
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ,-.]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = formatName(name);
    }

    /**
     * Format the name such that every first letter of every word is a capital letter.
     * No leading and trailing space.
     * Only 1 space between words.
     * @param name Name given by user via input.
     * @return Name that is being formatted correctly.
     */
    private static String formatName(String name) {
        // name should not be empty at this point!

        String[] words = name.split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        // remove leading and trailing space
        return sb.toString().trim();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        // trim first
        return test.trim().matches(VALIDATION_REGEX) && formatName(test.trim()).length() <= 100;
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;

        // Every first letter is capital (hence do not ignore case)
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
