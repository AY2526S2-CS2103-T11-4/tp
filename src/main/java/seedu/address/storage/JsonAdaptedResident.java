package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.resident.Name;
import seedu.address.model.resident.Phone;
import seedu.address.model.resident.Resident;
import seedu.address.model.resident.UnitNumber;

/**
 * Jackson-friendly version of {@link Resident}.
 */
class JsonAdaptedResident {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Resident's %s field is missing!";

    private final String name;
    private final String phone;
    private final String unitNumber;

    /**
     * Constructs a {@code JsonAdaptedResident} with the given resident details.
     */
    @JsonCreator
    public JsonAdaptedResident(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                               @JsonProperty("unitNumber") String unitNumber,
                               @JsonProperty("address") String legacyUnitNumber) {
        this.name = name;
        this.phone = phone;
        this.unitNumber = unitNumber != null ? unitNumber : legacyUnitNumber;
    }

    public JsonAdaptedResident(String name, String phone, String unitNumber) {
        this(name, phone, unitNumber, null);
    }

    /**
     * Converts a given {@code Resident} into this class for Jackson use.
     */
    public JsonAdaptedResident(Resident source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        unitNumber = source.getUnitNumber().value;
    }

    /**
     * Converts this Jackson-friendly adapted resident object into the model's {@code Resident} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted resident.
     */
    public Resident toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);


        if (unitNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UnitNumber.class.getSimpleName()));
        }
        if (!UnitNumber.isValidUnitNumber(unitNumber)) {
            throw new IllegalValueException(UnitNumber.MESSAGE_CONSTRAINTS);
        }
        final UnitNumber modelUnitNumber = new UnitNumber(unitNumber);

        return new Resident(modelName, modelPhone, modelUnitNumber);
    }

}
