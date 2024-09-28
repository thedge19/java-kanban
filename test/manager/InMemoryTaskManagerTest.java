package manager;

import enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryTaskManagerTest {

    TaskManager testManager;
    Epic epic;
    Task task;
    SubTask subTask1;
    SubTask subTask2;
    SubTask subTask3;
    SubTask subTask4;
    List<Task> tasks;
    List<Epic> epics;
    List<SubTask> subTasks;

    @BeforeEach
    public void beforeEach() {
        testManager = Managers.getDefault();
        task = new Task(1, "Test addNewTask", "Test addNewTask description", Status.NEW);
        epic = new Epic(2, "Test addNewEpic", "Test addNewEpic description", Status.NEW, new ArrayList<>());
        subTask1 = new SubTask(3, "Test addNewSubTask", "Test addNewSubTask description1", Status.NEW, 2);
        subTask2 = new SubTask(4, "Test addNewSubTask1", "Test addNewSubTask description2", Status.NEW, 2);
        subTask3 = new SubTask(5, "Test addNewSubTask2", "Test addNewSubTask description3", Status.NEW, 2);
        subTask4 = new SubTask(6, "Test addNewSubTask3", "Test addNewSubTask3 description4", Status.NEW, 6);

        testManager.addTask(task);
        testManager.addEpic(epic);
        testManager.addSubTask(subTask1);
        testManager.addSubTask(subTask2);
        testManager.addSubTask(subTask3);

        tasks = testManager.getTasks();
        epics = testManager.getEpics();
        subTasks = testManager.getSubTasks();
    }

    @Test
    public void addAndGetNewTask() {
        // 1. Возможность добавления задач разного типа
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        testManager.addEpic(epic);
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.getFirst()); // наследники класса Task равны друг другу, если равен их id
        testManager.addSubTask(subTask1);
        assertEquals(3, subTasks.size(), "Неверное количество задач.");
        assertEquals(subTask1, subTasks.getFirst()); // наследники класса Task равны друг другу, если равен их id

        // 2. Возможность получения разных задач по id
        int taskId = task.getId();
        assertEquals(testManager.getTask(taskId), task);
        int epicId = epic.getId();
        assertEquals(testManager.getEpic(epicId), epic);
        int subTask1Id = subTask1.getId();
        assertEquals(testManager.getSubTask(subTask1Id), subTask1);

        final Task savedTask = testManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    public void shouldSubtaskOwnEpic() {
        // Добавление в качестве связанного эпика своего же id
        testManager.addSubTask(subTask4);

        Epic newEpic = testManager.getEpics().getFirst();
        assertEquals(newEpic.getSubTasksIds().size(), 3);
    }

    @Test
    public void shouldUnchangeableFields() {
        Task addedTask = tasks.getFirst();

        assertEquals(task.getId(), addedTask.getId());
        assertEquals(task.getName(), addedTask.getName());
        assertEquals(task.getDescription(), addedTask.getDescription());
        assertEquals(task.getStatus(), addedTask.getStatus());
    }

    @Test
    public void shouldConflictAddingAndGeneratingIds() {
        // Задача со сгенерированным id
        Task task2 = new Task(7, "Test addNewTask", "Test addNewTask description", Status.NEW);
        // Задача с заданным id
        Task task3 = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);

        testManager.addTask(task2);
        testManager.addTask(task3);

        List<Task> savedList = testManager.getTasks();

        assertEquals(savedList.getFirst().getId(), 1);
        assertEquals(savedList.get(1).getId(), 6);
        assertEquals(savedList.get(2).getId(), 7);
    }

    @Test
    public void shouldEpicDoesNotStoreOutdatedSubTaskIDs() {
        testManager.deleteById(4);
        assertEquals(epic.getSubTasksIds().size(), 2);
    }

    @Test
    public void shouldDeletedTasksAreDeletedFromHistory() {
        testManager.getTask(1);
        testManager.getSubTask(3);
        testManager.getSubTask(4);

        assertEquals(testManager.getHistory().size(), 3);
    }

    @Test
    public void shouldDeletedSubtaskDoesNotRemainInEpic() {
        assertEquals(epic.getSubTasksIds().size(), 3);

        testManager.deleteById(4);
        assertEquals(epic.getSubTasksIds().size(), 2);
    }
}
