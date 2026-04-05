package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    @Override
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        switch (trimmedArgs.toLowerCase()) {
        case "name":
            return new SortCommand(SortCommand.SortField.NAME);
        case "phone":
            return new SortCommand(SortCommand.SortField.PHONE);
        case "unit":
            return new SortCommand(SortCommand.SortField.UNIT_NO);
        case "role":
            return new SortCommand(SortCommand.SortField.ROLE);
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)
            );

        }


    }
}
