package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESIDENTS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.resident.Name;
import seedu.address.model.resident.Phone;
import seedu.address.model.resident.Resident;
import seedu.address.model.resident.Role;
import seedu.address.model.resident.UnitNumber;

/**
 * Edits the details of an existing resident in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the resident identified "
            + "by the index number used in the displayed resident list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_UNIT_NUMBER + "UNIT_NUMBER] "
            + "[" + PREFIX_ROLE + "ROLE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567";

    public static final String MESSAGE_EDIT_RESIDENT_SUCCESS = "Edited Resident: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RESIDENT = AddCommand.MESSAGE_DUPLICATE_RESIDENT;
    public static final String MESSAGE_DUPLICATE_PHONE = AddCommand.MESSAGE_DUPLICATE_PHONE;
    public static final String MESSAGE_DUPLICATE_UNITNUMBER = AddCommand.MESSAGE_DUPLICATE_UNITNUMBER;

    private final Index index;
    private final EditResidentDescriptor editResidentDescriptor;

    /**
     * @param index of the resident in the filtered resident list to edit
     * @param editResidentDescriptor details to edit the resident with
     */
    public EditCommand(Index index, EditResidentDescriptor editResidentDescriptor) {
        requireNonNull(index);
        requireNonNull(editResidentDescriptor);

        this.index = index;
        this.editResidentDescriptor = new EditResidentDescriptor(editResidentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Resident> lastShownList = model.getFilteredResidentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESIDENT_DISPLAYED_INDEX);
        }

        Resident residentToEdit = lastShownList.get(index.getZeroBased());
        Resident editedResident = createEditedResident(residentToEdit, editResidentDescriptor);

        // Overall checking
        if (!residentToEdit.isSameResident(editedResident) && model.hasResident(editedResident)) {
            throw new CommandException(MESSAGE_DUPLICATE_RESIDENT);
        }

        // Check for duplicate in phone number
        if (!residentToEdit.getPhone().equals(editedResident.getPhone())
                && model.hasPhone(editedResident.getPhone())) {
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        }

        // Check for duplicate in unitNumber
        if (!residentToEdit.getUnitNumber().equals(editedResident.getUnitNumber())
                && model.hasUnitNumber(editedResident.getUnitNumber())) {
            throw new CommandException(MESSAGE_DUPLICATE_UNITNUMBER);
        }

        model.setResident(residentToEdit, editedResident);
        model.updateFilteredResidentsList(PREDICATE_SHOW_ALL_RESIDENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_RESIDENT_SUCCESS, Messages.format(editedResident)));
    }

    /**
     * Creates and returns a {@code Resident} with the details of {@code residentToEdit}
     * edited with {@code editResidentDescriptor}.
     */
    private static Resident createEditedResident(Resident residentToEdit,
                                                 EditResidentDescriptor editResidentDescriptor) {
        assert residentToEdit != null;

        Name updatedName = editResidentDescriptor.getName().orElse(residentToEdit.getName());
        Phone updatedPhone = editResidentDescriptor.getPhone().orElse(residentToEdit.getPhone());
        UnitNumber updatedUnitNumber = editResidentDescriptor.getUnitNumber().orElse(residentToEdit.getUnitNumber());
        Role updatedRole = editResidentDescriptor.getRole().orElse(residentToEdit.getRole());

        return new Resident(updatedName, updatedPhone, updatedUnitNumber, updatedRole);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editResidentDescriptor.equals(otherEditCommand.editResidentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editResidentDescriptor", editResidentDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the resident with. Each non-empty field value will replace the
     * corresponding field value of the resident.
     */
    public static class EditResidentDescriptor {
        private Name name;
        private Phone phone;
        private UnitNumber unitNumber;
        private Role role;

        public EditResidentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditResidentDescriptor(EditResidentDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setUnitNumber(toCopy.unitNumber);
            setRole(toCopy.role);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, unitNumber, role);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setUnitNumber(UnitNumber unitNumber) {
            this.unitNumber = unitNumber;
        }

        public Optional<UnitNumber> getUnitNumber() {
            return Optional.ofNullable(unitNumber);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditResidentDescriptor)) {
                return false;
            }

            EditResidentDescriptor otherEditResidentDescriptor = (EditResidentDescriptor) other;
            return Objects.equals(name, otherEditResidentDescriptor.name)
                    && Objects.equals(phone, otherEditResidentDescriptor.phone)
                    && Objects.equals(unitNumber, otherEditResidentDescriptor.unitNumber)
                    && Objects.equals(role, otherEditResidentDescriptor.role);
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
}
