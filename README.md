# Note Manager

A Spring Boot application designed to help with **notes management**. This project is a setup for a fully-featured notes management application, offering core functionalities like creating, reading, updating, and deleting notes.
The purpose of this application is to provide a backend service for managing notes, which can be further developed into a comprehensive note management system with persistence, authentication, and additional features.

This project is an enhanced version of the [Note Manager](https://github.com/ruslanaprus/goit-academy-dev-hw14), and it has an improved architecture for better maintainability and scalability. The `INoteService` interface is used to make the service layer more testable and extendable.
Similarly, the introduction of the `INoteRepository` interface ensures flexibility in switching between different data storage solutions, such as in-memory or database options.

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
- **Web layer**: Manages HTTP requests for the note management functionality. Supports endpoints for listing, updating, and deleting notes.
- **Thymeleaf integration**: Added Thymeleaf templates to render data on web pages directly from the backend:
    - **List All Notes**: Displays all notes.
    - **Edit Note**: Form to update a note.
    - **Error page**: Displays user-friendly error messages for invalid inputs or missing resources.

---

## Technologies Used

- **Java 21**: Utilized for its modern features and enhanced performance.
- **Spring Boot 3.3.5**: Simplifies application development with embedded server and configuration support.
- **Lombok**: Reduces boilerplate code with annotations like `@Builder`, `@Getter`, and `@RequiredArgsConstructor`.
- **JUnit 5 & Mockito**: Provides a robust framework for writing unit and integration tests.
- **ConcurrentHashMap**: Offers thread-safe storage for managing notes in memory.
- **Thymeleaf**: Template engine for rendering dynamic HTML pages with embedded backend data.

---

## Getting Started

### Prerequisites

- **Java 21**: Ensure Java 21 is installed on your system.
- **Gradle**: This project uses Gradle for dependency management and build tasks.

### Installation

1. Clone the repository:
```shell
git clone git@github.com:ruslanaprus/goit-academy-dev-hw15.git
cd goit-academy-dev-hw15
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
- **`repository`**: Defines repository operations, allows easy integration with various data sources.
- **`service`**: Contains `NoteService` for managing CRUD operations.
- **`controller`**: Handles web requests for note operations.
- **`exception`**: Includes custom exceptions and the `ExceptionMessages` enum.
- **`util`**: Includes utility classes like `IdGeneratorService` for ID generation.

---

## Future Enhancements

- **Database Integration**: Replace in-memory storage with a database.
- **REST API**: Add controllers to expose note management functionalities via HTTP. Expand endpoints to support pagination, search, and filtering.
- **Authentication**: Implement user authentication and authorization.
