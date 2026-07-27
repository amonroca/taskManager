package taskmanager

// Entry point – initializes the manager, loads sample tasks, and runs the menu loop
fun main() {
    val appTitle  = "KOTLIN TASK MANAGER"
    val separator = "=".repeat(45)

    println("+$separator+")
    println("|  $appTitle${" ".repeat(separator.length - appTitle.length - 1)}|")
    println("+$separator+")

    val manager = TaskManager()
    seedTasks(manager)
    println("  Loaded ${manager.taskCount} sample tasks.\n")

    // Controls whether the menu loop keeps running
    var running = true

    while (running) {
        printMainMenu()
        val input = readLine()?.trim() ?: ""

        when (input) {
            "1" -> listAllTasks(manager)
            "2" -> addNewTask(manager)
            "3" -> updateTask(manager)
            "4" -> deleteTask(manager)
            "5" -> filterTasks(manager)
            "6" -> showStatistics(manager)
            "0" -> {
                println("\n  Goodbye!")
                running = false
            }
            else -> println("  Invalid option. Please try again.")
        }
    }
}

// Populates the manager with a set of sample tasks at startup
fun seedTasks(manager: TaskManager) {
    manager.addTask("Buy groceries",         "Milk, eggs, bread, and coffee",          Priority.LOW)
    manager.addTask("Complete Kotlin module", "Finish the CSE 310 task manager project", Priority.HIGH)
    manager.addTask("Schedule doctor visit", "Annual checkup is overdue",              Priority.MEDIUM)
    manager.addTask("Pay bills",             "Electricity and internet due soon",       Priority.HIGH)
    manager.addTask("Read Clean Code",       "Resume reading from chapter 5",           Priority.LOW)
}

// Menu

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

// Prints all tasks, or a message when the list is empty
fun listAllTasks(manager: TaskManager) {
    val tasks = manager.getAllTasks()
    println("\n  ─── All Tasks (${tasks.size}) ───")

    if (tasks.isEmpty()) {
        println("  No tasks found.")
        return
    }

    for (task in tasks) {
        println()
        println(task.display())
    }
}

// Prompts for task details and adds a new task to the manager
fun addNewTask(manager: TaskManager) {
    println("\n  ─── Add New Task ───")

    print("  Title: ")
    val title = readLine()?.trim() ?: ""

    if (title.isBlank()) {
        println("  Title cannot be empty.")
        return
    }

    print("  Description: ")
    val description = readLine()?.trim() ?: ""

    val priority = promptPriority() ?: return

    val task = manager.addTask(title, description, priority)
    println("  Task #${task.id} '${task.title}' added successfully.")
}

// Lets the user select a task and change its title, status, or priority
fun updateTask(manager: TaskManager) {
    println("\n  ─── Update Task ───")
    listAllTasks(manager)

    print("\n  Enter task ID to update: ")
    val id = readLine()?.trim()?.toIntOrNull()

    if (id == null) { println("  Invalid ID."); return }

    val task = manager.getTaskById(id)
    if (task == null) { println("  Task #$id not found."); return }

    println("\n  Current task:\n${task.display()}")
    println()
    println("  What to update?  1. Title   2. Status   3. Priority")
    print("  Select (1-3): ")

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

// Asks for a task ID and removes it from the manager
fun deleteTask(manager: TaskManager) {
    println("\n  ─── Delete Task ───")
    listAllTasks(manager)

    print("\n  Enter task ID to delete: ")
    val id = readLine()?.trim()?.toIntOrNull()

    if (id == null) { println("  Invalid ID."); return }

    if (manager.deleteTask(id)) {
        println("  Task #$id deleted.")
    } else {
        println("  Task #$id not found.")
    }
}

// Filters tasks by status or priority, or sorts them by priority
fun filterTasks(manager: TaskManager) {
    println("\n  ─── Filter / Sort Tasks ───")
    println("  1. Filter by Status")
    println("  2. Filter by Priority")
    println("  3. Sort by Priority (High → Low)")
    print("  Select (1-3): ")

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

// Shows task counts per status, a progress bar, and a motivational message
fun showStatistics(manager: TaskManager) {
    println("\n  ─── Task Statistics ───\n")

    val total = manager.taskCount
    val stats = manager.getStatistics()

    for ((statusName, count) in stats) {
        val pct    = if (total > 0) count * 100 / total else 0
        val filled = pct / 5
        val bar    = "█".repeat(filled) + "░".repeat(20 - filled)
        println("  ${statusName.padEnd(14)}: ${count.toString().padStart(2)} task(s)  $bar  ($pct%)")
    }

    println()

    val completedCount = stats["COMPLETED"] ?: 0
    val completionRate = if (total > 0) completedCount * 100 / total else 0

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

    val urgency = when {
        stats["HIGH"] ?: 0 > 0  && stats["PENDING"] ?: 0 > 0  -> "You have HIGH priority tasks pending — act now!"
        stats["MEDIUM"] ?: 0 > 0                               -> "Some MEDIUM priority tasks still need attention."
        else                                                    -> "No urgent tasks. Well managed!"
    }
    println("  Urgency note    : $urgency")
}

// Asks the user to pick a Priority; returns null if input is invalid
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

// Asks the user to pick a Status; returns null if input is invalid
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
