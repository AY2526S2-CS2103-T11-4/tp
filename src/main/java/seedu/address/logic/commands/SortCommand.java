package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.resident.Resident;

/**
 * Sorts the displayed list of residents by the specified field.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all residents";
    public static final String MESSAGE_EMPTY = "No residents found; to add, use the ‘add’ command";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the displayed list of residents by the specified "
            + "field.\n"
            + "Parameters: FIELD (must be one of: name, phone, unit)\n"
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
        if (!model.hasListEntries()) {
            return new CommandResult(MESSAGE_EMPTY);
        }

        model.updateSortedResidentsList(getComparator());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private Comparator<Resident> getComparator() {
        return switch (sortField) {
        case NAME -> Comparator.comparing(resident -> resident.getName().fullName.toLowerCase());
        case PHONE -> Comparator.comparingLong(resident -> Long.parseLong(resident.getPhone().value));
        case UNIT_NO -> Comparator.comparing(resident -> resident.getUnitNumber().value.toLowerCase());
        };
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return sortField.equals(otherSortCommand.sortField);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("sortField", sortField)
                .toString();
    }

    /**
     * Represents the fields that the resident list can be sorted by.
     */
    public enum SortField {
        NAME,
        PHONE,
        UNIT_NO
    }
}
