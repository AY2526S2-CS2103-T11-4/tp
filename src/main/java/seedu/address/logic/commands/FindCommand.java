package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.resident.Resident;

/**
 * Finds and lists all residents in the address book that match the configured find predicate.
 *
 * <p>This command supports both name-only searches and fielded searches.</p>
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds residents using prefixed search criteria "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME]... "
            + "[" + PREFIX_PHONE + "PHONE_NUMBER]... "
            + "[" + PREFIX_UNIT_NUMBER + "UNIT_NUMBER]... "
            + "[" + PREFIX_ROLE + "ROLE]...\n"
            + "Every search term must be prefixed.\n"
            + "Role can be HA, FH, RA, or unassigned.\n"
            + "Examples: " + COMMAND_WORD + " n/alex n/david\n"
            + "          " + COMMAND_WORD + " n/alex p/9876 u/02-25 r/HA\n"
            + "          " + COMMAND_WORD + " r/unassigned";

    private final Predicate<Resident> predicate;

    public FindCommand(Predicate<Resident> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredResidentsList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_RESIDENTS_LISTED_OVERVIEW, model.getFilteredResidentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
