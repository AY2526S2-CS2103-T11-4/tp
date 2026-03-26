package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.resident.Resident;
import seedu.address.model.resident.Role;

/**
 * An UI component that displays information of a {@code Resident}.
 */
public class ResidentCard extends UiPart<Region> {

    private static final String FXML = "ResidentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Resident resident;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private ImageView phoneIcon;
    @FXML
    private Label phone;
    @FXML
    private ImageView unitNumberIcon;
    @FXML
    private Label unitNumber;
    @FXML
    private Label role;

    /**
     * Creates a {@code ResidentCard} with the given {@code Resident} and index to display.
     */
    public ResidentCard(Resident resident, int displayedIndex) {
        super(FXML);
        this.resident = resident;

        id.setText(displayedIndex + ". ");
        name.setText(resident.getName().fullName);
        phoneIcon.setImage(IconManager.getIcon(Icons.PHONE));
        phone.setText(resident.getPhone().value);
        unitNumberIcon.setImage(IconManager.getIcon(Icons.UNIT_NUMBER));
        unitNumber.setText(resident.getUnitNumber().value);
        if (resident.getRole() == Role.NONE) {
            role.setVisible(false);
            role.setManaged(false);
        } else {
            role.setVisible(true);
            role.setManaged(true);
            role.setText(resident.getRole().name());
        }
    }
}
