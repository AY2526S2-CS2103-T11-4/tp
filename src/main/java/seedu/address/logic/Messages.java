package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.resident.Resident;
import seedu.address.model.resident.Role;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_RESIDENT_DISPLAYED_INDEX = "The resident index provided is invalid";
    public static final String MESSAGE_RESIDENTS_LISTED_OVERVIEW = "%1$d residents listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_MIXED_FIND_SYNTAX =
            "Find command cannot mix prefixed and unprefixed search terms. "
                    + "Prefix every search term with n/, p/, u/, or r/.";

    // More for AddCommand and EditCommand (since used in both; they are placed here instead)
    public static final String MESSAGE_DUPLICATE_RESIDENT = "This resident already exists in the list";
    public static final String MESSAGE_DUPLICATE_PHONE = "A resident with this phone number already exists in the list";
    public static final String MESSAGE_DUPLICATE_UNITNUMBER =
            "A resident with this unit number already exists in the list";


    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code resident} for display to the user.
     */
    public static String format(Resident resident) {
        final StringBuilder builder = new StringBuilder();
        builder.append(resident.getName())
                .append("; Phone: ")
                .append(resident.getPhone())
                .append("; UnitNumber: ")
                .append(resident.getUnitNumber());

        // Add Role (except when Role is NONE)
        if (resident.getRole() != Role.NONE) {
            builder.append("; Role: ").append(resident.getRole());
        }
        return builder.toString();
    }

}
