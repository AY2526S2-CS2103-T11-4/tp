package seedu.address.model.resident;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalResidents.ALICE;
import static seedu.address.testutil.TypicalResidents.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.resident.exceptions.DuplicateResidentException;
import seedu.address.model.resident.exceptions.ResidentNotFoundException;
import seedu.address.testutil.ResidentBuilder;

public class UniqueResidentListTest {

    private final UniqueResidentList uniqueResidentList = new UniqueResidentList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueResidentList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueResidentList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueResidentList.add(ALICE);
        assertTrue(uniqueResidentList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueResidentList.add(ALICE);
        Resident editedAlice = new ResidentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueResidentList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueResidentList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueResidentList.add(ALICE);
        assertThrows(DuplicateResidentException.class, () -> uniqueResidentList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueResidentList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueResidentList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(ResidentNotFoundException.class, () -> uniqueResidentList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueResidentList.add(ALICE);
        uniqueResidentList.setPerson(ALICE, ALICE);
        UniqueResidentList expectedUniqueResidentList = new UniqueResidentList();
        expectedUniqueResidentList.add(ALICE);
        assertEquals(expectedUniqueResidentList, uniqueResidentList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueResidentList.add(ALICE);
        Resident editedAlice = new ResidentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueResidentList.setPerson(ALICE, editedAlice);
        UniqueResidentList expectedUniqueResidentList = new UniqueResidentList();
        expectedUniqueResidentList.add(editedAlice);
        assertEquals(expectedUniqueResidentList, uniqueResidentList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueResidentList.add(ALICE);
        uniqueResidentList.setPerson(ALICE, BOB);
        UniqueResidentList expectedUniqueResidentList = new UniqueResidentList();
        expectedUniqueResidentList.add(BOB);
        assertEquals(expectedUniqueResidentList, uniqueResidentList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueResidentList.add(ALICE);
        uniqueResidentList.add(BOB);
        assertThrows(DuplicateResidentException.class, () -> uniqueResidentList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueResidentList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(ResidentNotFoundException.class, () -> uniqueResidentList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueResidentList.add(ALICE);
        uniqueResidentList.remove(ALICE);
        UniqueResidentList expectedUniqueResidentList = new UniqueResidentList();
        assertEquals(expectedUniqueResidentList, uniqueResidentList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueResidentList.setPersons((UniqueResidentList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueResidentList.add(ALICE);
        UniqueResidentList expectedUniqueResidentList = new UniqueResidentList();
        expectedUniqueResidentList.add(BOB);
        uniqueResidentList.setPersons(expectedUniqueResidentList);
        assertEquals(expectedUniqueResidentList, uniqueResidentList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueResidentList.setPersons((List<Resident>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueResidentList.add(ALICE);
        List<Resident> residentList = Collections.singletonList(BOB);
        uniqueResidentList.setPersons(residentList);
        UniqueResidentList expectedUniqueResidentList = new UniqueResidentList();
        expectedUniqueResidentList.add(BOB);
        assertEquals(expectedUniqueResidentList, uniqueResidentList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Resident> listWithDuplicateResidents = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateResidentException.class, () -> uniqueResidentList.setPersons(listWithDuplicateResidents));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueResidentList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueResidentList.asUnmodifiableObservableList().toString(), uniqueResidentList.toString());
    }
}
