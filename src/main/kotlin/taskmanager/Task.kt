package taskmanager

// ─── Enums ───────────────────────────────────────────────────────────────────

// Demonstrates: 'when' as an expression on an enum type
enum class Priority {
    LOW, MEDIUM, HIGH;

    /** Returns a display-friendly label using a 'when' expression. */
    fun label(): String = when (this) {
        LOW    -> "[ Low    ]"
        MEDIUM -> "[ Medium ]"
        HIGH   -> "[ High   ]"
    }
}

// Demonstrates: another 'when' expression returning different string branches
enum class Status {
    PENDING, IN_PROGRESS, COMPLETED;

    /** Returns a display-friendly label using a 'when' expression. */
    fun label(): String = when (this) {
        PENDING     -> "[ Pending     ]"
        IN_PROGRESS -> "[ In Progress ]"
        COMPLETED   -> "[ Completed   ]"
    }
}

// ─── Data Class ──────────────────────────────────────────────────────────────

/**
 * Represents a single task in the task manager.
 *
 * Demonstrates: data class (auto-generates equals, hashCode, toString, copy).
 * Also demonstrates: immutable (val) vs mutable (var) properties.
 */
data class Task(
    val id: Int,               // immutable – set once at creation
    var title: String,         // mutable – can be updated by the user
    var description: String,   // mutable
    var priority: Priority,    // mutable
    var status: Status         // mutable
) {
    /** Builds a formatted multi-line display string (string-template expression). */
    fun display(): String =
        "  ID          : $id\n" +
        "  Title       : $title\n" +
        "  Description : $description\n" +
        "  Priority    : ${priority.label()}\n" +
        "  Status      : ${status.label()}"
}
