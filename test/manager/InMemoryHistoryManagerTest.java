package manager;

import enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    HistoryManager testHistoryManager = new InMemoryHistoryManager();
    TaskManager testManager = new InMemoryTaskManager(testHistoryManager);

    Task task1;
    Task task2;
    Task task3;
    Epic epic1;
    Epic epic2;
    SubTask subTask1;
    SubTask subTask2;
    SubTask subTask3;
    SubTask subTask4;

    @BeforeEach
    public void beforeEach() {
        task1 = new Task(1, "Test addNewTask 1", "Test addNewTask description 1", Status.NEW);
        task2 = new Task(2, "Test addNewTask 2", "Test addNewTask description 2", Status.NEW);
        task3 = new Task(3, "Test addNewTask 3", "Test addNewTask description 3", Status.NEW);
        epic1 = new Epic(4, "Test addNewEpic 1", "Test addNewEpic description 1", Status.NEW);
        epic2 = new Epic(5, "Test addNewEpic 2", "Test addNewEpic description 2", Status.NEW);
        subTask1 = new SubTask(6, "Test addNewSubTask 1", "Test addNewSubTask description 1", Status.NEW, 4);
        subTask2 = new SubTask(7, "Test addNewSubTask 2", "Test addNewSubTask description 2", Status.NEW, 4);
        subTask3 = new SubTask(8, "Test addNewSubTask 3", "Test addNewSubTask description 3", Status.NEW, 5);
        subTask4 = new SubTask(9, "Test addNewSubTask 4", "Test addNewSubTask description 4", Status.NEW, 5);

        // создаём задачи
        testManager.addTask(task1);
        testManager.addTask(task2);
        testManager.addTask(task3);
        testManager.addEpic(epic1);
        testManager.addEpic(epic2);
        testManager.addSubTask(subTask1);
        testManager.addSubTask(subTask2);
        testManager.addSubTask(subTask3);
        testManager.addSubTask(subTask4);

        // создаём историю просмотров
        testManager.getTask(1);
        testManager.getTask(2);
        testManager.getTask(3);
        testManager.getTask(1);
        testManager.getTask(2);
        testManager.getEpic(4);
        testManager.getEpic(5);
        testManager.getEpic(5);
        testManager.getEpic(4);
        testManager.getSubTask(7);
        testManager.getSubTask(8);
        testManager.getSubTask(6);
        testManager.getSubTask(9);
        testManager.getSubTask(6);
        testManager.getSubTask(8);
    }

    @Test
    public void shouldNotDoublesInHistory() { // Проверка того, что история просмотров не хранит дубликаты
        assertEquals(testManager.getHistory().size(), 9);
    }

    @Test
    public void shouldDeletedTaskIsDeletedFromHistory() { // Проверка того, что удаляемая задача также удаляется из истории просмотров
        testManager.deleteById(2);
        assertEquals(testManager.getHistory().size(), 8);
    }

    @Test
    public void shouldDeletingEpicsAndSubTasksAlsoDeletingFromHistory() { // Проверка того, что при удалении эпиков из истории удаляются все эпики и подзадачи
        testManager.deleteAllEpics();
        assertEquals(testManager.getHistory().size(), 3);
    }
}