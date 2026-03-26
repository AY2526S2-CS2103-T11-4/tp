package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalResidents.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.resident.Resident;
import seedu.address.testutil.ResidentBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingResidentAdded modelStub = new ModelStubAcceptingResidentAdded();
        Resident validResident = new ResidentBuilder().build();

        CommandResult commandResult = new AddCommand(validResident).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validResident)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validResident), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Resident validResident = new ResidentBuilder().build();
        AddCommand addCommand = new AddCommand(validResident);
        ModelStub modelStub = new ModelStubWithResident(validResident);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_RESIDENT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Resident alice = new ResidentBuilder().withName("Alice").build();
        Resident bob = new ResidentBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addResident(Resident resident) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasResident(Resident resident) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteResident(Resident target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setResident(Resident target, Resident editedResident) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Resident> getFilteredResidentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasListEntries() {
            return false;
        }

        @Override
        public void updateFilteredResidentsList(Predicate<Resident> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedResidentsList(Comparator<Resident> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetSortedResidentsList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single resident.
     */
    private class ModelStubWithResident extends ModelStub {
        private final Resident resident;

        ModelStubWithResident(Resident resident) {
            requireNonNull(resident);
            this.resident = resident;
        }

        @Override
        public boolean hasResident(Resident resident) {
            requireNonNull(resident);
            return this.resident.isSameResident(resident);
        }


    }

    /**
     * A Model stub that always accept the resident being added.
     */
    private class ModelStubAcceptingResidentAdded extends ModelStub {
        final ArrayList<Resident> personsAdded = new ArrayList<>();

        @Override
        public boolean hasResident(Resident resident) {
            requireNonNull(resident);
            return personsAdded.stream().anyMatch(resident::isSameResident);
        }

        @Override
        public void addResident(Resident resident) {
            requireNonNull(resident);
            personsAdded.add(resident);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
