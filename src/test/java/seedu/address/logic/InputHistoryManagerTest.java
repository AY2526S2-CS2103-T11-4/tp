package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class InputHistoryManagerTest {

    @Test
    public void retrieveEarlierPastInput_emptyHistory_returnsNull() {
        InputHistoryManager historyManager = new InputHistoryManager();

        assertNull(historyManager.retrieveEarlierPastInput());
    }

    @Test
    public void retrieveEarlierPastInput_hasHistory_stopsAtEarliestInput() {
        InputHistoryManager historyManager = new InputHistoryManager();
        historyManager.addToHistory("first");
        historyManager.addToHistory("second");
        historyManager.addToHistory("third");

        assertEquals("third", historyManager.retrieveEarlierPastInput());
        assertEquals("second", historyManager.retrieveEarlierPastInput());
        assertEquals("first", historyManager.retrieveEarlierPastInput());
        assertEquals("first", historyManager.retrieveEarlierPastInput());
    }

    @Test
    public void retrieveLaterPastInput_withHistory_returnsNullAtPresentInput() {
        InputHistoryManager historyManager = new InputHistoryManager();
        historyManager.addToHistory("first");
        historyManager.addToHistory("second");
        historyManager.addToHistory("third");

        historyManager.retrieveEarlierPastInput();
        historyManager.retrieveEarlierPastInput();
        historyManager.retrieveEarlierPastInput();

        assertEquals("second", historyManager.retrieveLaterPastInput());
        assertEquals("third", historyManager.retrieveLaterPastInput());
        assertNull(historyManager.retrieveLaterPastInput());
        assertNull(historyManager.retrieveLaterPastInput());
    }

    @Test
    public void addToHistory_duplicateConsecutiveInput_doesNotAddDuplicate() {
        InputHistoryManager historyManager = new InputHistoryManager();
        historyManager.addToHistory("list");
        historyManager.addToHistory("list");

        assertEquals("list", historyManager.retrieveEarlierPastInput());
        assertNull(historyManager.retrieveLaterPastInput());

        historyManager.addToHistory("list 123");
        historyManager.addToHistory("list 123");

        assertEquals("list 123", historyManager.retrieveEarlierPastInput());

        historyManager.addToHistory("add first");
        historyManager.addToHistory("add first ");
        historyManager.addToHistory("add first  ");
        historyManager.addToHistory("add first");

        assertEquals("add first", historyManager.retrieveEarlierPastInput());
        assertEquals("add first  ", historyManager.retrieveEarlierPastInput());
        assertEquals("add first ", historyManager.retrieveEarlierPastInput());
        assertEquals("add first", historyManager.retrieveEarlierPastInput());
    }

    @Test
    public void addToHistory_afterNavigating_resetsIndexToLatestInput() {
        InputHistoryManager historyManager = new InputHistoryManager();
        historyManager.addToHistory("find a");
        historyManager.addToHistory("find b");

        assertEquals("find b", historyManager.retrieveEarlierPastInput());

        historyManager.addToHistory("find c");

        assertEquals("find c", historyManager.retrieveEarlierPastInput());
    }
}
