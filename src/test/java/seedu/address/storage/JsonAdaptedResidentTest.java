package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedResident.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalResidents.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.resident.Name;
import seedu.address.model.resident.Phone;
import seedu.address.model.resident.UnitNumber;

public class JsonAdaptedResidentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_ADDRESS = BENSON.getUnitNumber().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedResident resident = new JsonAdaptedResident(BENSON);
        assertEquals(BENSON, resident.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedResident resident =
                new JsonAdaptedResident(INVALID_NAME, VALID_PHONE, VALID_ADDRESS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, resident::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedResident resident = new JsonAdaptedResident(null, VALID_PHONE, VALID_ADDRESS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, resident::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedResident resident =
                new JsonAdaptedResident(VALID_NAME, INVALID_PHONE, VALID_ADDRESS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, resident::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedResident resident = new JsonAdaptedResident(VALID_NAME, null, VALID_ADDRESS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, resident::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedResident resident =
                new JsonAdaptedResident(VALID_NAME, VALID_PHONE, INVALID_ADDRESS);
        String expectedMessage = UnitNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, resident::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedResident resident = new JsonAdaptedResident(VALID_NAME, VALID_PHONE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, UnitNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, resident::toModelType);
    }

}
