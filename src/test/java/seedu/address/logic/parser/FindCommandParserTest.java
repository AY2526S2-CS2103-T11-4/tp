package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MIXED_FIND_SYNTAX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.resident.ResidentMatchesFindPredicate;
import seedu.address.model.resident.Role;

public class FindCommandParserTest {
    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_legacyNameOnlyArgs_throwsParseException() {
        assertParseFailure(parser, "Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validFieldedArgs_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(new ResidentMatchesFindPredicate(
                Arrays.asList("Alice", "Bob"), Arrays.asList("9876"), Arrays.asList("02-25")));

        assertParseSuccess(parser, "n/Alice n/Bob p/9876 u/02-25", expectedFindCommand);
    }

    @Test
    public void parse_mixedPrefixedAndUnprefixedArgs_throwsParseException() {
        assertParseFailure(parser, "9876 n/Bob", MESSAGE_MIXED_FIND_SYNTAX);
    }

    @Test
    public void parse_unprefixedSearchTermWithinField_throwsParseException() {
        assertParseFailure(parser, "n/Alice Bob p/9876",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, "x/Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyNameField_throwsParseException() {
        assertParseFailure(parser, "n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPhoneField_throwsParseException() {
        assertParseFailure(parser, "p/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyUnitField_throwsParseException() {
        assertParseFailure(parser, "u/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyRoleField_throwsParseException() {
        assertParseFailure(parser, "r/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRoleField_throwsParseException() {
        assertParseFailure(parser, "r/INVALID-ROLE", Role.MESSAGE_CONSTRAINTS);
    }

}
