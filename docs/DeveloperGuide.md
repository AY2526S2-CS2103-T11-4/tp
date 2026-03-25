---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# AB-3 Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a resident).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th resident in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new resident. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the resident was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the resident being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**: Dormitory Resident Assistants (RAs)

* has a need to manage a significant number of residents' information
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Provides quick access to residents' information (e.g. Mobile Number, Next-of-Kin's contact, Unit Number) in times of emergency


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                              | I want to …​                                                                    | So that I can…​                                                                                                                |
|----------|--------------------------------------|---------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------|
| `* * *`  | user                                 | add a new resident                                                              | add new residents into the current list of residents                                                                           |
| `* * *`  | user                                 | delete a resident                                                               | remove people who are no longer residents, or wrong entries from the current list of residents                                 |
| `* * *`  | user                                 | see a list of current residents                                                 | see the list of current residents                                                                                              |
| `* *`    | advanced user                        | search for residents with partial information                                   | still find residents with only incomplete information (e.g. Only their name, part of their phone number)                       |
| `* *`    | advanced user                        | import contacts from an appropriate file the local device                       | automatically add a set of contacts to save time and effort on manually adding                                                 |
| `* *`    | cautious user                        | add custom notes to a residents' profile                                        | add important details to residents to remind app users of them in the future                                                   |
| `* *`    | cautious user                        | backup resident data to a file on the local device                              | restore app data through the backup file as in case of emergencies                                                             |
| `* *`    | forgetful/busy/concerned user        | set reminders linked to a specific individual                                   | get the system to remind me to check in on them (e.g. Residents that may require counselling)                                  |
| `* *`    | frequent user                        | add categories to residents and search by categories                            | easily narrow down specific groups or individuals through categories                                                           |
| `* *`    | frequent user                        | access the app data from the previous sessions when i used the app              | access and modify app data through various sessions of using the app.                                                          |
| `* *`    | frequent user                        | autocomplete certain commands                                                   | save time on typing things out fully                                                                                           |
| `* *`    | frequent user                        | quickly retrieve my previous inputs                                             | quickly correct a wrong input, or modify a previous input and execute it without having to spend time retyping everything      |
| `* *`    | new user                             | access a comprehensive guide for a specific command                             | learn how to use each specific command in-depth                                                                                |
| `* *`    | new user                             | see a list of all app commands                                                  | find out what commands are available to me                                                                                     |
| `* *`    | new user                             | use the app which is filled with sample data                                    | use the app and explore the features while having sample data to operate on                                                    |
| `* *`    | organised user                       | archive residents that are no longer active (i.e., not a current resident)      | maintain the relevance of the current list of residents while also having record of past residents for administrative purposes |
| `* *`    | organised user                       | merge duplicate entries for the same individual                                 | ensure cleanliness of data and being able to conveniently combine 2 resident records into 1, instead of manually editing       |
| `* *`    | security-conscious user              | secure the app with a password                                                  | prevent any unwanted access to sensitive information stored in the app                                                         |
| `* *`    | user                                 | copy textual information to device's clipboard                                  | use this information for other purposes, like sending the information through a messaging app on the same device               |
| `* *`    | user                                 | edit residents' information                                                     | update contact details and other information of residents                                                                      |
| `* *`    | user concerned with administratives  | view "last updated" timestamps                                                  | see when the information was last modified                                                                                     |
| `* *`    | user concerned with administratives  | view the history of user actions which modify app data and settings             | maintain accountability and cater to any administrative matters, like audits                                                   |
| `* *`    | user concerned with administratives  | export the data to the device's local storage in a widely supported file format | use the data for other purposes, like loading it into another program, or printing it out                                      |
| `* *`    | user with sight issues               | adjust the font size and color scheme of the app                                | better see the text on the app                                                                                                 |
| `* *`    | visual user                          | upload residents' profile pictures                                              | easily recognise residents visually by their faces rather than name, or other information                                      |
| `* *`    | visual user                          | change the view settings (list style, with photo, without photo, etc.)          | choose which information is more important to me currently                                                                     |

*{More to be added}*

### Use cases

(For all use cases below, the System is the QuickLookup and the Actor is the user, unless specified otherwise)

**Use case: UC1 - Start the Application**

**MSS**

1.  User runs the jar file.
2.  System loads Residents' information from Storage.

    Use case ends.

**Extensions**

* 2a. No Storage found.
    * 2a1. System creates a new Storage.

      Use case ends.

<br>

**Use case: UC2 - List all the Residents**

**Precondition: User has started the application**

**MSS**

1.  User requests for the list of Residents.
2.  System displays the list of Residents.

    Use case ends.

**Extensions**

* 2a. System has no Residents to display.
    * 2a1. System prompt User with a message to indicate that the list of Residents is empty.

      Use case ends.

<br>

**Use Case: UC3 - Add a new Resident**

**Precondition: User has started the application**

**MSS**

1. User adds the information via the command line for the new Resident.
2. System adds the new Resident.
3. System saves the new Resident to the list of Residents (Storage).
4. System displays the updated list of Residents (UC2).

   Use case ends.

**Extensions**

* 1a. System detects invalid or missing inputs.
    * 1a1. System prompts User with a message of the error.
    * 1a2. User reenters the information and resubmits via the command line.

      Steps 1a1-1a2 are repeated until the information entered is correct.

      Use case resumes from step 2.

<br>

**Use Case: UC4 - Delete a Resident**

**Precondition: User has started the application and the Resident to be deleted is in the list of Residents**

**MSS**
1. User requests for the list of Residents (UC2).
2. User requests for the deletion of a Resident.
3. System deletes the specified Resident from the list (Storage).
4. System displays the new list of Residents.

   Use case ends.

**Extensions**
* 2a. System detects invalid or missing input.
    * 2a1. System prompts User with a message of the error.
    * 2a2. User reenters the command with the index and resubmits via command line.

      Steps 2a1-2a2 are repeated until the command inputted is valid.

      Use case resumes from step 3.

<br>

**Use Case: UC5 - Copy Resident Information**

**Precondition: User has started the application**

**MSS**
1. User requests for the list of Residents (UC2).
2. User requests to copy resident information.
3. System copies all displayed residents' information (names, phone numbers, emails, addresses, tags) to the device's clipboard.
4. System displays a confirmation message.

   Use case ends.

**Extensions**
* 1a. User has applied a search filter using find command.
    * 1a1. System copies only the residents matching the search results to the device's clipboard.
    
      Use case resumes from step 4.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 residents without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The GUI shall function correctly on screen resolutions **1920x1080 and above at 100% and 125% scaling** and remain usuable on screen resolutions **1280x720 and above at 150% scaling**, although the user experience may not be optimal. 
5.  All application data shall be stored locally in human-editable text files.
6.  The system shall not use a DBMS for data storage.
7.  The system shall primarily follow the object-oriented programming paradigm.
8.  The system shall evolve from the provided existing codebase, with changes implemented incrementally while maintaining a working system.
9. The application shall be packaged into a single executable JAR file.
10. The final packaged application shall not exceed 100MB in size.
11.  The system shall function without depending on a remote server
12. The application shall not requre users to install additional software dependencies
13. The User Guide and Developer Guide shall be PDF-friendly and avoid interactive elements such as expandable panels or embedded videos.

*{More to be added}*

### Glossary

* **GUI**: A visual interface that allows users to interact with the application using graphical elements such as windows, buttons, icons, and menus instead of typing commands.
* **JAR file**: A packaged file format used to distribute Java applications. It bundles compiled Java classes and resources into a single executable file.
* **Object-Oriented Programming**: A programming paradigm based on organizing code into objects that contain data and behavior.
* **Resident**: A resident whose record is managed by the application.
* **Resident Assistants (RAs)**: The primary target user of the application, responsible for resident welfare and administrative follow-up.
* **Command**: A text instruction entered by the user to perform an action in the app.
* **Contact Information**: Personal communication details of a resident, such as phone number and email address.
* **Room Location**: The dormitory room or unit assigned to a resident.
* **Emergency Contact**: A contact resident who should be reached in urgent situations; may refer to the next-of-kin or another designated resident.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a resident

1. Deleting a resident while all residents are being shown

   1. Prerequisites: List all residents using the `list` command. Multiple residents in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No resident is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Copying resident information

1. Copying all resident information while showing residents

   1. Prerequisites: List all residents using the `list` command. Multiple residents in the list.

   1. Test case: `copy`<br>
      Expected: All displayed residents' information is copied to clipboard. Confirmation message shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `copy` after using `find` command<br>
      Expected: All residents matching the search results are copied to clipboard. Confirmation message shown in the status message.

   1. Test case: `copy` when the list is empty<br>
      Expected: Command executes but copies empty or minimal data. Appropriate message shown in the status message.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
