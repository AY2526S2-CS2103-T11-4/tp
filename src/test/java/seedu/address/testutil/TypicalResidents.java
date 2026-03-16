package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.resident.Resident;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalResidents {

    public static final Resident ALICE = new ResidentBuilder().withName("Alice Pauline")
            .withUnitNumber("123, Jurong West Ave 6, #08-111").withPhone("94351253").build();
    public static final Resident BENSON = new ResidentBuilder().withName("Benson Meier")
            .withUnitNumber("311, Clementi Ave 2, #02-25").withPhone("98765432").build();
    public static final Resident CARL = new ResidentBuilder().withName("Carl Kurz").withPhone("95352563")
            .withUnitNumber("wall street").build();
    public static final Resident DANIEL = new ResidentBuilder().withName("Daniel Meier").withPhone("87652533")
            .withUnitNumber("10th street").build();
    public static final Resident ELLE = new ResidentBuilder().withName("Elle Meyer").withPhone("9482224")
            .withUnitNumber("michegan ave").build();
    public static final Resident FIONA = new ResidentBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withUnitNumber("little tokyo").build();
    public static final Resident GEORGE = new ResidentBuilder().withName("George Best").withPhone("9482442")
            .withUnitNumber("4th street").build();

    // Manually added
    public static final Resident HOON = new ResidentBuilder().withName("Hoon Meier").withPhone("8482424")
            .withUnitNumber("little india").build();
    public static final Resident IDA = new ResidentBuilder().withName("Ida Mueller").withPhone("8482131")
            .withUnitNumber("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Resident AMY = new ResidentBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withUnitNumber(VALID_ADDRESS_AMY).build();
    public static final Resident BOB = new ResidentBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withUnitNumber(VALID_ADDRESS_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalResidents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Resident resident : getTypicalPersons()) {
            ab.addPerson(resident);
        }
        return ab;
    }

    public static List<Resident> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
