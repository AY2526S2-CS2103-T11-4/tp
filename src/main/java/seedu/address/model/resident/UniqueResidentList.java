package seedu.address.model.resident;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.resident.exceptions.DuplicateResidentException;
import seedu.address.model.resident.exceptions.ResidentNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Resident#isSamePerson(Resident)
 */
public class UniqueResidentList implements Iterable<Resident> {

    private final ObservableList<Resident> internalList = FXCollections.observableArrayList();
    private final ObservableList<Resident> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Resident toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Resident toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateResidentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Resident target, Resident editedResident) {
        requireAllNonNull(target, editedResident);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ResidentNotFoundException();
        }

        if (!target.isSamePerson(editedResident) && contains(editedResident)) {
            throw new DuplicateResidentException();
        }

        internalList.set(index, editedResident);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Resident toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ResidentNotFoundException();
        }
    }

    public void setPersons(UniqueResidentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Resident> residents) {
        requireAllNonNull(residents);
        if (!personsAreUnique(residents)) {
            throw new DuplicateResidentException();
        }

        internalList.setAll(residents);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Resident> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Resident> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueResidentList)) {
            return false;
        }

        UniqueResidentList otherUniqueResidentList = (UniqueResidentList) other;
        return internalList.equals(otherUniqueResidentList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Resident> residents) {
        for (int i = 0; i < residents.size() - 1; i++) {
            for (int j = i + 1; j < residents.size(); j++) {
                if (residents.get(i).isSamePerson(residents.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
