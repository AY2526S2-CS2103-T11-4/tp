package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * Contains tests for the FontManager class.
 */
public class FontManagerTest {

    /**
     * Checks if FontManager.loadFonts() can load all fonts without throwing any exceptions.
     */
    @Test
    public void loadFonts_allConfiguredFonts_doNotThrow() {
        assertDoesNotThrow(FontManager::loadFonts);
    }

}
