package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.resident.Resident;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the residents list.
     * This list will not contain any duplicate residents.
     */
    ObservableList<Resident> getResidentList();

}
