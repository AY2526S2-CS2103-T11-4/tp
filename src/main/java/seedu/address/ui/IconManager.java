package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * Manages the loading and retrieval of icons used in the application in a static manner.
 */
public class IconManager {

    private static final Map<Icons, Image> ICON_MAP = new HashMap<>();

    static {
        for (Icons icon : Icons.values()) {
            try {
                ICON_MAP.put(icon, new Image(IconManager.class.getResourceAsStream(icon.getPath())));
            } catch (NullPointerException exception) {
                ICON_MAP.put(icon, null);
                System.err.println("Failed to load icon: " + icon.getPath());
            }
        }
    }

    /**
     * Returns the Image instance given the Icon enum.
     * @param icon The Icon enum to search for.
     * @return The Image instance tagged to the given Icon enum.
     */
    public static Image getIcon(Icons icon) {
        return ICON_MAP.get(icon);
    }

}
