package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.resident.Resident;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords or matches the specified fielded criteria and displays them as a list "
            + "with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]... or "
            + "[" + CliSyntax.PREFIX_NAME + "NAME_KEYWORDS]... "
            + "[" + CliSyntax.PREFIX_PHONE + "PHONE_FRAGMENT]... "
            + "[" + CliSyntax.PREFIX_UNIT_NUMBER + "UNIT_FRAGMENT]...\n"
            + "Examples: " + COMMAND_WORD + " alice bob charlie\n"
            + "          " + COMMAND_WORD + " n/alice bob p/9876 u/02-25";

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
