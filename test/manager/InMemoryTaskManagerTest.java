package manager;

import enums.Status;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    public InMemoryTaskManager getManager() {
        return new InMemoryTaskManager(new InMemoryHistoryManager());
    }

    @Override
    @Test
    void shouldGetTask() {
        super.shouldGetTask();
    }

    @Override
    @Test
    void shouldAddTask() {
        super.shouldAddTask();
    }

    @Override
    @Test
    void shouldAddSubTask() {
        super.shouldAddSubTask();
    }

    @Override
    @Test
    void shouldAddEpic() {
        super.shouldAddEpic();
    }

    @Override
    @Test
    void shouldAddDifferentTasks() {
        super.shouldAddDifferentTasks();
    }

    @Override
    @Test
    void shouldGetDifferentTaskById() {
        super.shouldGetDifferentTaskById();
    }

    @Override
    @Test
    public void shouldUnchangeableFields() {
        super.shouldUnchangeableFields();
    }

    @Override
    @Test
    public void epicBoundaryValues() {
        super.epicBoundaryValues();
    }

    @Test
    public void checkingIntersectionsTimelines() {
        Task anotherTask = new Task(4, "taskNotInTimeline", "taskNotInTimeline", Status.NEW, LocalDateTime.now().plus(Duration.ofMinutes(10)), 10);
        if (manager.checkTime(anotherTask)) {
            manager.addTask(anotherTask);
        }
        List<Task> tasks = manager.getTasks();
        assertEquals(tasks.size(), 1);
        Task anotherAnotherTask = new Task(5, "taskInTimeline", "taskInTimeline", Status.NEW, LocalDateTime.now().plus(Duration.ofMinutes(60)), 10);
        if (manager.checkTime(anotherAnotherTask)) {
            manager.addTask(anotherAnotherTask);
        }
        tasks = manager.getTasks();
        assertEquals(tasks.size(), 2);
    }
}
