---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# QuickLookup Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**


* QuickLookup is based on the [AddressBook-Level3 project](https://github.com/se-edu/addressbook-level3) created by the [SE-EDU initiative](https://se-education.org).
* Some documentation and architecture descriptions were adapted from the AddressBook-Level3 project.
* The bundled Montserrat font files are from the [Montserrat Font Project](https://github.com/JulietaUla/Montserrat), licensed under the SIL Open Font License 1.1.
* Icon and image assets were created by the team, except where otherwise stated

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S2-CS2103-T11-4/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2526S2-CS2103-T11-4/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2526S2-CS2103-T11-4/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ResidentListPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2526S2-CS2103-T11-4/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2526S2-CS2103-T11-4/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Resident` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2526S2-CS2103-T11-4/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

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
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g., during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2526S2-CS2103-T11-4/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Resident` objects (which are contained in a `UniqueResidentList` object).
* stores the currently 'selected' `Resident` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Resident>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPrefs` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPrefs` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### Storage component

**API** : [`Storage.java`](https://github.com/AY2526S2-CS2103-T11-4/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefsStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

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
2.  System displays all Residents, clearing any active search filter and restoring the default list order.

     Use case ends.

**Extensions**

* 2a. System has no Residents to display.
    * 2a1. System prompts User with a message to indicate that the list of Residents is empty.

       Use case ends.

<br>

**Use Case: UC3 - Add a new Resident**

**Precondition: User has started the application**

**MSS**

1. User enters the new Resident's information via the command line, optionally including a role.
2. System validates the entered information.
3. System checks that the new Resident does not duplicate an existing Resident, phone number, or unit number.
4. System adds the new Resident to the list of Residents.
5. System saves the updated list of Residents to Storage.
6. System displays the updated list of Residents.

   Use case ends.

**Extensions**

* 3a. There is missing or invalid entered information.
    * 3a1. System prompts User with a message of the error.
    * 3a2. User reenters the information and resubmits via the command line.
    * 3a3. System validates the entered information.

      Steps 3a1-3a3 are repeated until the information entered is correct.

      Use case resumes from step 4.

* 3b. The new Resident would duplicate an existing Resident, phone number, or unit number.
    * 3b1. System prompts User with a message of the error.
    * 3b2. User reenters the information and resubmits via the command line.
    * 3b3. System validates the entered information.
    * 3b4. System checks that the new Resident does not duplicate an existing Resident, phone number, or unit number.

      Steps 3b1-3b4 are repeated until the information entered is valid and unique.

      Use case resumes from step 4.

<br>

**Use Case: UC4 - Delete a Resident**

**Precondition: User has started the application and the Resident to be deleted is in the list of Residents**

**MSS**
1. User <u>requests for the list of Residents (UC2)</u>.
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
1. User <u>requests for the list of Residents (UC2).</u>
2. User requests to copy resident information.
3. System copies all displayed residents' information (names, phone numbers, unit numbers, roles) to the device's clipboard.
4. System displays a confirmation message.

   Use case ends.

**Extensions**
* 1a. User has applied a search filter using find command.
    * 1a1. User requests to copy resident information.
    * 1a2. System copies only the residents matching the search results to the device's clipboard.
    
      Use case resumes from step 4.

<br>

**Use Case: UC6 - Navigate to an Earlier Input**

**Precondition: User has started the application**

**MSS**
1. User requests to navigate to an earlier input.
2. System displays the earlier input.

    Steps 1-2 are repeated as long as the user continues to request to navigate to an earlier input.

   Use case ends.

**Extensions**
* 1a. Input history is empty.
    * 1a1. Nothing changes.

      Use case ends.

* 1b. User is already at the earliest input.
    * 1b1. Nothing changes.

      Use case ends.

<br>

**Use Case: UC7 - Navigate to a Later Input**

**Precondition: User has started the application**

**MSS**
1. User requests to navigate to a later input.
2. System displays the later input.

   Steps 1-2 are repeated as long as the user continues to request to navigate to a later input.

   Use case ends.

**Extensions**
* 1a. Input history is empty.
    * 1a1. Nothing changes.

      Use case ends.

* 1b. User is already at the latest input in history.
    * 1b1. System exits input history navigation.

       Use case ends.

* 1c. User is already at the present input (i.e. not navigating history).
    * 1c1. Nothing changes.

      Use case ends.

<br>

**Use Case: UC8 - Find Residents by Criteria**

**Precondition: User has started the application**

**MSS**
1. User enters one or more prefixed search criteria.
2. System validates the search criteria.
3. System filters the list of Residents to those matching the specified criteria.
4. System displays the filtered list of matching Residents.

   Use case ends.

**Extensions**
* 3a. The search criteria are invalid, missing, or unprefixed.
    * 3a1. System prompts User with a message of the error.
    * 3a2. User reenters the search criteria and resubmits via command line.
    * 3a3. System validates the search criteria.

      Steps 3a1-3a3 are repeated until the search criteria entered are valid.

      Use case resumes from step 3.

* 4a. No Residents match the specified criteria.
    * 4a1. System displays an empty filtered list.
    * 4a2. System displays a message indicating that no Residents were found.

      Use case ends.

<br>

**Use Case: UC9 - Sort the Displayed Resident List**

**Precondition: User has started the application**

**MSS**
1. User requests to sort the currently displayed list of Residents by a specified field.
2. System validates the sort field.
3. System sorts the currently displayed list of Residents by the specified field.
4. System displays the sorted list of Residents.

   Use case ends.

**Extensions**
* 3a. The sort field is invalid or missing.
    * 3a1. System prompts User with a message of the error.
    * 3a2. User reenters the sort command and resubmits via command line.
    * 3a3. System validates the sort field.

      Steps 3a1-3a3 are repeated until the sort field entered is valid.

      Use case resumes from step 3.

* 3b. No Residents are currently displayed.
    * 3b1. System prompts User with a message indicating that there are no Residents to sort.

      Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 residents without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The GUI shall function correctly on screen resolutions **1920x1080 and above at 100% and 125% scaling** and remain usable on screen resolutions **1280x720 and above at 150% scaling**, although the user experience may not be optimal. 
5.  All application data shall be stored locally in human-editable text files.
6.  The system shall not use a DBMS for data storage.
7.  The system shall primarily follow the object-oriented programming paradigm.
8.  The system shall evolve from the provided existing codebase, with changes implemented incrementally while maintaining a working system.
9. The application shall be packaged into a single executable JAR file.
10. The final packaged application shall not exceed 100MB in size.
11.  The system shall function without depending on a remote server
12. The application shall not require users to install additional software dependencies
13. The User Guide and Developer Guide shall be PDF-friendly and avoid interactive elements such as expandable panels or embedded videos.

### Glossary

* **GUI**: A visual interface that allows users to interact with the application using graphical elements such as windows, buttons, icons, and menus instead of typing commands.
* **CLI**: A text-based interface that allows users to interact with the application by typing commands.
* **JAR file**: A packaged file format used to distribute Java applications. It bundles compiled Java classes and resources into a single executable file.
* **Object-Oriented Programming**: A programming paradigm based on organizing code into objects that contain data and behavior.
* **Resident**: A resident whose record is managed by the application.
* **Resident Assistants (RAs)**: The primary target user of the application, responsible for resident welfare and administrative follow-up.
* **Command**: A text instruction entered by the user to perform an action in the app.
* **Command Word**: The first word in a command that identifies the action to execute, such as `add`, `find`, or `sort`.
* **Prefix**: A marker placed before a command argument to identify the field being supplied, such as `n/` for name, `p/` for phone number, `u/` for unit number, and `r/` for role.
* **Phone Number**: The mobile phone number of the resident.
* **Unit Number**: The dormitory room assigned to the resident.
* **Role**: The special dormitory-related position held by a resident. To maintain data consistency, every resident has a role; if no special appointment is held, the attribute defaults to None. 

    Valid roles include:

  * _None: Default status for residents without specific appointment._
  * HA: House Assistant
  * FH: Floor Head
  * RA: Resident Assistant
* **Command Box**: The UI component where users type their commands.
* **Result Display**: The UI component that shows the result of executing a command, such as success messages, error messages.
* **Resident List Panel**: The UI component that displays the resident information.
* **Displayed Resident List**: The current list of residents shown to the user in the resident list panel.
* **Filtered Resident List**: The subset of residents shown after a command such as `find` applies search criteria.
* **Sort Field**: The resident field used to order the displayed resident list, such as name, phone number, unit number, or role.
* **Natural Ordering**: A sorting method that compares digit sequences by their numeric value instead of comparing each digit as a separate character.
* **Clipboard**: The operating system's temporary storage area for copied text, used by the `copy` command.
* **Data File**: The local file used to store resident data between application sessions. The current default data file is `data/addressbook.json`.
* **Input History**: A feature that allows users to navigate through their previous command inputs.

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

   1. Test case: Double-click the jar file.<br>
      Expected: The GUI opens with a set of sample residents shown in the resident list. The initial window size
      may not be optimum.

2. Saving window preferences

   1. Prerequisites: Launch the application.

   1. Test case: Resize the window to an optimum size. Move the window to a different location. Close the window.
      Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location are retained.

3. Opening the help window

   1. Prerequisites: Launch the application.

   1. Test case: `help`<br>
      Expected: A help window opens. A confirmation message is shown in the status message.

4. Exiting the application

   1. Prerequisites: Launch the application.

   1. Test case: `exit`<br>
      Expected: The application closes.

### Listing residents

Prerequisites for all test cases below: Launch the application with the sample data loaded.

1. Listing all residents from the sample data

   1. Test case: `list`<br>
      Expected: All residents are displayed in the resident list. A success message is shown.
<br><br>
2. Listing all residents after applying a search filter

   1. Prerequisites: Enter `find n/Alex` so that only matching residents are shown.

   2. Test case: `list`<br>
      Expected: The search filter is cleared and all residents are displayed again in the resident list.
      <br><br>
3. Listing all residents after sorting the displayed list

   1. Prerequisites: Enter `sort unit` so that the currently displayed list is no longer in its default order.

   2. Test case: `list`<br>
      Expected: All residents are displayed again in the default list order.
      <br><br>
4. Listing residents when the address book is empty

   1. Prerequisites: Enter `clear`.

   2. Test case: `list`<br>
      Expected: No residents are displayed. A message indicating that no residents were found is shown.
<box type="important" seamless>

**Note:** The `clear` command is destructive and will wipe the data stored. This is intentional. Upon restart, the address book will remain empty. To get back the sample residents, delete the `addressbook.json` and restart the application.

</box>

### Adding a resident

Prerequisites for all test cases below: Launch the application with the sample data loaded.

1. Adding a resident without specifying a role

   1. Test case: `add n/John Doe p/95551234 u/12-1-A`<br>
      Expected: A new resident is added to the resident list. A success message is shown.
   <br><br>

2. Adding a resident with an explicit role

   1. Test case: `add n/Jane Tan p/95551235 u/12-1-B r/FH`<br>
      Expected: A new resident is added to the resident list with role `FH`. A success message is shown.
      <br><br>

3. Adding a resident with a missing required field

   1. Test case: `add n/John Doe p/95551236`<br>
      Expected: No resident is added. Error details for invalid command format are shown.
      <br><br>

4. Adding a resident with an invalid role

   1. Test case: `add n/John Doe p/95551236 u/12-1-C r/INVALID`<br>
      Expected: No resident is added. Error details for invalid role are shown.
      <br><br>

5. Adding a resident with a duplicate phone number

   1. Test case: `add n/John Doe p/87438807 u/12-1-D`<br>
      Expected: No resident is added. Error details indicating a duplicate phone number are shown.
      <br><br>

6. Adding a resident with a duplicate unit number

   1. Test case: `add n/John Doe p/95551237 u/30-1-A`<br>
      Expected: No resident is added. Error details indicating a duplicate unit number are shown.
      <br><br>

7. Adding a resident that duplicates an existing resident

   1. Test case: `add n/Alex Yeoh p/87438807 u/99-9-Z`<br>
      Expected: No resident is added. Error details indicating that the resident already exists are shown.

### Finding residents

Prerequisites for all test cases below: Launch the application with the sample data loaded.

1. Finding residents by name

   1. Test case: `find n/Alex`<br>
      Expected: Only `Alex Yeoh` is displayed in the resident list.
      <br><br>
2. Finding residents by phone number substring

   1. Test case: `find p/388`<br>
      Expected: Only `Alex Yeoh` is displayed in the resident list.
      <br><br>
3. Finding residents by unit number substring

   1. Test case: `find u/20-7`<br>
      Expected: Only `Bernice Yu` is displayed in the resident list.
      <br><br>
4. Finding residents by role

   1. Test case: `find r/RA`<br>
      Expected: Only `Charlotte Oliveiro` is displayed in the resident list.
      <br><br>
5. Finding residents by NONE role

   1. Test case: `find r/NONE`<br>
      Expected: Only residents without an assigned role are displayed. `David Li`, `Irfan Ibrahim`, and
      `Roy Balakrishnan` are shown in the resident list.
      <br><br>
6. Finding residents using multiple criteria

   1. Test case: `find n/Alex r/HA`<br>
      Expected: Only residents matching all specified criteria are displayed. `Alex Yeoh` is shown in the
      resident list.
      <br><br>
7. Finding residents with invalid unprefixed input

   1. Test case: `find Alex`<br>
      Expected: No filtering is applied. Error details for invalid command format are shown.
      <br><br>
8. Finding residents with mixed prefixed and unprefixed input

   1. Test case: `find 388 n/Alex`<br>
      Expected: No filtering is applied. Error details indicating that prefixed and unprefixed terms cannot be
      mixed are shown.
      <br><br>
9. Finding residents with no matches

   1. Test case: `find n/ZZZ`<br>
      Expected: No residents are displayed. A message indicating that `0` residents were listed is shown.
      <br><br>

### Sorting residents

Prerequisites for all test cases below: Launch the application with the sample data loaded.

1. Sorting residents by name

   1. Prerequisites: Enter `sort unit` so that the list is not already in name order.

   2. Test case: `sort name`<br>
      Expected: Residents are displayed in ascending alphabetical order by name: `Alex Yeoh`, `Bernice Yu`,
      `Charlotte Oliveiro`, `David Li`, `Irfan Ibrahim`, `Roy Balakrishnan`.
2. Sorting residents by phone number

   1. Test case: `sort phone`<br>
      Expected: Residents are displayed in ascending order by phone number: `Alex Yeoh`, `David Li`,
      `Irfan Ibrahim`, `Roy Balakrishnan`, `Charlotte Oliveiro`, `Bernice Yu`.
      <br><br>

3. Sorting residents by unit number

   1. Test case: `sort unit`<br>
      Expected: Residents are displayed in ascending natural order by unit number: `Irfan Ibrahim`,
      `Charlotte Oliveiro`, `Bernice Yu`, `David Li`, `Roy Balakrishnan`, `Alex Yeoh`.
      <br><br>

4. Sorting residents by role

   1. Prerequisites: Enter `sort phone` so that the list is not already in role order.

   2. Test case: `sort role`<br>
      Expected: Residents are displayed in ascending role order, with assigned roles shown before unassigned
      residents: `Alex Yeoh`, `Bernice Yu`, `Charlotte Oliveiro`, `David Li`, `Irfan Ibrahim`,
      `Roy Balakrishnan`.
      <br><br>

5. Sorting a filtered resident list

   1. Prerequisites: Enter `find r/NONE` so that only unassigned residents are shown.

   2. Test case: `sort unit`<br>
      Expected: Only the filtered residents remain displayed, sorted by unit number: `Irfan Ibrahim`,
      `David Li`, `Roy Balakrishnan`.

6. Sorting residents with an invalid field

   1. Test case: `sort invalid`<br>
      Expected: No sorting is applied. Error details for invalid command format are shown.
      <br><br>

7. Sorting residents when the list is empty

   1. Prerequisites: Enter `clear`.

   2. Test case: `sort unit`<br>
      Expected: No residents are displayed. A message indicating that there are no residents to sort is shown.

<box type="important" seamless>

**Note:** The `clear` command is destructive and will wipe the data stored. This is intentional. Upon restart, the address book will remain empty. To get back the sample residents, delete the `addressbook.json` and restart the application.

</box>

### Command history navigation

Prerequisites for test cases 1-5 below: Launch the application with the sample data loaded. Ensure the command box
is focused. Execute `list`, then `find n/Alex`, then `sort name` in that order.

1. Navigating to the most recent earlier input

   1. Test case: Press the Up Arrow Key (`↑`) once.<br>
      Expected: `sort name` is shown in the command box.
      <br><br>

2. Navigating through multiple earlier inputs

   1. Test case: Press the Up Arrow Key (`↑`) three times.<br>
      Expected: The command box shows `sort name`, then `find n/Alex`, then `list`.
      <br><br>

3. Navigating past the earliest input

   1. Prerequisites: Press the Up Arrow Key (`↑`) until `list` is shown in the command box.

   2. Test case: Press the Up Arrow Key (`↑`) once more.<br>
      Expected: The command box remains as `list`.
4. Navigating to a later input

   1. Prerequisites: Press the Up Arrow Key (`↑`) twice so that `find n/Alex` is shown in the command box.

   2. Test case: Press the Down Arrow Key (`↓`) once.<br>
      Expected: `sort name` is shown in the command box.

5. Exiting command history navigation at the latest input

   1. Prerequisites: Press the Up Arrow Key (`↑`) once so that `sort name` is shown in the command box.

   2. Test case: Press the Down Arrow Key (`↓`) once.<br>
      Expected: The command box becomes empty.

6. Navigating when input history is empty

   1. Prerequisites: Launch the application with the sample data loaded. Do not execute any commands. Ensure the
      command box is focused.

   2. Test case: Press the Up Arrow Key (`↑`) or the Down Arrow Key (`↓`).<br>
      Expected: Nothing changes. The command box remains empty.

### Case-insensitive commands

Prerequisites for all test cases below: Launch the application with the sample data loaded.

1. Executing a command word in uppercase

   1. Test case: `LIST`<br>
      Expected: The command is accepted. All residents are displayed in the resident list.
      <br><br>

1. Executing a command word in mixed case

   1. Test case: `FiNd n/Alex`<br>
      Expected: The command is accepted. Only `Alex Yeoh` is displayed in the resident list.

### Deleting a resident

Prerequisites for all test cases below: Launch the application with the sample data loaded. Enter `list` so that
multiple residents are displayed in the resident list.

1. Deleting a resident while all residents are being shown

   1. Test case: `delete 1`<br>
      Expected: The first resident is deleted from the list. Details of the deleted resident are shown in the
      status message.
      <br><br>

1. Deleting a resident with an invalid index

   1. Test case: `delete 0`<br>
      Expected: No resident is deleted. Error details are shown in the status message.
      <br><br>

1. Deleting a resident with other invalid inputs

   1. Other incorrect delete commands to try: `delete`, `delete x`, `delete 999`<br>
      Expected: Similar to previous.

### Copying resident information

Prerequisites for test case 1 below: Launch the application with the sample data loaded. Enter `list` so that
multiple residents are displayed in the resident list.

1. Copying all resident information while showing residents

   1. Test case: `copy`<br>
      Expected: All displayed residents' information is copied to the clipboard. A confirmation message is shown in
      the status message.
      <br><br>

1. Copying resident information after filtering the list

   1. Prerequisites: Enter `find n/Alex`.

   1. Test case: `copy`<br>
      Expected: Only residents matching the search results are copied to the clipboard. A confirmation message is
      shown in the status message.

1. Copying resident information when the list is empty

   1. Prerequisites: Enter `clear`.

   1. Test case: `copy`<br>
      Expected: No data is copied. A message indicating that there are no residents to copy is shown in the status
      message.

<box type="important" seamless>

**Note:** The `clear` command is destructive and will wipe the data stored. This is intentional. Upon restart, the address book will remain empty. To get back the sample residents, delete the `addressbook.json` and restart the application.

</box>

### Saving data

1. Saving data after modifying the address book

   1. Prerequisites: Launch the application with the sample data loaded.

   1. Test case: `add n/John Doe p/95551234 u/12-1-A`, then close and reopen the application.<br>
      Expected: `John Doe` is still present in the resident list after reopening the application.

1. Starting the application with a missing data file

   1. Prerequisites: Close the application. Delete `data/addressbook.json` from the application folder.

   1. Test case: Launch the application.<br>
      Expected: The application starts with the sample residents shown in the resident list.

1. Starting the application with a corrupted data file

   1. Prerequisites: Close the application. Open `data/addressbook.json` and replace its contents with invalid JSON
      such as `not json`.

   1. Test case: Launch the application.<br>
      Expected: The application starts with an empty address book.
