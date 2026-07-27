package taskmanager

// Enums

enum class Priority {
    LOW, MEDIUM, HIGH;

    // Returns a display-friendly label for each priority level
    fun label(): String = when (this) {
        LOW    -> "[ Low    ]"
        MEDIUM -> "[ Medium ]"
        HIGH   -> "[ High   ]"
    }
}

enum class Status {
    PENDING, IN_PROGRESS, COMPLETED;

    // Returns a display-friendly label for each status
    fun label(): String = when (this) {
        PENDING     -> "[ Pending     ]"
        IN_PROGRESS -> "[ In Progress ]"
        COMPLETED   -> "[ Completed   ]"
    }
}

// Represents a single task. val fields are set once at creation; var fields can be updated.
data class Task(
    val id: Int,               // immutable – set once at creation
    var title: String,         // mutable – can be updated by the user
    var description: String,   // mutable
    var priority: Priority,    // mutable
    var status: Status         // mutable
) {
    // Returns a formatted multi-line string with all task details
    fun display(): String =
        "  ID          : $id\n" +
        "  Title       : $title\n" +
        "  Description : $description\n" +
        "  Priority    : ${priority.label()}\n" +
        "  Status      : ${status.label()}"
}
