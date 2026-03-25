package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.resident.Name;
import seedu.address.model.resident.Phone;
import seedu.address.model.resident.Resident;
import seedu.address.model.resident.UnitNumber;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Resident[] getSampleResidents() {
        return new Resident[] {
            new Resident(new Name("Alex Yeoh"), new Phone("87438807"),
                new UnitNumber("30/1/A")),
            new Resident(new Name("Bernice Yu"), new Phone("99272758"),
                new UnitNumber("20/7/A")),
            new Resident(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new UnitNumber("11/6/B")),
            new Resident(new Name("David Li"), new Phone("91031282"),
                new UnitNumber("22/4/A")),
            new Resident(new Name("Irfan Ibrahim"), new Phone("92492021"),
                new UnitNumber("7/5/A")),
            new Resident(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new UnitNumber("28/3/E"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Resident sampleResident : getSampleResidents()) {
            sampleAb.addResident(sampleResident);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
