package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MIXED_FIND_SYNTAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.resident.NameContainsKeywordsPredicate;
import seedu.address.model.resident.ResidentMatchesFindPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(" " + trimmedArgs, PREFIX_NAME, PREFIX_PHONE, PREFIX_UNIT_NUMBER);
        boolean hasFieldPrefixes = areAnyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_UNIT_NUMBER);

        if (hasFieldPrefixes && !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MESSAGE_MIXED_FIND_SYNTAX);
        }

        if (hasFieldPrefixes) {
            return parseFieldedFind(argMultimap);
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    private FindCommand parseFieldedFind(ArgumentMultimap argMultimap) throws ParseException {
        List<String> nameKeywords = parseNameKeywords(argMultimap.getAllValues(PREFIX_NAME));
        List<String> phoneKeywords = parseFieldKeywords(argMultimap.getAllValues(PREFIX_PHONE));
        List<String> unitKeywords = parseFieldKeywords(argMultimap.getAllValues(PREFIX_UNIT_NUMBER));

        if (nameKeywords.isEmpty() && phoneKeywords.isEmpty() && unitKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new ResidentMatchesFindPredicate(nameKeywords, phoneKeywords, unitKeywords));
    }

    private List<String> parseNameKeywords(List<String> values) throws ParseException {
        List<String> keywords = new ArrayList<>();
        for (String value : values) {
            String trimmedValue = value.trim();
            if (trimmedValue.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            keywords.addAll(Arrays.asList(trimmedValue.split("\\s+")));
        }
        return keywords;
    }

    private List<String> parseFieldKeywords(List<String> values) throws ParseException {
        List<String> keywords = new ArrayList<>();
        for (String value : values) {
            String trimmedValue = value.trim();
            if (trimmedValue.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            keywords.add(trimmedValue);
        }
        return keywords;
    }

    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
