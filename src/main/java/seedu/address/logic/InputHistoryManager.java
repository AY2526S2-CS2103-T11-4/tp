package seedu.address.logic;

import java.util.ArrayList;

/**
 * Maintains the history of user inputs for retrieval and navigation.
 */
public class InputHistoryManager {

    private final ArrayList<String> inputHistory = new ArrayList<>();
    private int currentIndex = 0;

    /**
     * Returns an earlier past input than the current past input, if possible.
     * "Earlier" here means further back in time.
     * If attempting to retrieve an earlier input would exceed the bounds of
     * the input history, null is returned instead.
     *
     * @return The earlier past input, if it exists.
     */
    public String retrieveEarlierPastInput() {
        int prevIndex = currentIndex;
        currentIndex = Math.max(0, currentIndex - 1);

        if (prevIndex == currentIndex) {
            return null;
        }

        if (currentIndex == inputHistory.size()) {
            return null;
        }

        return inputHistory.get(currentIndex);
    }

    /**
     * Returns a later past input than the current past input, if possible.
     * "Later" here means closer to the present.
     * If attempting to retrieve a later input would bring the user back to
     * the present (i.e. empty input) an empty string is returned instead.
     * If the user is already at the present, null is returned instead.
     *
     * @return The later past input, if it exists.
     */
    public String retrieveLaterPastInput() {
        int prevIndex = currentIndex;
        currentIndex = Math.min(inputHistory.size(), currentIndex + 1);

        // Stays in non-input history retrieval mode
        if (prevIndex == currentIndex) {
            return null;
        }

        // Otherwise, exit input-history retrieval mode
        if (currentIndex == inputHistory.size()) {
            return "";
        }

        return inputHistory.get(currentIndex);
    }

    /**
     * Adds the given user input to the input history, if applicable.
     * Resets currentIndex to -1, indicating that user is not navigating through input history.
     * If the user input matches the most recent input in the input history exactly,
     * nothing is added instead.
     *
     * @param input The user input to add to the input history.
     */
    public void addToHistory(String input) {
        if (inputHistory.isEmpty() || !input.equals(inputHistory.get(inputHistory.size() - 1))) {
            inputHistory.add(input);
        }
        exitHistoryMode();
    }

    /**
     * Resets the current index to the end of the input history, indicating
     * that user is not navigating through input history.
     */
    public void exitHistoryMode() {
        currentIndex = inputHistory.size();
    }

}
