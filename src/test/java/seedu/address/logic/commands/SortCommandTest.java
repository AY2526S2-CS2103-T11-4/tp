package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.SortCommand.SortField.NAME;
import static seedu.address.logic.commands.SortCommand.SortField.PHONE;
import static seedu.address.logic.commands.SortCommand.SortField.UNIT_NO;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.resident.Resident;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.ResidentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SortCommand}.
 */
public class SortCommandTest {

    private Resident zed;
    private Resident amy;
    private Resident mike;
    private Model model;

    @BeforeEach
    public void setUp() {
        zed = new ResidentBuilder().withName("Zed Tan").withPhone("300").withUnitNumber("Gamma Block").build();
        amy = new ResidentBuilder().withName("Amy Lim").withPhone("100").withUnitNumber("Alpha Block").build();
        mike = new ResidentBuilder().withName("Mike Goh").withPhone("200").withUnitNumber("Beta Block").build();

        AddressBook addressBook = new AddressBookBuilder()
                .withPerson(zed)
                .withPerson(amy)
                .withPerson(mike)
                .build();

        model = new ModelManager(addressBook, new UserPrefs());
    }

    @Test
    public void equals() {
        SortCommand sortByName = new SortCommand(NAME);
        SortCommand sortByNameCopy = new SortCommand(NAME);
        SortCommand sortByPhone = new SortCommand(PHONE);

        assertTrue(sortByName.equals(sortByName));
        assertTrue(sortByName.equals(sortByNameCopy));
        assertFalse(sortByName.equals(1));
        assertFalse(sortByName.equals(null));
        assertFalse(sortByName.equals(sortByPhone));
    }

    @Test
    public void execute_sortByName_showsResidentsInNameOrder() throws CommandException {
        SortCommand sortCommand = new SortCommand(NAME);

        CommandResult commandResult = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertResidentOrder(model.getFilteredResidentList(), amy, mike, zed);
    }

    @Test
    public void execute_sortByPhone_showsResidentsInPhoneOrder() throws CommandException {
        SortCommand sortCommand = new SortCommand(PHONE);

        CommandResult commandResult = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertResidentOrder(model.getFilteredResidentList(), amy, mike, zed);
    }

    @Test
    public void execute_sortByBlock_showsResidentsInBlockOrder() throws CommandException {
        SortCommand sortCommand = new SortCommand(UNIT_NO);

        CommandResult commandResult = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertResidentOrder(model.getFilteredResidentList(), amy, mike, zed);
    }

    @Test
    public void execute_emptyList_returnsEmptyMessage() throws CommandException {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        SortCommand sortCommand = new SortCommand(NAME);

        CommandResult commandResult = sortCommand.execute(model);

        assertEquals(SortCommand.MESSAGE_EMPTY, commandResult.getFeedbackToUser());
        assertResidentOrder(model.getFilteredResidentList());
    }

    @Test
    public void execute_listAfterSort_resetsDisplayedOrder() throws CommandException {
        new SortCommand(NAME).execute(model);

        CommandResult commandResult = new ListCommand().execute(model);

        assertEquals(ListCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertResidentOrder(model.getFilteredResidentList(), zed, amy, mike);
    }

    private void assertResidentOrder(List<Resident> actualResidents, Resident... expectedResidents) {
        assertEquals(Arrays.asList(expectedResidents), actualResidents);
    }
}
