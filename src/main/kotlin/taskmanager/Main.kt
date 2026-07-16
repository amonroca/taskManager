package taskmanager

// ─── Entry Point ─────────────────────────────────────────────────────────────

/**
 * Main entry point for the Task Manager CLI application.
 *
 * Demonstrates: variables (val/var), expressions, conditionals,
 *               loops, functions, classes, data classes,
 *               collections, and the 'when' keyword.
 */
fun main() {
    // Immutable variables (val)
    val appTitle  = "KOTLIN TASK MANAGER"
    val separator = "=".repeat(45)

    println("+$separator+")
    println("|  $appTitle${" ".repeat(separator.length - appTitle.length - 1)}|")
    println("+$separator+")

    val manager = TaskManager()
    seedTasks(manager)
    println("  Loaded ${manager.taskCount} sample tasks.\n")

    // Mutable variable (var) – controls the main loop
    var running = true

    // Demonstrates: while loop
    while (running) {
        printMainMenu()
        val input = readLine()?.trim() ?: ""

        // Demonstrates: 'when' as a statement dispatching on the user's menu choice
        when (input) {
            "1" -> listAllTasks(manager)
            "2" -> addNewTask(manager)
            "3" -> updateTask(manager)
            "4" -> deleteTask(manager)
            "5" -> filterTasks(manager)
            "6" -> showStatistics(manager)
            "0" -> {
                println("\n  Goodbye!")
                running = false            // mutable var modified inside the loop
            }
            else -> println("  Invalid option. Please try again.")
        }
    }
}

// ─── Seed Data ────────────────────────────────────────────────────────────────

/**
 * Populates the manager with sample tasks.
 * Demonstrates: calling a function that modifies a collection repeatedly.
 */
fun seedTasks(manager: TaskManager) {
    manager.addTask("Buy groceries",         "Milk, eggs, bread, and coffee",          Priority.LOW)
    manager.addTask("Complete Kotlin module", "Finish the CSE 310 task manager project", Priority.HIGH)
    manager.addTask("Schedule doctor visit", "Annual checkup is overdue",              Priority.MEDIUM)
    manager.addTask("Pay bills",             "Electricity and internet due soon",       Priority.HIGH)
    manager.addTask("Read Clean Code",       "Resume reading from chapter 5",           Priority.LOW)
}

// ─── Menu ─────────────────────────────────────────────────────────────────────

fun printMainMenu() {
    println()
    println("  +-------------- MENU ----------------+")
    println("  |  1. List all tasks                 |")
    println("  |  2. Add a new task                 |")
    println("  |  3. Update a task                  |")
    println("  |  4. Delete a task                  |")
    println("  |  5. Filter / sort tasks            |")
    println("  |  6. Show statistics                |")
    println("  |  0. Exit                           |")
    println("  +------------------------------------+")
    print("  Enter choice: ")
}

// ─── List Tasks ──────────────────────────────────────────────────────────────

/**
 * Prints every task.
 * Demonstrates: for loop over a list.
 */
fun listAllTasks(manager: TaskManager) {
    val tasks = manager.getAllTasks()
    println("\n  ─── All Tasks (${tasks.size}) ───")

    // Demonstrates: conditional – check for empty list
    if (tasks.isEmpty()) {
        println("  No tasks found.")
        return
    }

    // Demonstrates: for loop iterating over a collection
    for (task in tasks) {
        println()
        println(task.display())
    }
}

// ─── Add Task ─────────────────────────────────────────────────────────────────

/**
 * Prompts the user for new task details and adds it.
 * Demonstrates: conditionals guarding invalid input, string-template expressions.
 */
fun addNewTask(manager: TaskManager) {
    println("\n  ─── Add New Task ───")

    print("  Title: ")
    val title = readLine()?.trim() ?: ""

    // Conditional check
    if (title.isBlank()) {
        println("  Title cannot be empty.")
        return
    }

    print("  Description: ")
    val description = readLine()?.trim() ?: ""

    val priority = promptPriority() ?: return

    val task = manager.addTask(title, description, priority)
    // String-template expression
    println("  Task #${task.id} '${task.title}' added successfully.")
}

// ─── Update Task ──────────────────────────────────────────────────────────────

/**
 * Lets the user choose which field of a task to update.
 * Demonstrates: nested 'when' expressions.
 */
fun updateTask(manager: TaskManager) {
    println("\n  ─── Update Task ───")
    listAllTasks(manager)

    print("\n  Enter task ID to update: ")
    val id = readLine()?.trim()?.toIntOrNull()

    // Conditional – validate the ID
    if (id == null) { println("  Invalid ID."); return }

    val task = manager.getTaskById(id)
    if (task == null) { println("  Task #$id not found."); return }

    println("\n  Current task:\n${task.display()}")
    println()
    println("  What to update?  1. Title   2. Status   3. Priority")
    print("  Select (1-3): ")

    // Demonstrates: 'when' dispatching on sub-menu choice
    when (readLine()?.trim()) {
        "1" -> {
            print("  New title: ")
            val newTitle = readLine()?.trim() ?: ""
            if (newTitle.isBlank()) {
                println("  Title cannot be empty.")
            } else {
                manager.updateTaskTitle(id, newTitle)
                println("  Title updated to '$newTitle'.")
            }
        }
        "2" -> {
            val newStatus = promptStatus() ?: return
            manager.updateTaskStatus(id, newStatus)
            println("  Status updated to ${newStatus.label()}.")
        }
        "3" -> {
            val newPriority = promptPriority() ?: return
            manager.updateTaskPriority(id, newPriority)
            println("  Priority updated to ${newPriority.label()}.")
        }
        else -> println("  Invalid option.")
    }
}

// ─── Delete Task ──────────────────────────────────────────────────────────────

/**
 * Removes a task by ID.
 * Demonstrates: conditional branching on a boolean return value.
 */
fun deleteTask(manager: TaskManager) {
    println("\n  ─── Delete Task ───")
    listAllTasks(manager)

    print("\n  Enter task ID to delete: ")
    val id = readLine()?.trim()?.toIntOrNull()

    if (id == null) { println("  Invalid ID."); return }

    // Conditional – check result of deleteTask()
    if (manager.deleteTask(id)) {
        println("  Task #$id deleted.")
    } else {
        println("  Task #$id not found.")
    }
}

// ─── Filter / Sort ────────────────────────────────────────────────────────────

/**
 * Filters or sorts the task list.
 * Demonstrates: 'when' with multiple branches and forEach loop.
 */
fun filterTasks(manager: TaskManager) {
    println("\n  ─── Filter / Sort Tasks ───")
    println("  1. Filter by Status")
    println("  2. Filter by Priority")
    println("  3. Sort by Priority (High → Low)")
    print("  Select (1-3): ")

    // Demonstrates: 'when' expression choosing a branch based on user input
    when (readLine()?.trim()) {
        "1" -> {
            val status   = promptStatus() ?: return
            val filtered = manager.filterByStatus(status)
            println("\n  ─── ${status.label()} Tasks (${filtered.size}) ───")
            if (filtered.isEmpty()) println("  None found.")
            else filtered.forEach { println(); println(it.display()) }
        }
        "2" -> {
            val priority = promptPriority() ?: return
            val filtered = manager.filterByPriority(priority)
            println("\n  ─── ${priority.label()} Priority Tasks (${filtered.size}) ───")
            if (filtered.isEmpty()) println("  None found.")
            else filtered.forEach { println(); println(it.display()) }
        }
        "3" -> {
            val sorted = manager.getSortedByPriority()
            println("\n  ─── Tasks Sorted High → Low ───")
            sorted.forEach { println(); println(it.display()) }
        }
        else -> println("  Invalid option.")
    }
}

// ─── Statistics ───────────────────────────────────────────────────────────────

/**
 * Displays a summary of task counts and a progress bar.
 *
 * Demonstrates:
 *  - 'when' with exact integer matches
 *  - 'when' with integer ranges (in 1..25, in 26..50, …)
 *  - 'when' without an argument (acting as an if-else chain)
 *  - for loop over a Map
 *  - if as an expression (ternary-style)
 */
fun showStatistics(manager: TaskManager) {
    println("\n  ─── Task Statistics ───\n")

    // Immutable local val
    val total = manager.taskCount
    val stats = manager.getStatistics()

    // Demonstrates: for loop over a Map's entries
    for ((statusName, count) in stats) {
        // Demonstrates: if as an expression (avoids division by zero)
        val pct    = if (total > 0) count * 100 / total else 0
        val filled = pct / 5
        val bar    = "█".repeat(filled) + "░".repeat(20 - filled)
        println("  ${statusName.padEnd(14)}: ${count.toString().padStart(2)} task(s)  $bar  ($pct%)")
    }

    println()

    val completedCount = stats["COMPLETED"] ?: 0
    // Expression: if used inline to compute completion rate
    val completionRate = if (total > 0) completedCount * 100 / total else 0

    // Demonstrates: 'when' with exact-value and range branches
    val message = when (completionRate) {
        0         -> "No tasks completed yet — time to get started!"
        in 1..25  -> "Just getting started. Keep the momentum going!"
        in 26..50 -> "Making solid progress. Keep it up!"
        in 51..75 -> "More than halfway there. Great work!"
        in 76..99 -> "Almost done — finish strong!"
        100       -> "All tasks complete! Outstanding work!"
        else      -> "Unknown completion rate."
    }

    println("  Completion rate : $completionRate%")
    println("  $message")

    println()

    // Demonstrates: 'when' without an argument (used as a compact if-else chain)
    val urgency = when {
        stats["HIGH"] ?: 0 > 0  && stats["PENDING"] ?: 0 > 0  -> "You have HIGH priority tasks pending — act now!"
        stats["MEDIUM"] ?: 0 > 0                               -> "Some MEDIUM priority tasks still need attention."
        else                                                    -> "No urgent tasks. Well managed!"
    }
    println("  Urgency note    : $urgency")
}

// ─── Input Helpers ────────────────────────────────────────────────────────────

/**
 * Prompts the user to pick a Priority.
 * Returns null on invalid input.
 * Demonstrates: 'when' as an expression returning a nullable type.
 */
fun promptPriority(): Priority? {
    println("  Priority:  1. Low   2. Medium   3. High")
    print("  Select (1-3): ")
    return when (readLine()?.trim()) {
        "1"  -> Priority.LOW
        "2"  -> Priority.MEDIUM
        "3"  -> Priority.HIGH
        else -> { println("  Invalid priority."); null }
    }
}

/**
 * Prompts the user to pick a Status.
 * Returns null on invalid input.
 * Demonstrates: 'when' as an expression returning a nullable type.
 */
fun promptStatus(): Status? {
    println("  Status:  1. Pending   2. In Progress   3. Completed")
    print("  Select (1-3): ")
    return when (readLine()?.trim()) {
        "1"  -> Status.PENDING
        "2"  -> Status.IN_PROGRESS
        "3"  -> Status.COMPLETED
        else -> { println("  Invalid status."); null }
    }
}
