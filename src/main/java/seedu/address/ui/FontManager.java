package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.text.Font;
import seedu.address.commons.core.LogsCenter;

/**
 * Handles loading custom font resources for the app.
 */
public class FontManager {

    private static final Logger logger = LogsCenter.getLogger(FontManager.class);

    private static final String[] fontPaths = {
        "/fonts/Montserrat-Black.ttf",
        "/fonts/Montserrat-BlackItalic.ttf",
        "/fonts/Montserrat-Bold.ttf",
        "/fonts/Montserrat-BoldItalic.ttf",
        "/fonts/Montserrat-ExtraBold.ttf",
        "/fonts/Montserrat-ExtraBoldItalic.ttf",
        "/fonts/Montserrat-ExtraLight.ttf",
        "/fonts/Montserrat-ExtraLightItalic.ttf",
        "/fonts/Montserrat-Italic.ttf",
        "/fonts/Montserrat-Light.ttf",
        "/fonts/Montserrat-LightItalic.ttf",
        "/fonts/Montserrat-Medium.ttf",
        "/fonts/Montserrat-MediumItalic.ttf",
        "/fonts/Montserrat-Regular.ttf",
        "/fonts/Montserrat-SemiBold.ttf",
        "/fonts/Montserrat-SemiBoldItalic.ttf",
        "/fonts/Montserrat-Thin.ttf",
        "/fonts/Montserrat-ThinItalic.ttf",
    };

    /**
     * Loads all font files from the fontPaths array.
     * This method should be called at the start of UI loading for the CSS files to
     * be able to use the included font families.
     */
    public static void loadFonts() {
        assert fontPaths != null;

        for (String fontPath : fontPaths) {
            assert fontPath != null : "A font path in the FontManager font list is null";

            Font font = Font.loadFont(FontManager.class.getResourceAsStream(fontPath), 12);
            if (font == null) {
                throw new NullPointerException("Unable to load font from: " + fontPath);
            }
            logger.info("Loaded font: " + font.getName());
        }
    }

}
