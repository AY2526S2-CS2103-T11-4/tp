package seedu.address.ui;

/**
 * Contains the paths to the icons used in this application.
 */
public enum Icons {

    PHONE("/images/phone_icon.png"),
    UNIT_NUMBER("/images/unit_number_icon.png");

    private final String path;

    /**
     * Initialises the Icon enum with its given path.
     * @param path The path to the icon image file.
     */
    Icons(String path) {
        this.path = path;
    }

    /**
     * Returns the path to the icon image file.
     * @return The path to the icon image file.
     */
    public String getPath() {
        return path;
    }

}
