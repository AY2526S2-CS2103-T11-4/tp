package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalResidents.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.resident.NameContainsKeywordsPredicate;
import seedu.address.model.resident.Resident;

/**
 * Contains integration tests (interaction with the Model) and unit tests for CopyCommand.
 */
public class CopyCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_unfilteredList_success() {
        CopyCommand copyCommand = new CopyCommand();

        List<Resident> residents = model.getFilteredResidentList();
        String expectedMessage = String.format(CopyCommand.MESSAGE_COPY_SUCCESS, residents.size());

        // Verify clipboard content would be generated
        CommandResult result = copyCommand.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertTrue(result.isCopy());
        assertFalse(result.getCopyContent().isEmpty());
    }

    @Test
    public void execute_filteredList_success() {
        // Create a model with all residents
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // Filter the model to show only the first resident
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Resident filteredResident = model.getFilteredResidentList().get(0);
        CopyCommand copyCommand = new CopyCommand();

        String expectedMessage = String.format(CopyCommand.MESSAGE_COPY_SUCCESS, 1);

        CommandResult result = copyCommand.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertTrue(result.isCopy());

        // Verify clipboard content contains the filtered resident
        String copyContent = result.getCopyContent();
        assertTrue(copyContent.contains(filteredResident.getName().fullName));
        assertTrue(copyContent.contains(filteredResident.getPhone().value));
        assertTrue(copyContent.contains(filteredResident.getUnitNumber().value));
    }

    @Test
    public void execute_emptyFilteredList_returnsEmptyMessage() {
        // Create an empty filtered list by applying an impossible filter
        model.updateFilteredResidentsList(new NameContainsKeywordsPredicate(List.of("NonexistentName12345")));

        CopyCommand copyCommand = new CopyCommand();
        String expectedMessage = CopyCommand.MESSAGE_COPY_EMPTY;

        CommandResult result = copyCommand.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertFalse(result.isCopy());
    }

    @Test
    public void execute_copyContentFormat() {
        // Get residents and copy
        List<Resident> residents = model.getFilteredResidentList();
        CopyCommand copyCommand = new CopyCommand();
        CommandResult result = copyCommand.execute(model);

        // Verify format of copied content
        String copyContent = result.getCopyContent();
        for (Resident resident : residents) {
            assertTrue(copyContent.contains("Name: " + resident.getName().fullName));
            assertTrue(copyContent.contains("Phone: " + resident.getPhone().value));
            assertTrue(copyContent.contains("Unit Number: " + resident.getUnitNumber().value));
        }
    }

    @Test
    public void execute_multipleResidents_copiedCorrectly() {
        // Keep full list visible
        CopyCommand copyCommand = new CopyCommand();
        CommandResult result = copyCommand.execute(model);

        // Should copy all residents in the list
        List<Resident> visibleResidents = model.getFilteredResidentList();
        String expectedMessage = String.format(CopyCommand.MESSAGE_COPY_SUCCESS, visibleResidents.size());
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify each resident's info is in the copy content
        String copyContent = result.getCopyContent();
        for (Resident resident : visibleResidents) {
            assertTrue(copyContent.contains(resident.getName().fullName),
                    "Copy content should contain resident name: " + resident.getName().fullName);
        }
    }

    @Test
    public void equals() {
        CopyCommand copyCommand1 = new CopyCommand();
        CopyCommand copyCommand2 = new CopyCommand();

        // same object -> returns true
        assertTrue(copyCommand1.equals(copyCommand1));

        // same type -> returns true (CopyCommand has no parameters, so all instances are equal)
        assertTrue(copyCommand1.equals(copyCommand2));

        // different types -> returns false
        assertFalse(copyCommand1.equals(new ListCommand()));

        // null -> returns false
        assertFalse(copyCommand1.equals(null));
    }

    @Test
    public void toStringMethod() {
        CopyCommand copyCommand = new CopyCommand();
        assertTrue(copyCommand.toString().contains("CopyCommand"));
    }
}
