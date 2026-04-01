package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedResident.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalResidents.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.resident.Name;
import seedu.address.model.resident.Phone;
import seedu.address.model.resident.Resident;
import seedu.address.model.resident.Role;
import seedu.address.model.resident.UnitNumber;

public class JsonAdaptedResidentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_UNIT_NUMBER = " ";
    private static final String INVALID_ROLE = "INVALID ROLE";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_UNIT_NUMBER = BENSON.getUnitNumber().toString();
    private static final String VALID_ROLE = BENSON.getRole().name();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedResident resident = new JsonAdaptedResident(BENSON);
        assertEquals(BENSON, resident.toModelType());
    }

    @Test
    public void toModelType_validResidentDetailsWithoutRole_returnsResidentWithDefaultRole() throws Exception {
        JsonAdaptedResident resident = new JsonAdaptedResident(VALID_NAME, VALID_PHONE, VALID_UNIT_NUMBER);
        Resident expectedResident = new Resident(BENSON.getName(), BENSON.getPhone(), BENSON.getUnitNumber());
        assertEquals(expectedResident, resident.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedResident resident =
                new JsonAdaptedResident(INVALID_NAME, VALID_PHONE, VALID_UNIT_NUMBER, VALID_ROLE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, resident::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedResident resident = new JsonAdaptedResident(null, VALID_PHONE, VALID_UNIT_NUMBER, VALID_ROLE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, resident::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedResident resident =
                new JsonAdaptedResident(VALID_NAME, INVALID_PHONE, VALID_UNIT_NUMBER, VALID_ROLE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, resident::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedResident resident = new JsonAdaptedResident(VALID_NAME, null, VALID_UNIT_NUMBER, VALID_ROLE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, resident::toModelType);
    }

    @Test
    public void toModelType_invalidUnitNumber_throwsIllegalValueException() {
        JsonAdaptedResident resident =
                new JsonAdaptedResident(VALID_NAME, VALID_PHONE, INVALID_UNIT_NUMBER, VALID_ROLE);
        String expectedMessage = UnitNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, resident::toModelType);
    }

    @Test
    public void toModelType_nullUnitNumberAndLegacyAddress_throwsIllegalValueException() {
        JsonAdaptedResident resident = new JsonAdaptedResident(VALID_NAME, VALID_PHONE, null, VALID_ROLE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, UnitNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, resident::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedResident resident =
                new JsonAdaptedResident(VALID_NAME, VALID_PHONE, VALID_UNIT_NUMBER, INVALID_ROLE);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, resident::toModelType);
    }

    @Test
    public void toModelType_validLegacyAddress_returnsResident() throws Exception {
        JsonAdaptedResident resident =
                new JsonAdaptedResident(VALID_NAME, VALID_PHONE, null, VALID_UNIT_NUMBER, VALID_ROLE);
        assertEquals(BENSON, resident.toModelType());
    }

    @Test
    public void toModelType_invalidLegacyAddress_throwsIllegalValueException() {
        JsonAdaptedResident resident =
                new JsonAdaptedResident(VALID_NAME, VALID_PHONE, null, INVALID_UNIT_NUMBER, VALID_ROLE);
        String expectedMessage = UnitNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, resident::toModelType);
    }

}
