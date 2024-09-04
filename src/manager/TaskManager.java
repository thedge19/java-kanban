package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;

public interface TaskManager {

    // Метод проверки пустоты хэшмапов
    boolean tasksIsEmpty();

    // метод получения списка задач
    ArrayList<Task> getTasks();

    // метод получения списка эпиков
    ArrayList<Epic> getEpics();

    // метод получения списка подзадач
    ArrayList<SubTask> getSubTasks();

    // БЛОК ПОЛУЧЕНИЯ ЗАДАЧИ ПО ИДЕНТИФИКАТОРУ
    // метод получения задачи по идентификатору
    Task returnTask(int taskId);

    // метод получения эпика по идентификатору
    Epic returnEpic(int epicId);

    // метод получения подзадачи по идентификатору
    SubTask returnSubTask(int subTaskId);

    // БЛОК ДОБАВЛЕНИЯ ЗАДАЧ

    // метод проверки наличия эпика перед созданием подзадачи
    boolean isEpicExist(int epicId);

    // метод добавления задачи
    void createTask(Task task);

    // метод добавления эпика
    void createEpic(Epic epic);

    // метод добавления подзадачи
    void createSubTask(SubTask subTask);

    // Возвращение в main типа задачи
    String checkTypeById(int taskId);

    // метод изменения задачи
    void updateTask(Task task);

    // Метод изменения эпика
    void updateEpic(Epic epic);

    // метод изменения подзадачи
    void updateSubTask(SubTask subTask);

    // БЛОК УДАЛЕНИЯ ПО ИДЕНТИФИКАТОРУ
    // метод выбора задачи по типу
    void deleteById(int deleteId);

    // БЛОК УДАЛЕНИЯ ВСЕХ ЗАДАЧ
    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubTasks();

    // БЛОК ПОЛУЧЕНИЯ ВСЕХ ПОДЗАДАЧ ЭПИКА
    ArrayList<SubTask> returnSubTaskList(int epicId);

    void createTasks();
}
