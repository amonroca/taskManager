# Overview

I built a command-line Task Manager application in Kotlin to practice and demonstrate core features of the language. My goal was to go beyond simple "hello world" examples and write a real, interactive program that covers the required Kotlin syntax elements: immutable and mutable variables, expressions (including string templates and `if` as an expression), conditionals, loops, functions, classes, data classes, collections, and the `when` keyword.

The program lets you add, list, update, delete, and view statistics for tasks. Each task has a title, description, priority (LOW/MEDIUM/HIGH), and status (TODO/IN_PROGRESS/DONE). All data is managed in memory using a `MutableList` and a `MutableMap`.

My purpose was to gain hands-on experience with Kotlin's type system, null-safety, and expressive syntax, and to understand how it differs from languages I already know.

[Software Demo Video](https://youtu.be/QamAKnlXsHs)

# Development Environment

- **IDE:** Visual Studio Code with the Kotlin extension
- **Build tool:** Gradle 8 (Kotlin DSL – `build.gradle.kts`)
- **JDK:** OpenJDK 20
- **Language:** Kotlin 2.0.0 (JVM target)
- **Libraries:** Kotlin standard library only (no third-party dependencies)

# Useful Websites

- [Kotlin Official Documentation](https://kotlinlang.org/docs/home.html)
- [Kotlin – Classes and Objects](https://kotlinlang.org/docs/classes.html)
- [Kotlin – Collections Overview](https://kotlinlang.org/docs/collections-overview.html)
- [Gradle Kotlin DSL Primer](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- [Baeldung – Kotlin Tutorials](https://www.baeldung.com/kotlin)

# Future Work

- Persist tasks to a JSON or CSV file so data survives between sessions
- Add due-date support with sorting and overdue warnings
- Implement a search/filter command to find tasks by keyword or status
