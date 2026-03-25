package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_MIXED_FIND_SYNTAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.resident.Resident;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.ResidentBuilder;

/**
 * Acceptance tests for the proposed fielded {@code find} syntax.
 *
 * <p>These tests intentionally assert behavior that is not supported by the current implementation yet:
 * users should be able to search by name, phone fragment, and unit fragment using prefixes.</p>
 */
public class FieldedFindCommandTest {
    @TempDir
    public Path temporaryFolder;

    private Model model;
    private Logic logic;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();

        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_findWithNamePrefix_matchesResidentsByName() throws Exception {
        Resident targetResident = new ResidentBuilder().withName("Alex Tan").withPhone("11112222")
                .withUnitNumber("Alpha Block #01-01").build();
        Resident otherResident = new ResidentBuilder().withName("Brenda Lim").withPhone("33334444")
                .withUnitNumber("Beta Block #02-02").build();
        addResidents(targetResident, otherResident);

        assertFieldedFindSuccess(FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "Alex",
                List.of(targetResident));
    }

    @Test
    public void execute_findWithPhonePrefix_matchesResidentsByPhoneFragment() throws Exception {
        Resident targetResident = new ResidentBuilder().withName("Alex Tan").withPhone("98761234")
                .withUnitNumber("Alpha Block #01-01").build();
        Resident otherResident = new ResidentBuilder().withName("Brenda Lim").withPhone("33334444")
                .withUnitNumber("Beta Block #02-02").build();
        addResidents(targetResident, otherResident);

        assertFieldedFindSuccess(FindCommand.COMMAND_WORD + " " + PREFIX_PHONE + "9876",
                List.of(targetResident));
    }

    @Test
    public void execute_findWithUnitPrefix_matchesResidentsByUnitFragment() throws Exception {
        Resident targetResident = new ResidentBuilder().withName("Alex Tan").withPhone("11112222")
                .withUnitNumber("Block B #02-25").build();
        Resident otherResident = new ResidentBuilder().withName("Brenda Lim").withPhone("33334444")
                .withUnitNumber("Block C #03-30").build();
        addResidents(targetResident, otherResident);

        assertFieldedFindSuccess(FindCommand.COMMAND_WORD + " " + PREFIX_UNIT_NUMBER + "02-25",
                List.of(targetResident));
    }

    @Test
    public void execute_findWithMultipleFieldPrefixes_requiresAllSpecifiedFieldsToMatch() throws Exception {
        Resident targetResident = new ResidentBuilder().withName("Alex Tan").withPhone("98761234")
                .withUnitNumber("Block B #02-25").build();
        Resident nameOnlyResident = new ResidentBuilder().withName("Alex Lim").withPhone("11112222")
                .withUnitNumber("Block C #03-30").build();
        Resident phoneOnlyResident = new ResidentBuilder().withName("Brenda Lim").withPhone("98769999")
                .withUnitNumber("Block C #03-30").build();
        Resident unitOnlyResident = new ResidentBuilder().withName("Chris Goh").withPhone("33334444")
                .withUnitNumber("Block D #02-25").build();
        addResidents(targetResident, nameOnlyResident, phoneOnlyResident, unitOnlyResident);

        String commandText = FindCommand.COMMAND_WORD + " "
                + PREFIX_NAME + "Alex "
                + PREFIX_PHONE + "9876 "
                + PREFIX_UNIT_NUMBER + "02-25";

        assertFieldedFindSuccess(commandText, List.of(targetResident));
    }

    @Test
    public void execute_findWithMixedSyntax_throwsParseException() {
        Resident resident = new ResidentBuilder().withName("Bob Tan").withPhone("98761234")
                .withUnitNumber("Block B #02-25").build();
        addResidents(resident);

        List<Resident> expectedResidents = new ArrayList<>(logic.getFilteredResidentList());

        assertThrows(ParseException.class, MESSAGE_MIXED_FIND_SYNTAX, () ->
                logic.execute(FindCommand.COMMAND_WORD + " 9876 " + PREFIX_NAME + "Bob"));
        assertEquals(expectedResidents, logic.getFilteredResidentList());
    }

    private void addResidents(Resident... residents) {
        Arrays.stream(residents).forEach(model::addResident);
    }

    private void assertFieldedFindSuccess(String commandText, List<Resident> expectedResidents) throws Exception {
        CommandResult result = logic.execute(commandText);

        assertEquals(String.format(Messages.MESSAGE_RESIDENTS_LISTED_OVERVIEW, expectedResidents.size()),
                result.getFeedbackToUser());
        assertEquals(expectedResidents, logic.getFilteredResidentList());
    }
}
