[![Java CI](https://github.com/AY2526S2-CS2103-T11-4/tp/actions/workflows/gradle.yml/badge.svg)](https://github.com/AY2526S2-CS2103-T11-4/tp/actions/workflows/gradle.yml)

![Ui](docs/images/Ui.png)

# QuickLookup

**QuickLookup** is a desktop application for managing a list of residents, optimized for users who prefer fast keyboard input via a **Command Line Interface (CLI)** while still providing a **Graphical User Interface (GUI)** for visual feedback.

It allows users to quickly **add, remove, and view residents** in a locally stored list.

---

## Target Users

QuickLookup is designed for users who:

- Need to quickly look up residents in a list
- Prefer typing commands instead of navigating through menus
- Want a lightweight desktop application for managing simple resident records

---

## Features

### View All Residents
Users can view the full list of currently registered residents.

### Add Residents
Users can add new residents to the list using a command.

### Delete Residents
Users can remove residents from the list if they are no longer relevant or were added incorrectly.

### Persistent Storage
Resident data is automatically stored, allowing the list to persist between application runs.

---

## How It Works

When the application starts:

1. The system loads resident data from the storage system.
2. If the storage system does not exist, the system automatically creates a new one.
3. The current list of residents is displayed in the GUI.

Users can then interact with the application by typing commands into the command box.

---

## Requirements

- **Java 17**
- Any modern operating system:
  - Windows
  - macOS
  - Linux

No installation is required. Simply download and run the JAR file.

---

## Storage

QuickLookup stores all resident data in a storage system.  
Advanced users may modify the storage directly if necessary.

---

## Project Status

This project is currently under active development as part of a **software engineering course project**. Features will be added incrementally throughout the development process.

---

**Acknowledgements**

* The project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
