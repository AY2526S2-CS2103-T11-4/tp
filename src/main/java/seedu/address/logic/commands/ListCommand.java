package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESIDENTS;

import seedu.address.model.Model;

/**
 * Lists all residents in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all residents";
    public static final String MESSAGE_EMPTY = "No residents found; to add, use the ‘add’ command";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredResidentsList(PREDICATE_SHOW_ALL_RESIDENTS);
        model.resetSortedResidentsList();

        if (!model.hasListEntries()) {
            return new CommandResult(MESSAGE_EMPTY);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
