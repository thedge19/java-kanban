package manager;

import enums.Status;
import org.junit.jupiter.api.Test;
import tasks.Task;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class InMemoryTaskManagerTest {

    @Test
    public void addNewTask() {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task(1, "Test addNewTask", "Test addNewTask description", Status.NEW);
        taskManager.createTask(task);
        int taskId = task.getId();

        final Task savedTask = taskManager.returnTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }
}