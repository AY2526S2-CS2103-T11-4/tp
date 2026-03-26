package seedu.address.testutil;

import seedu.address.model.resident.Name;
import seedu.address.model.resident.Phone;
import seedu.address.model.resident.Resident;
import seedu.address.model.resident.Role;
import seedu.address.model.resident.UnitNumber;

/**
 * A utility class to help with building Person objects.
 */
public class ResidentBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ADDRESS = "28/3/E";
    public static final Role DEFAULT_ROLE = Role.HA;

    private Name name;
    private Phone phone;
    private UnitNumber unitNumber;
    private Role role;

    /**
     * Creates a {@code ResidentBuilder} with the default details.
     */
    public ResidentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        unitNumber = new UnitNumber(DEFAULT_ADDRESS);
        role = DEFAULT_ROLE;
    }

    /**
     * Initializes the ResidentBuilder with the data of {@code residentToCopy}.
     */
    public ResidentBuilder(Resident residentToCopy) {
        name = residentToCopy.getName();
        phone = residentToCopy.getPhone();
        unitNumber = residentToCopy.getUnitNumber();
        role = residentToCopy.getRole();
    }

    /**
     * Sets the {@code Name} of the {@code Resident} that we are building.
     */
    public ResidentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code UnitNumber} of the {@code Resident} that we are building.
     */
    public ResidentBuilder withUnitNumber(String address) {
        this.unitNumber = new UnitNumber(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Resident} that we are building.
     */
    public ResidentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Resident} that we are building.
     */
    public ResidentBuilder withRole(String role) {
        this.role = Role.valueOf(role.toUpperCase());
        return this;
    }


    public Resident build() {
        return new Resident(name, phone, unitNumber, role);
    }

}
