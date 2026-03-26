package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditResidentDescriptor;
import seedu.address.model.resident.Name;
import seedu.address.model.resident.Phone;
import seedu.address.model.resident.Resident;
import seedu.address.model.resident.Role;
import seedu.address.model.resident.UnitNumber;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditResidentDescriptorBuilder {

    private EditResidentDescriptor descriptor;

    public EditResidentDescriptorBuilder() {
        descriptor = new EditResidentDescriptor();
    }

    public EditResidentDescriptorBuilder(EditResidentDescriptor descriptor) {
        this.descriptor = new EditResidentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditResidentDescriptor} with fields containing {@code resident}'s details
     */
    public EditResidentDescriptorBuilder(Resident resident) {
        descriptor = new EditResidentDescriptor();
        descriptor.setName(resident.getName());
        descriptor.setPhone(resident.getPhone());
        descriptor.setUnitNumber(resident.getUnitNumber());
        descriptor.setRole(resident.getRole());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditResidentDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditResidentDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code UnitNumber} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditResidentDescriptorBuilder withAddress(String address) {
        descriptor.setUnitNumber(new UnitNumber(address));
        return this;
    }

    /**
     * Sets the {@code role} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditResidentDescriptorBuilder withRole(String role) {
        descriptor.setRole(Role.valueOf(role));
        return this;
    }


    public EditCommand.EditResidentDescriptor build() {
        return descriptor;
    }
}
