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

    @BeforeEach
    public void beforeEach() {
        testManager = Managers.getDefault();
    }

    @Test
    public void addAndGetNewTask() {
        task = new Task(1, "Test addNewTask", "Test addNewTask description", Status.NEW);
        epic = new Epic(2, "Test addNewEpic", "Test addNewEpic description", Status.NEW, new ArrayList<>());
        subTask1 = new SubTask(3, "Test addNewSubTask", "Test addNewSubTask description", Status.NEW, 2);

        // 1. Возможность добавления задач разного типа
        testManager.addTask(task);
        final List<Task> tasks = testManager.getTasks();
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        testManager.addEpic(epic);
        final List<Epic> epics = testManager.getEpics();
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.getFirst()); // наследники класса Task равны друг другу, если равен их id
        testManager.addSubTask(subTask1);
        final List<SubTask> subTasks = testManager.getSubTasks();
        assertEquals(1, subTasks.size(), "Неверное количество задач.");
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
        epic = new Epic(1, "Test addNewEpic", "Test addNewEpic description", Status.NEW, new ArrayList<>());
        testManager.addEpic(epic);

        subTask1 = new SubTask(2, "Test addNewSubTask", "Test addNewSubTask description", Status.NEW, 1);
        subTask2 = new SubTask(3, "Test addNewSubTask", "Test addNewSubTask description", Status.NEW, 1);
        subTask3 = new SubTask(4, "Test addNewSubTask3", "Test addNewSubTask3 description", Status.NEW, 4);


        testManager.addSubTask(subTask1);
        testManager.addSubTask(subTask2);

        // Добавление в качестве связанного эпика своего же id
        testManager.addSubTask(subTask3);

        Epic newEpic = testManager.getEpics().getFirst();
        assertEquals(newEpic.getSubTasksIds().size(), 2);
    }

    @Test
    public void shouldUnchangeableFields() {
        task = new Task(1, "Test addNewTask", "Test addNewTask description", Status.NEW);
        testManager.addTask(task);
        final List<Task> tasks = testManager.getTasks();

        Task addedTask = tasks.getFirst();

        assertEquals(task.getId(), addedTask.getId());
        assertEquals(task.getName(), addedTask.getName());
        assertEquals(task.getDescription(), addedTask.getDescription());
        assertEquals(task.getStatus(), addedTask.getStatus());
    }

    @Test
    public void shouldConflictAddingAndGeneratingIds() {
        // Задача с заданным id
        Task task1 = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        // Задача со сгенерированным id
        Task task2 = new Task(2, "Test addNewTask", "Test addNewTask description", Status.NEW);
        // Задача с заданным id
        Task task3 = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);

        testManager.addTask(task1);
        testManager.addTask(task2);
        testManager.addTask(task3);

        List<Task> savedList = testManager.getTasks();

        assertEquals(savedList.getFirst().getId(), 1);
        assertEquals(savedList.get(1).getId(), 2);
        assertEquals(savedList.get(2).getId(), 3);
    }
}
