# Note Manager

A Spring Boot application designed to help with **notes management**. This project is a setup for a fully-featured notes management application, offering core functionalities like creating, reading, updating, and deleting notes.
The purpose of this application is to provide a backend service for managing notes, which can be further developed into a comprehensive note management system with persistence, authentication, and additional features.

---

## Main Features

- **In-Memory Note Management**: Notes are stored in a concurrent map for thread-safe operations.
- **CRUD Operations**:
    - **List All Notes**: Retrieve all existing notes.
    - **Get Note by ID**: Fetch a specific note using its unique ID.
    - **Create Note**: Add a new note with an auto-generated ID.
    - **Update Note**: Modify an existing note.
    - **Delete Note**: Remove a note by ID.
- **Thread-Safe ID Generation**: Ensures unique note identifiers using an atomic counter.

---

## Technologies Used

- **Java 21**: Utilized for its modern features and enhanced performance.
- **Spring Boot 3.3.5**: Simplifies application development with embedded server and configuration support.
- **Lombok**: Reduces boilerplate code with annotations like `@Builder`, `@Getter`, and `@RequiredArgsConstructor`.
- **JUnit 5 & Mockito**: Provides a robust framework for writing unit and integration tests.
- **ConcurrentHashMap**: Offers thread-safe storage for managing notes in memory.

---

## Getting Started

### Prerequisites

- **Java 21**: Ensure Java 21 is installed on your system.
- **Gradle**: This project uses Gradle for dependency management and build tasks.

### Installation

1. Clone the repository:
```shell
git clone git@github.com:ruslanaprus/goit-academy-dev-hw14.git
cd goit-academy-dev-hw14
```
2. Build the project:
```shell
./gradlew clean build
```
3. Run the application:
```shell
./gradlew bootRun
```

---

## Project Structure

- **`com.example.notemanager`**: Main entry point for the application.
- **`model`**: Defines the `Note` class, the core entity of the application.
- **`service`**: Contains `NoteService` for managing CRUD operations.
- **`config`**: Provides application-level beans (e.g., `notesMap`).
- **`util`**: Includes utility classes like `IdGenerator`.

---

## Future Enhancements

- **Database Integration**: Replace in-memory storage with a database.
- **REST API**: Add controllers to expose note management functionalities via HTTP.
- **Authentication**: Implement user authentication and authorization.
- **Frontend Integration**: Develop a user-friendly UI for managing notes.