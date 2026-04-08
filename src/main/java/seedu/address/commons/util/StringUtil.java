package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Locale;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns true if the {@code sentence} contains a word that matches {@code word} exactly or
     * within one single-character edit, ignoring case.
     *
     * <p>A full word comparison is still required. This means the comparison is done against each
     * whitespace-delimited word in the sentence rather than against arbitrary substrings.</p>
     *
     * <p>Supported single-character edits are insertion, deletion, and substitution.</p>
     *
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsFuzzyWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String[] wordsInPreppedSentence = sentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(sentenceWord -> isWithinOneEditIgnoreCase(sentenceWord, preppedWord));
    }

    /**
     * Returns true if the two words are equal ignoring case or differ by at most one insertion,
     * deletion, or substitution.
     */
    private static boolean isWithinOneEditIgnoreCase(String firstWord, String secondWord) {
        String normalizedFirstWord = firstWord.toLowerCase(Locale.ROOT);
        String normalizedSecondWord = secondWord.toLowerCase(Locale.ROOT);

        int lengthDifference = Math.abs(normalizedFirstWord.length() - normalizedSecondWord.length());
        if (lengthDifference > 1) {
            return false;
        }

        if (normalizedFirstWord.length() == normalizedSecondWord.length()) {
            return hasAtMostOneReplacement(normalizedFirstWord, normalizedSecondWord);
        }

        if (normalizedFirstWord.length() > normalizedSecondWord.length()) {
            return hasAtMostOneInsertionOrDeletion(normalizedFirstWord, normalizedSecondWord);
        }

        return hasAtMostOneInsertionOrDeletion(normalizedSecondWord, normalizedFirstWord);
    }

    /**
     * Returns true if two same-length words differ at no more than one position.
     */
    private static boolean hasAtMostOneReplacement(String firstWord, String secondWord) {
        int mismatchCount = 0;

        for (int i = 0; i < firstWord.length(); i++) {
            if (firstWord.charAt(i) != secondWord.charAt(i)) {
                mismatchCount++;
                if (mismatchCount > 1) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns true if removing one character from {@code longerWord} makes it equal to
     * {@code shorterWord}.
     */
    private static boolean hasAtMostOneInsertionOrDeletion(String longerWord, String shorterWord) {
        int longerIndex = 0;
        int shorterIndex = 0;
        boolean skippedCharacter = false;

        while (longerIndex < longerWord.length() && shorterIndex < shorterWord.length()) {
            if (longerWord.charAt(longerIndex) == shorterWord.charAt(shorterIndex)) {
                longerIndex++;
                shorterIndex++;
                continue;
            }

            if (skippedCharacter) {
                return false;
            }

            skippedCharacter = true;
            longerIndex++;
        }

        return true;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
