package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.resident.Resident;

/**
 * Adds a resident to the list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a resident to the list. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_UNIT_NUMBER + "UNIT_NUMBER "
            + "[" + PREFIX_ROLE + "ROLE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_UNIT_NUMBER + "26/1/A "
            + PREFIX_ROLE + "HA";

    public static final String MESSAGE_SUCCESS = "New resident added: %1$s";
    public static final String MESSAGE_DUPLICATE_RESIDENT = "This resident already exists in the list";
    public static final String MESSAGE_DUPLICATE_PHONE = "This phone already exists in the list";
    public static final String MESSAGE_DUPLICATE_UNITNUMBER = "This unit number already exists in the list";

    private final Resident toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Resident}
     */
    public AddCommand(Resident resident) {
        requireNonNull(resident);
        toAdd = resident;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // same name, same phone, same unitNumber (Role is not checked here)
        if (model.hasResident(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RESIDENT);
        }

        if (model.hasPhone(toAdd.getPhone())) {
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        }

        if (model.hasUnitNumber(toAdd.getUnitNumber())) {
            throw new CommandException(MESSAGE_DUPLICATE_UNITNUMBER);
        }

        model.addResident(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
