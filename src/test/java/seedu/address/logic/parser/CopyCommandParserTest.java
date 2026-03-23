package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CopyCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CopyCommand code. The CopyCommand takes no arguments, so we test
 * that any input (including empty or with arguments) is successfully parsed.
 */
public class CopyCommandParserTest {

    private CopyCommandParser parser = new CopyCommandParser();

    @Test
    public void parse_noArgs_returnsCopyCommand() {
        assertParseSuccess(parser, "", new CopyCommand());
    }

    @Test
    public void parse_withArgs_returnsCopyCommand() {
        // CopyCommand ignores any arguments since it copies all displayed residents
        assertParseSuccess(parser, "   ", new CopyCommand());
        assertParseSuccess(parser, "1 2 3", new CopyCommand());
        assertParseSuccess(parser, "some random text", new CopyCommand());
    }
}
