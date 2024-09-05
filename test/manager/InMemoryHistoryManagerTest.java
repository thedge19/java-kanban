package manager;

import enums.Status;
import org.junit.jupiter.api.Test;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    HistoryManager testHistoryManager = new InMemoryHistoryManager();
    Task task;

    @Test
        public void shouldSavePreviousVersion() {
        testHistoryManager = new InMemoryHistoryManager();
        task = new Task(1, "Test addNewTask", "Test addNewTask description",Status.NEW);

        testHistoryManager.addTask(task);
        testHistoryManager.addTask(task);

        Task viewedTask1 = testHistoryManager.getHistory().getFirst();
        Task viewedTask2 = testHistoryManager.getHistory().get(1);

        assertEquals(viewedTask1, viewedTask2);
    }
}