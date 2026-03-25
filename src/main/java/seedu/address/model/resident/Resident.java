package seedu.address.model.resident;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Resident in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Resident {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final UnitNumber unitNumber;

    // Data fields
    private final Role role;

    /**
     * Every field must be present and not null.
     * This constructor is used when a resident has a specific (additional) role.
     */
    public Resident(Name name, Phone phone, UnitNumber unitNumber, Role role) {
        requireAllNonNull(name, phone, unitNumber, role);
        this.name = name;
        this.phone = phone;
        this.unitNumber = unitNumber;
        this.role = role;
    }

    /**
     * Every field must be present and not null.
     * This constructor is used for resident without a specific role.
     */
    public Resident(Name name, Phone phone, UnitNumber unitNumber) {
        requireAllNonNull(name, phone, unitNumber);
        this.name = name;
        this.phone = phone;
        this.unitNumber = unitNumber;
        this.role = Role.NONE; // default as Role.NONE
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public UnitNumber getUnitNumber() {
        return unitNumber;
    }

    public Role getRole() {
        return role;
    }

    /**
     * Returns true if both residents have the same name.
     * This defines a weaker notion of equality between two residents.
     */
    public boolean isSameResident(Resident otherResident) {
        if (otherResident == this) {
            return true;
        }

        return otherResident != null
                && otherResident.getName().equals(getName());
    }

    /**
     * Returns true if both residents have the same identity and data fields.
     * This defines a stronger notion of equality between two residents.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Resident)) {
            return false;
        }

        Resident otherResident = (Resident) other;
        return name.equals(otherResident.name)
                && phone.equals(otherResident.phone)
                && unitNumber.equals(otherResident.unitNumber)
                && role.equals(otherResident.role);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, unitNumber, role);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("unitNumber", unitNumber)
                .add("role", role)
                .toString();
    }

}
