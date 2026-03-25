package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showResidentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESIDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RESIDENT;
import static seedu.address.testutil.TypicalResidents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditResidentDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.resident.Resident;
import seedu.address.testutil.EditResidentDescriptorBuilder;
import seedu.address.testutil.ResidentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Resident editedResident = new ResidentBuilder().build();
        EditCommand.EditResidentDescriptor descriptor = new EditResidentDescriptorBuilder(editedResident).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RESIDENT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESIDENT_SUCCESS,
                Messages.format(editedResident));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setResident(model.getFilteredResidentList().get(0), editedResident);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastResident = Index.fromOneBased(model.getFilteredResidentList().size());
        Resident lastResident = model.getFilteredResidentList().get(indexLastResident.getZeroBased());

        ResidentBuilder residentInList = new ResidentBuilder(lastResident);
        Resident editedResident = residentInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditResidentDescriptor descriptor = new EditResidentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditCommand editCommand = new EditCommand(indexLastResident, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESIDENT_SUCCESS,
                Messages.format(editedResident));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setResident(lastResident, editedResident);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RESIDENT, new EditResidentDescriptor());
        Resident editedResident = model.getFilteredResidentList().get(INDEX_FIRST_RESIDENT.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESIDENT_SUCCESS,
                Messages.format(editedResident));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showResidentAtIndex(model, INDEX_FIRST_RESIDENT);

        Resident residentInFilteredList = model.getFilteredResidentList().get(INDEX_FIRST_RESIDENT.getZeroBased());
        Resident editedResident = new ResidentBuilder(residentInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RESIDENT,
                new EditResidentDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESIDENT_SUCCESS,
                Messages.format(editedResident));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setResident(model.getFilteredResidentList().get(0), editedResident);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateResidentUnfilteredList_failure() {
        Resident firstResident = model.getFilteredResidentList().get(INDEX_FIRST_RESIDENT.getZeroBased());
        EditResidentDescriptor descriptor = new EditResidentDescriptorBuilder(firstResident).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_RESIDENT, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_RESIDENT);
    }

    @Test
    public void execute_duplicateResidentFilteredList_failure() {
        showResidentAtIndex(model, INDEX_FIRST_RESIDENT);

        // edit resident in filtered list into a duplicate in address book
        Resident residentInList = model.getAddressBook().getResidentList().get(INDEX_SECOND_RESIDENT.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RESIDENT,
                new EditResidentDescriptorBuilder(residentInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_RESIDENT);
    }

    @Test
    public void execute_invalidResidentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredResidentList().size() + 1);
        EditResidentDescriptor descriptor = new EditResidentDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_RESIDENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidResidentIndexFilteredList_failure() {
        showResidentAtIndex(model, INDEX_FIRST_RESIDENT);
        Index outOfBoundIndex = INDEX_SECOND_RESIDENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getResidentList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditResidentDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_RESIDENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_RESIDENT, DESC_AMY);

        // same values -> returns true
        EditResidentDescriptor copyDescriptor = new EditCommand.EditResidentDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_RESIDENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_RESIDENT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_RESIDENT, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditCommand.EditResidentDescriptor editResidentDescriptor = new EditResidentDescriptor();
        EditCommand editCommand = new EditCommand(index, editResidentDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editResidentDescriptor="
                + editResidentDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
