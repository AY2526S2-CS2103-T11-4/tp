package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Sorts the displayed list of residents by the specified field.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all residents";
    public static final String MESSAGE_EMPTY = "No residents found; to add, use the ‘add’ command";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Sort command not implemented yet";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the displayed list of residents by the specified "
            + "field.\n"
            + "Parameters: FIELD (must be one of: name, phone, block)\n"
            + "Example: " + COMMAND_WORD + " name";
    private final SortField sortField;
    /**
     * Creates a SortCommand to sort residents by the specified field.
     */
    public SortCommand(SortField sortField) {
        this.sortField = requireNonNull(sortField);

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_NOT_IMPLEMENTED_YET);
    }

    /**
     * Represents the fields that the resident list can be sorted by.
     */
    public enum SortField {
        NAME,
        PHONE,
        BLOCK
    }
}
