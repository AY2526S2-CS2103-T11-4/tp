package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CopyCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CopyCommand code. The CopyCommand takes no arguments.
 */
public class CopyCommandParserTest {

    private CopyCommandParser parser = new CopyCommandParser();

    @Test
    public void parse_noArgs_returnsCopyCommand() {
        assertParseSuccess(parser, "", new CopyCommand());
    }

    @Test
    public void parse_withArgs_throwsParseException() {
        // CopyCommand does not accept any arguments, including whitespace
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 2 3", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "some random text",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyCommand.MESSAGE_USAGE));
    }
}
