package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.InputHistoryManager;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    public static final String IN_HISTORY_STYLE_CLASS = "in-history";
    private static final String FXML = "CommandBox.fxml";

    private final InputHistoryManager inputHistoryManager;
    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(InputHistoryManager inputHistoryManager, CommandExecutor commandExecutor) {
        super(FXML);
        this.inputHistoryManager = inputHistoryManager;
        this.commandExecutor = commandExecutor;

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                inputHistoryManager.exitHistoryMode();
            }
            setStyleToDefault();
        });
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles the key pressed event.
     */
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            onUpArrowPressed();
            break;
        case DOWN:
            onDownArrowPressed();
            break;
        default:
            // Do nothing
        }
    }

    /**
     * Handles event passing for the up arrow key pressed event.
     */
    private void onUpArrowPressed() {
        String prevInput = inputHistoryManager.retrieveEarlierPastInput();

        if (prevInput == null) {
            return;
        }

        commandTextField.setText(prevInput);
        formatInHistoryInputField();
    }

    /**
     * Handles event passing for the down arrow key pressed event.
     */
    private void onDownArrowPressed() {
        String input = inputHistoryManager.retrieveLaterPastInput();
        if (input == null) {
            setStyleToDefault();
        } else {
            commandTextField.setText(input);
            if (!input.isEmpty()) {
                formatInHistoryInputField();
            }
        }
    }

    /**
     * Applies common formats for the input field when the user is navigating through input history.
     */
    private void formatInHistoryInputField() {
        setStyleToDefault();
        setStyleToIndicateInHistory();
        commandTextField.positionCaret(commandTextField.getLength());
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        ObservableList<String> styleClasses = commandTextField.getStyleClass();

        styleClasses.remove(ERROR_STYLE_CLASS);
        styleClasses.remove(IN_HISTORY_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        setStyleClass(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate user is navigating through input history
     */
    private void setStyleToIndicateInHistory() {
        setStyleClass(IN_HISTORY_STYLE_CLASS);
    }

    /**
     * Toggles the given style class to be enabled on the command field component.
     *
     * @param styleClass The style class to toggle on.
     */
    private void setStyleClass(String styleClass) {
        ObservableList<String> styleClasses = commandTextField.getStyleClass();
        if (styleClasses.contains(styleClass)) {
            return;
        }
        styleClasses.add(styleClass);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
