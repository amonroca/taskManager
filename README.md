# Task Manager – Kotlin

A command-line Task Manager application written in Kotlin for CSE 310.

## Kotlin Features Demonstrated

| Requirement                 | Where                                                                                                                                                         |
| --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Immutable variables (`val`) | `Main.kt`, `TaskManager.kt`, `Task.kt` – every local constant                                                                                                 |
| Mutable variables (`var`)   | `Main.kt` `running` flag; `TaskManager.kt` `nextId` counter; `Task` mutable properties                                                                        |
| Expressions                 | String templates throughout; `if` as an expression in `showStatistics()`; single-expression functions                                                         |
| Conditionals                | `if/else` guards in `addNewTask()`, `updateTask()`, `deleteTask()`                                                                                            |
| Loops                       | `while` main loop in `main()`; `for` loops in `listAllTasks()`, `showStatistics()`, `TaskManager.getStatistics()`                                             |
| Functions                   | All top-level functions in `Main.kt`; member functions in `TaskManager`                                                                                       |
| Classes                     | `TaskManager` class (`TaskManager.kt`)                                                                                                                        |
| **Data classes**            | `Task` data class (`Task.kt`)                                                                                                                                 |
| **Collections**             | `MutableList<Task>` and `MutableMap<String,Int>` created and modified in `TaskManager.kt`                                                                     |
| **`when` keyword**          | Menu dispatch (statement); `promptPriority/Status` (expression returning nullable); range branches in `showStatistics()`; argument-less form as if-else chain |

## Project Structure

```
taskManager/
├── build.gradle.kts
├── settings.gradle.kts
└── src/main/kotlin/taskmanager/
    ├── Task.kt          # Priority/Status enums + Task data class
    ├── TaskManager.kt   # Collection management class
    └── Main.kt          # Entry point, UI functions
```

## Prerequisites

- JDK 17+
- Gradle 8+ (`gradle` on your PATH) **or** use the wrapper (see below)

## Build & Run

```bash
# 1. Generate the Gradle wrapper (one-time setup)
gradle wrapper

# 2. Build a fat JAR
./gradlew jar          # Windows: gradlew.bat jar

# 3. Run the application
./gradlew run          # Windows: gradlew.bat run
```
