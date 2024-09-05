package manager;

import enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagersTest {

    TaskManager tm;

    private Task task;
    private Epic epic;
    private SubTask subTask1;
    private SubTask subTask2;

    @BeforeEach
    public void beforeEach() {
        tm = Managers.getDefault();
        task = new Task(1, "Test addNewTask", "Test addNewTask description", Status.NEW);
        epic = new Epic(2, "Test addNewEpic", "Test addNewEpic description", Status.NEW, new ArrayList<>());
        subTask1 = new SubTask(3, "Test addNewSubTask", "Test addNewSubTask description", Status.NEW, 2);
        subTask2 = new SubTask(4, "Test addNewSubTask", "Test addNewSubTask description", Status.NEW, 2);
        tm.createTask(task);
        tm.createEpic(epic);
        tm.createSubTask(subTask1);
        tm.createSubTask(subTask2);
    }

    @Test
    public void shouldManagerCreateTask() {
        assertEquals(tm.getTasks().size(), 1, "Неверное количество задач.");
        assertEquals(tm.getTasks().getFirst(), task, "Задача не найдена");
        assertEquals(tm.getEpics().size(), 1, "Неверное количество задач.");
        assertEquals(tm.getEpics().getFirst(), epic, "Задача не найдена");
        assertEquals(tm.getSubTasks().size(), 2, "Неверное количество задач.");
        assertEquals(tm.getSubTasks().getFirst(), subTask1, "Задача не найдена");
        assertEquals(tm.getSubTasks().get(1), subTask2, "Задача не найдена");
    }

    @Test
    public void shouldManagerUpdateAndDeleteTask() {
        Task updatedTask = new Task(1, "Test updateNewTask", "Test updateNewTask description", Status.IN_PROGRESS);
        tm.updateTask(updatedTask);
        ArrayList<Integer> subTaskIds = new ArrayList<>();
        subTaskIds.add(3);
        subTaskIds.add(4);
        Epic updatedEpic = new Epic(2, "Test updateNewEpic", "Test updateNewEpic description", Status.NEW, subTaskIds);
        tm.updateEpic(updatedEpic);
        SubTask updatedSubTask1 = new SubTask(3, "Test updateNewSubTask1", "Test updateNewSubTask1 description", Status.IN_PROGRESS, 2);
        SubTask updatedSubTask2 = new SubTask(4, "Test updateNewSubTask2", "Test updateNewSubTask2 description", Status.DONE, 2);
        tm.updateSubTask(updatedSubTask1);
        tm.updateSubTask(updatedSubTask2);

        assertEquals(tm.getTasks().getFirst().getName(), "Test updateNewTask");
        assertEquals(tm.getEpics().getFirst().getDescription(), "Test updateNewEpic description");
        assertEquals(tm.getSubTasks().getFirst().getId(), 3);
        assertEquals(tm.getSubTasks().get(1).getStatus(), Status.DONE);
        assertEquals(tm.getEpics().getFirst().getStatus(), Status.IN_PROGRESS);

        // проверка удаления по id
        tm.deleteById(1);
        assertEquals(tm.getTasks().size(), 0);

        // проверка обновления статуса эпика после удаления подзадачи
        tm.deleteById(3);
        assertEquals(tm.getEpics().getFirst().getStatus(), Status.DONE);

        // проверка удаления связанных подзадач после удаления эпика
        tm.deleteById(2);
        assertEquals(tm.getSubTasks().size(), 0);

    }

    @Test
    public void shouldManagerDeleteTasks() {
        tm.deleteAllTasks();
        assertEquals(tm.getTasks().size(), 0);

        // проверка очистки списка связанных подазадач эпика после удаления всех подзадач
        tm.deleteAllSubTasks();
        assertEquals(tm.getEpics().getFirst().getSubTasksIds().size(), 0);

        tm.deleteAllEpics();
        assertEquals(tm.getEpics().size(), 0);
    }
}