package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.math.BigInteger;
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
        case NAME -> Comparator.comparing(resident -> resident.getName().fullName, String.CASE_INSENSITIVE_ORDER);
        case PHONE -> Comparator.comparing((Resident resident) -> new BigInteger(resident.getPhone().value))
                .thenComparing(resident -> resident.getPhone().value);
        case UNIT_NO -> (left, right) -> compareNaturally(
                left.getUnitNumber().value,
                right.getUnitNumber().value
        );
        };
    }

    /**
     * Compares strings using case-insensitive natural ordering so digit runs are sorted numerically.
     */
    private static int compareNaturally(String left, String right) {
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.length() && rightIndex < right.length()) {
            char leftChar = left.charAt(leftIndex);
            char rightChar = right.charAt(rightIndex);

            if (Character.isDigit(leftChar) && Character.isDigit(rightChar)) {
                int leftDigitEnd = findDigitRunEnd(left, leftIndex);
                int rightDigitEnd = findDigitRunEnd(right, rightIndex);

                String leftDigits = left.substring(leftIndex, leftDigitEnd);
                String rightDigits = right.substring(rightIndex, rightDigitEnd);

                int digitComparison = compareDigitRuns(leftDigits, rightDigits);
                if (digitComparison != 0) {
                    return digitComparison;
                }

                leftIndex = leftDigitEnd;
                rightIndex = rightDigitEnd;
                continue;
            }

            int charComparison = Character.compare(
                    Character.toLowerCase(leftChar),
                    Character.toLowerCase(rightChar)
            );
            if (charComparison != 0) {
                return charComparison;
            }

            leftIndex++;
            rightIndex++;
        }

        if (leftIndex < left.length()) {
            return 1;
        }
        if (rightIndex < right.length()) {
            return -1;
        }
        return left.compareToIgnoreCase(right);
    }

    private static int findDigitRunEnd(String value, int startIndex) {
        int index = startIndex;
        while (index < value.length() && Character.isDigit(value.charAt(index))) {
            index++;
        }
        return index;
    }

    private static int compareDigitRuns(String leftDigits, String rightDigits) {
        String normalizedLeft = stripLeadingZeros(leftDigits);
        String normalizedRight = stripLeadingZeros(rightDigits);

        int lengthComparison = Integer.compare(normalizedLeft.length(), normalizedRight.length());
        if (lengthComparison != 0) {
            return lengthComparison;
        }

        int valueComparison = normalizedLeft.compareTo(normalizedRight);
        if (valueComparison != 0) {
            return valueComparison;
        }

        return Integer.compare(leftDigits.length(), rightDigits.length());
    }

    private static String stripLeadingZeros(String digits) {
        int index = 0;
        while (index < digits.length() - 1 && digits.charAt(index) == '0') {
            index++;
        }
        return digits.substring(index);
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
