package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MIXED_FIND_SYNTAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.resident.ResidentMatchesFindPredicate;
import seedu.address.model.resident.Role;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final Prefix[] SUPPORTED_PREFIXES =
            new Prefix[] {PREFIX_NAME, PREFIX_PHONE, PREFIX_UNIT_NUMBER, PREFIX_ROLE};


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

        String[] tokens = trimmedArgs.split("\\s+");
        if (!containsRecognizedPrefix(tokens, 0)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return parseFieldedFind(tokens);
    }

    private FindCommand parseFieldedFind(String[] tokens) throws ParseException {
        List<String> nameKeywords = new ArrayList<>();
        List<String> phoneKeywords = new ArrayList<>();
        List<String> unitKeywords = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        boolean hasSeenPrefixedToken = false;

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            Optional<Prefix> matchedPrefix = findMatchingPrefix(token);
            if (matchedPrefix.isEmpty()) {
                if (!hasSeenPrefixedToken && !looksLikePrefixedToken(token) && containsRecognizedPrefix(tokens, i + 1)) {
                    throw new ParseException(MESSAGE_MIXED_FIND_SYNTAX);
                }
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

            hasSeenPrefixedToken = true;
            String value = token.substring(matchedPrefix.get().getPrefix().length()).trim();
            if (value.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

            addParsedValue(matchedPrefix.get(), value, nameKeywords, phoneKeywords, unitKeywords, roles);
        }

        return new FindCommand(new ResidentMatchesFindPredicate(nameKeywords, phoneKeywords, unitKeywords, roles));
    }

    private void addParsedValue(Prefix prefix, String value, List<String> nameKeywords,
                                List<String> phoneKeywords, List<String> unitKeywords,
                                List<Role> roles) throws ParseException {
        if (prefix.equals(PREFIX_NAME)) {
            nameKeywords.add(value);
            return;
        }

        if (prefix.equals(PREFIX_PHONE)) {
            phoneKeywords.add(value);
            return;
        }

        if (prefix.equals(PREFIX_UNIT_NUMBER)) {
            unitKeywords.add(value);
            return;
        }

        roles.add(ParserUtil.parseRole(value));
    }

    private Optional<Prefix> findMatchingPrefix(String token) {
        return Arrays.stream(SUPPORTED_PREFIXES)
                .filter(prefix -> token.startsWith(prefix.getPrefix()))
                .findFirst();
    }

    private boolean containsRecognizedPrefix(String[] tokens, int startIndex) {
        for (int i = startIndex; i < tokens.length; i++) {
            if (findMatchingPrefix(tokens[i]).isPresent()) {
                return true;
            }
        }
        return false;
    }

    private boolean looksLikePrefixedToken(String token) {
        int separatorIndex = token.indexOf('/');
        return separatorIndex > 0;
    }

}
