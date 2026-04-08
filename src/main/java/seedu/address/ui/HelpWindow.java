package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String COMMAND_LIST_MESSAGE = """
Available commands

add: Adds a resident to the address book.
  Format:
    add n/NAME p/PHONE_NUMBER u/UNIT_NUMBER [r/ROLE]
  Note:
    ROLE may be HA, FH, RA, or NONE.
  Example:
    add n/John Doe p/98765432 u/02-25 r/HA

list: Shows all residents and resets any active sort.
  Format:
    list

sort: Sorts the displayed list of residents by the specified field.
  Format:
    sort FIELD
  Fields:
    name, phone, unit, role
  Example:
    sort role

edit: Edits an existing resident by index.
  Format:
    edit INDEX [n/NAME] [p/PHONE_NUMBER] [u/UNIT_NUMBER] [r/ROLE]
  Note:
    Use r/NONE to remove an assigned role.
  Example:
    edit 1 p/91234567 u/03-14

find: Finds residents using either name keywords or fielded criteria.
  Format:
    find [n/NAME]... [p/PHONE_NUMBER]...
         [u/UNIT_NUMBER]...[r/ROLE]
  Note:
    Once you use a prefix, every search term must be prefixed.
  Examples:
    find alex david
    find n/alex n/david p/9876 u/02-25

delete: Deletes a resident by index.
  Format:
    delete INDEX
  Example:
    delete 2

copy: Copies all currently displayed residents to the clipboard.
  Format:
    copy

clear: Deletes all residents from the address book.
  Format:
    clear

help: Shows this command list.
  Format:
    help

exit: Exits the application.
  Format:
    exit""";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Label helpMessage;



    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(COMMAND_LIST_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        if (getRoot().isIconified()) {
            getRoot().setIconified(false);
        }
        getRoot().show();
        getRoot().toFront();
        getRoot().requestFocus();
    }
}
