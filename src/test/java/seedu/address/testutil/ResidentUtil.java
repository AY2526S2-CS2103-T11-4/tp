package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.resident.Resident;

/**
 * A utility class for Resident.
 */
public class ResidentUtil {

    /**
     * Returns an add command string for adding the {@code resident}.
     */
    public static String getAddCommand(Resident resident) {
        return AddCommand.COMMAND_WORD + " " + getResidentDetails(resident);
    }

    /**
     * Returns the part of command string for the given {@code resident}'s details.
     */
    public static String getResidentDetails(Resident resident) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + resident.getName().fullName + " ");
        sb.append(PREFIX_PHONE + resident.getPhone().value + " ");
        sb.append(PREFIX_UNIT_NUMBER + resident.getUnitNumber().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditResidentDescriptorDetails}'s details.
     */
    public static String getEditResidentDescriptorDetails(EditCommand.EditResidentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getUnitNumber().ifPresent(address -> sb.append(PREFIX_UNIT_NUMBER).append(address.value)
                .append(" "));
        return sb.toString();
    }
}
