package manager;

import enums.Status;
import org.junit.jupiter.api.Test;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    TaskManager testManager = new InMemoryTaskManager();
    Task task;

    @Test
        public void shouldSavePreviousVersion() {
        testManager = Managers.getDefault();

        task = new Task(1, "Test addNewTask", "Test addNewTask description",Status.NEW);
        testManager.createTask(task);

        testManager.getTask(task.getId());
        testManager.getTask(task.getId());

        Task viewedTask1 = testManager.getHistory().getFirst();
        Task viewedTask2 = testManager.getHistory().get(1);

        assertEquals(viewedTask1, viewedTask2);
    }
}