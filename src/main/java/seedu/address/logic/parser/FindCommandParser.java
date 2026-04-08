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
     * Enum to categorise different types of tokens
     * RECOGNIZED_PREFIX: tokens that are recognized prefixes in the system
     * UNRECOGNIZED_PREFIX: tokens that are prefixes with invalid parameters (currently: x/ is invalid)
     * BARE: tokens that do no resemble prefixes, i.e. raw argument tokens
     */
    private enum TokenKind {
        RECOGNIZED_PREFIX,
        UNRECOGNIZED_PREFIX,
        BARE
    }


    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw invalidCommandFormatException();
        }

        String[] tokens = trimmedArgs.split("\\s+");
        return parseFieldedFind(tokens);
    }

    /**
     * Parses tokenized fielded find arguments into a {@code FindCommand}.
     *
     * <p>This method first identifies where valid prefixed search terms begin, validates any
     * leading tokens before that point, then parses the remaining tokens as strictly prefixed
     * search terms.</p>
     */
    private FindCommand parseFieldedFind(String[] tokens) throws ParseException {
        // Check for any valid prefixes in tokens
        int firstRecognizedPrefixIndex = findFirstRecognizedPrefixIndex(tokens);
        if (firstRecognizedPrefixIndex == -1) {
            throw invalidCommandFormatException();
        }

        // Checks if first token is a prefix
        validateLeadingTokens(tokens, firstRecognizedPrefixIndex);

        List<String> nameKeywords = new ArrayList<>();
        List<String> phoneKeywords = new ArrayList<>();
        List<String> unitKeywords = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

        for (int i = firstRecognizedPrefixIndex; i < tokens.length; i++) {
            String token = tokens[i];
            Optional<Prefix> matchedPrefix = findMatchingPrefix(token);
            if (matchedPrefix.isEmpty()) {
                throw invalidCommandFormatException();
            }

            Prefix prefix = matchedPrefix.get();
            String value = token.substring(prefix.getPrefix().length()).trim();
            if (value.isEmpty()) {
                throw invalidCommandFormatException();
            }

            addParsedValue(prefix, value, nameKeywords, phoneKeywords, unitKeywords, roles);
        }

        return new FindCommand(new ResidentMatchesFindPredicate(nameKeywords, phoneKeywords, unitKeywords, roles));
    }

    /**
     * Validates tokens that appear before the first recognized prefix.
     *
     * <p>A bare token before a valid prefixed token indicates mixed syntax, while a token that
     * looks prefixed but uses an unsupported prefix indicates invalid command format.</p>
     */
    private void validateLeadingTokens(String[] tokens, int firstRecognizedPrefixIndex) throws ParseException {
        if (firstRecognizedPrefixIndex == 0) {
            return;
        }

        TokenKind firstLeadingTokenKind = classifyToken(tokens[0]);
        if (firstLeadingTokenKind == TokenKind.BARE) {
            throw new ParseException(MESSAGE_MIXED_FIND_SYNTAX);
        }

        throw invalidCommandFormatException();
    }

    /**
     * Adds the parsed value to the collection corresponding to the given prefix.
     */
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

    /**
     * Returns the recognized prefix that starts the token, if any.
     */
    private Optional<Prefix> findMatchingPrefix(String token) {
        return Arrays.stream(SUPPORTED_PREFIXES)
                .filter(prefix -> token.startsWith(prefix.getPrefix()))
                .findFirst();
    }

    /**
     * Classifies a token based on whether it starts with a supported prefix, resembles an
     * unsupported prefixed token, or is plain unprefixed text.
     */
    private TokenKind classifyToken(String token) {
        if (findMatchingPrefix(token).isPresent()) {
            return TokenKind.RECOGNIZED_PREFIX;
        }

        if (looksLikePrefixedToken(token)) {
            return TokenKind.UNRECOGNIZED_PREFIX;
        }

        return TokenKind.BARE;
    }

    /**
     * Returns the index of the first token with a recognized prefix, or {@code -1} if none exists.
     */
    private int findFirstRecognizedPrefixIndex(String[] tokens) {
        for (int i = 0; i < tokens.length; i++) {
            if (classifyToken(tokens[i]) == TokenKind.RECOGNIZED_PREFIX) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns whether the token has the shape of a prefixed argument, i.e. it contains a slash
     * after at least one leading character.
     */
    private boolean looksLikePrefixedToken(String token) {
        int separatorIndex = token.indexOf('/');
        return separatorIndex > 0;
    }

    /**
     * Returns the standard parse exception used for invalid find command formats.
     */
    private ParseException invalidCommandFormatException() {
        return new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

}
