package taskmanager

// Manages a list of tasks and exposes CRUD operations on them
class TaskManager {

    private val tasks: MutableList<Task> = mutableListOf()

    // Auto-incremented ID assigned to each new task
    private var nextId: Int = 1

    // Returns the current number of tasks in the list
    val taskCount: Int get() = tasks.size

    // Adds a new task with PENDING status and returns it
    fun addTask(title: String, description: String, priority: Priority): Task {
        val task = Task(
            id          = nextId++,
            title       = title,
            description = description,
            priority    = priority,
            status      = Status.PENDING
        )
        tasks.add(task)
        return task
    }

    // Returns an immutable snapshot of all tasks
    fun getAllTasks(): List<Task> = tasks.toList()

    // Returns the task with the given id, or null if not found
    fun getTaskById(id: Int): Task? = tasks.find { it.id == id }

    // Changes the title of the task with the given id; returns false if not found
    fun updateTaskTitle(id: Int, newTitle: String): Boolean {
        val task = getTaskById(id) ?: return false
        task.title = newTitle
        return true
    }

    // Changes the status of the task with the given id; returns false if not found
    fun updateTaskStatus(id: Int, newStatus: Status): Boolean {
        val task = getTaskById(id) ?: return false
        task.status = newStatus
        return true
    }

    // Changes the priority of the task with the given id; returns false if not found
    fun updateTaskPriority(id: Int, newPriority: Priority): Boolean {
        val task = getTaskById(id) ?: return false
        task.priority = newPriority
        return true
    }

    // Removes a task by id; returns true if removed, false if not found
    fun deleteTask(id: Int): Boolean = tasks.removeIf { it.id == id }

    // Returns all tasks with the given status
    fun filterByStatus(status: Status): List<Task> =
        tasks.filter { it.status == status }

    // Returns all tasks with the given priority
    fun filterByPriority(priority: Priority): List<Task> =
        tasks.filter { it.priority == priority }

    // Returns a new list sorted from highest to lowest priority
    fun getSortedByPriority(): List<Task> =
        tasks.sortedByDescending { it.priority.ordinal }

    // Builds and returns a map of status name -> task count
    fun getStatistics(): Map<String, Int> {
        val stats: MutableMap<String, Int> = mutableMapOf()
        for (status in Status.entries) {
            stats[status.name] = tasks.count { it.status == status }
        }
        return stats
    }
}
