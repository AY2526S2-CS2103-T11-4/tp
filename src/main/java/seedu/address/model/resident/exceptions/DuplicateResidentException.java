package seedu.address.model.resident.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateResidentException extends RuntimeException {
    public DuplicateResidentException() {
        super("Operation would result in duplicate persons");
    }
}
