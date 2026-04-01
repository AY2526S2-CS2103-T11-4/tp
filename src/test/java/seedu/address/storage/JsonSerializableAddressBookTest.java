package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalResidents;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_RESIDENTS_FILE = TEST_DATA_FOLDER.resolve("typicalResidentsAddressBook.json");
    private static final Path LEGACY_PERSONS_FILE = TEST_DATA_FOLDER.resolve("legacyPersonsAddressBook.json");
    private static final Path LEGACY_ADDRESS_FILE = TEST_DATA_FOLDER.resolve("legacyAddressFieldAddressBook.json");
    private static final Path INVALID_RESIDENT_FILE = TEST_DATA_FOLDER.resolve("invalidResidentAddressBook.json");
    private static final Path DUPLICATE_RESIDENT_FILE = TEST_DATA_FOLDER.resolve("duplicateResidentAddressBook.json");

    @Test
    public void toModelType_typicalResidentsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_RESIDENTS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalResidentsAddressBook = TypicalResidents.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalResidentsAddressBook);
    }

    @Test
    public void toModelType_legacyPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(LEGACY_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook expectedLegacyAddressBook = TypicalResidents.getTypicalLegacyAddressBook();
        assertEquals(addressBookFromFile, expectedLegacyAddressBook);
    }

    @Test
    public void toModelType_legacyAddressFieldFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(LEGACY_ADDRESS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook expectedLegacyAddressBook = TypicalResidents.getTypicalLegacyAddressBook();
        assertEquals(addressBookFromFile, expectedLegacyAddressBook);
    }

    @Test
    public void toModelType_invalidResidentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_RESIDENT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateResidents_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_RESIDENT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_RESIDENT,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_nullResidentsAndLegacyResidents_returnsEmptyAddressBook() throws Exception {
        JsonSerializableAddressBook dataFromFile = new JsonSerializableAddressBook(null, null);
        assertEquals(new AddressBook(), dataFromFile.toModelType());
    }

}
