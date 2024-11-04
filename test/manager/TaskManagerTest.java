package manager;

import enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TaskManagerTest<T extends TaskManager> {

    T manager;

    protected abstract T getManager();

    Task task;
    Epic epic;
    SubTask subTask;

    @BeforeEach
    void setUp() {
        manager = getManager();
        task = new Task(1, "task", "task", Status.NEW, LocalDateTime.now(), 30);
        epic = new Epic(2, "epic", "epic", Status.NEW, null, null, 0);
        subTask = new SubTask(3, "subTask", "subTask", Status.NEW, 2, LocalDateTime.now().plus(Duration.ofMinutes(40)), 10);
        manager.addTask(task);
        manager.addEpic(epic);
        manager.addSubTask(subTask);
        manager.getTask(1);
        manager.getEpic(2);
        manager.getSubTask(3);
        manager.getTask(1);
    }

    @Test
    void shouldGetTask() {
        List<Task> tasks = manager.getTasks();
        assertEquals(tasks.size(), 1);
    }

    @Test
    void shouldAddTask() {
        Task anotherTask = new Task( 4, "anotherTask", "anotherTask", Status.NEW, LocalDateTime.now().plus(Duration.ofMinutes(120)), 30);
        manager.addTask(anotherTask);
        List<Task> tasks = manager.getTasks();
        assertEquals(tasks.size(), 2);
    }

    @Test
    void shouldAddSubTask() {
        SubTask anotherSubTask = new SubTask( 5, "anotherSubTask", "anotherSubTask", Status.NEW, 2, LocalDateTime.now().plus(Duration.ofMinutes(180)), 10);
        manager.addSubTask(anotherSubTask);
        List<SubTask> subTasks = manager.getSubTasks();
        assertEquals(subTasks.size(), 2);
    }

    @Test
    void shouldAddDifferentTasks() {
        assertEquals(manager.getEpics().size(), 1, "Неверное количество задач.");
        assertEquals(manager.getSubTasks().size(), 1, "Неверное количество задач.");
    }

    @Test
    void shouldGetDifferentTaskById() {
        int taskId = task.getId();
        assertEquals(manager.getTask(taskId), task);
        int epicId = epic.getId();
        assertEquals(manager.getEpic(epicId), epic);
        int subTask1Id = subTask.getId();
        assertEquals(manager.getSubTask(subTask1Id), subTask);
    }

    @Test
    void shouldAddEpic() {
        Epic anotherEpic = new Epic(6,"anotherEpic", "anotherEpic", Status.NEW, null, null, 0);
        manager.addEpic(anotherEpic);
        List<Epic> epics = manager.getEpics();
        assertEquals(epics.size(), 2);
    }

    @Test
    public void shouldUnchangeableFields() {
        Task addedTask = manager.getTasks().getFirst();
        assertEquals(task.getId(), addedTask.getId());
        assertEquals(task.getName(), addedTask.getName());
        assertEquals(task.getDescription(), addedTask.getDescription());
        assertEquals(task.getStatus(), addedTask.getStatus());
    }

    @Test
    public void shouldNotDoublesInHistory() {
        // Проверка того, что история просмотров не хранит дубликаты
        assertEquals(manager.getHistory().size(), 3);
    }

    @Test
    public void shouldDeletedTaskIsDeletedFromHistory() { // Проверка того, что удаляемая задача также удаляется из истории просмотров
        manager.deleteById(1);
        assertEquals(manager.getHistory().size(), 2);
    }

    @Test
    public void shouldDeletingEpicsAndSubTasksAlsoDeletingFromHistory() { // Проверка того, что при удалении эпиков из истории удаляются все эпики и подзадачи
        manager.deleteAllEpics();
        assertEquals(manager.getHistory().size(), 1);
    }

    @Test
    public void epicBoundaryValues() {
        Epic boundaryEpic = new Epic(4,"boundaryEpic", "boundaryEpic", Status.NEW, null, null, 0);
        manager.addEpic(boundaryEpic);
        SubTask boundarySubTask1 = new SubTask(5, "boundarySubTask1", "boundarySubTask1", Status.NEW, 4, LocalDateTime.now(), 10);
        SubTask boundarySubTask2 = new SubTask(6, "boundarySubTask2", "boundarySubTask2", Status.NEW, 4, LocalDateTime.now().plus(Duration.ofMinutes(15)), 10);
        manager.addSubTask(boundarySubTask1);
        manager.addSubTask(boundarySubTask2);
        assertEquals(boundaryEpic.getStatus(), Status.NEW);
        manager.updateSubTask(new SubTask(6, "boundarySubTask2", "boundarySubTask2", Status.IN_PROGRESS, 4, LocalDateTime.now().plus(Duration.ofMinutes(15)), 10));
        assertEquals(boundaryEpic.getStatus(), Status.IN_PROGRESS);
        manager.updateSubTask(new SubTask(5, "boundarySubTask1", "boundarySubTask1", Status.DONE, 4, LocalDateTime.now(), 10));
        manager.deleteById(6);
        assertEquals(boundaryEpic.getStatus(), Status.DONE);
    }

    @Test
    public void checkingIntersectionsTimelines() {
        Task anotherTask = new Task(4,"taskNotInTimeline", "taskNotInTimeline", Status.NEW, LocalDateTime.now().plus(Duration.ofMinutes(10)), 10);
        if (manager.checkTime(anotherTask)) {
            manager.addTask(anotherTask);
        }
        List<Task> tasks = manager.getTasks();
        assertEquals(tasks.size(), 1);
        Task anotherAnotherTask = new Task(5,"taskInTimeline", "taskInTimeline", Status.NEW, LocalDateTime.now().plus(Duration.ofMinutes(60)), 10);
        if (manager.checkTime(anotherAnotherTask)) {
            manager.addTask(anotherAnotherTask);
        }
        tasks = manager.getTasks();
        assertEquals(tasks.size(), 2);
    }
}
