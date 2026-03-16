package seedu.address.model.resident;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UnitNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnitNumber(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new UnitNumber(invalidAddress));
    }

    @Test
    public void isValidUnitNumber() {
        // null address
        assertThrows(NullPointerException.class, () -> UnitNumber.isValidUnitNumber(null));

        // invalid addresses
        assertFalse(UnitNumber.isValidUnitNumber("")); // empty string
        assertFalse(UnitNumber.isValidUnitNumber(" ")); // spaces only

        // valid addresses
        assertTrue(UnitNumber.isValidUnitNumber("Blk 456, Den Road, #01-355"));
        assertTrue(UnitNumber.isValidUnitNumber("-")); // one character
        // long address
        assertTrue(UnitNumber.isValidUnitNumber("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA"));
    }

    @Test
    public void equals() {
        UnitNumber unitNumber = new UnitNumber("Valid UnitNumber");

        // same values -> returns true
        assertTrue(unitNumber.equals(new UnitNumber("Valid UnitNumber")));

        // same object -> returns true
        assertTrue(unitNumber.equals(unitNumber));

        // null -> returns false
        assertFalse(unitNumber.equals(null));

        // different types -> returns false
        assertFalse(unitNumber.equals(5.0f));

        // different values -> returns false
        assertFalse(unitNumber.equals(new UnitNumber("Other Valid UnitNumber")));
    }
}
