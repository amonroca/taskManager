package taskmanager

/**
 * Manages a mutable collection of Tasks.
 *
 * Demonstrates:
 *  - Classes with private state
 *  - Creating and modifying MutableList and MutableMap collections
 *  - Functions with default / nullable return values
 */
class TaskManager {

    // Demonstrates: creating a mutable collection (MutableList)
    private val tasks: MutableList<Task> = mutableListOf()

    // Mutable counter – incremented each time a task is added
    private var nextId: Int = 1

    // Read-only computed property backed by the list size
    val taskCount: Int get() = tasks.size

    // ── Create ────────────────────────────────────────────────────────────────

    /** Adds a new PENDING task and returns it. */
    fun addTask(title: String, description: String, priority: Priority): Task {
        // Immutable local variable
        val task = Task(
            id          = nextId++,
            title       = title,
            description = description,
            priority    = priority,
            status      = Status.PENDING
        )
        tasks.add(task)   // modifying the collection
        return task
    }

    // ── Read ──────────────────────────────────────────────────────────────────

    /** Returns an immutable snapshot of all tasks. */
    fun getAllTasks(): List<Task> = tasks.toList()

    /** Returns the task with the given id, or null if none exists. */
    fun getTaskById(id: Int): Task? = tasks.find { it.id == id }

    // ── Update ────────────────────────────────────────────────────────────────

    /** Updates the title of a task. Returns false when the id is not found. */
    fun updateTaskTitle(id: Int, newTitle: String): Boolean {
        val task = getTaskById(id) ?: return false
        task.title = newTitle
        return true
    }

    /** Updates the status of a task. Returns false when the id is not found. */
    fun updateTaskStatus(id: Int, newStatus: Status): Boolean {
        val task = getTaskById(id) ?: return false
        task.status = newStatus
        return true
    }

    /** Updates the priority of a task. Returns false when the id is not found. */
    fun updateTaskPriority(id: Int, newPriority: Priority): Boolean {
        val task = getTaskById(id) ?: return false
        task.priority = newPriority
        return true
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    /** Removes a task by id. Returns true if a task was removed, false otherwise. */
    fun deleteTask(id: Int): Boolean = tasks.removeIf { it.id == id }

    // ── Filter / Sort ─────────────────────────────────────────────────────────

    /** Returns all tasks that match the given status. */
    fun filterByStatus(status: Status): List<Task> =
        tasks.filter { it.status == status }

    /** Returns all tasks that match the given priority. */
    fun filterByPriority(priority: Priority): List<Task> =
        tasks.filter { it.priority == priority }

    /** Returns tasks sorted from highest priority to lowest. */
    fun getSortedByPriority(): List<Task> =
        tasks.sortedByDescending { it.priority.ordinal }

    // ── Statistics ────────────────────────────────────────────────────────────

    /**
     * Builds a map of Status.name -> task count.
     *
     * Demonstrates: creating and populating a MutableMap, iterating with a for loop.
     */
    fun getStatistics(): Map<String, Int> {
        // Demonstrates: creating a MutableMap collection
        val stats: MutableMap<String, Int> = mutableMapOf()

        // Demonstrates: for loop over enum entries to populate the map
        for (status in Status.entries) {
            stats[status.name] = tasks.count { it.status == status }
        }
        return stats   // returned as the immutable Map interface
    }
}
