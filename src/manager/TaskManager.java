package manager;

import node.Node;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface TaskManager {

    // Метод проверки пустоты хэшмапов
    boolean tasksIsEmpty();

    // метод получения списка задач
    List<Task> getTasks();

    // метод получения списка эпиков
    List<Epic> getEpics();

    // метод получения списка подзадач
    List<SubTask> getSubTasks();

    // БЛОК ПОЛУЧЕНИЯ ЗАДАЧИ ПО ИДЕНТИФИКАТОРУ
    // метод получения задачи по идентификатору
    Task getTask(int taskId);

    // метод получения эпика по идентификатору
    Epic getEpic(int epicId);

    // метод получения подзадачи по идентификатору
    SubTask getSubTask(int subTaskId);

    // БЛОК ДОБАВЛЕНИЯ ЗАДАЧ

    // метод проверки наличия эпика перед созданием подзадачи
    boolean isEpicExist(int epicId);

    // метод добавления задачи
    void addTask(Task task);

    // метод добавления эпика
    void addEpic(Epic epic);

    // метод добавления подзадачи
    void addSubTask(SubTask subTask);

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
    List<SubTask> returnSubTaskList(int epicId);

    // БЛОК ПОЛУЧЕНИЯ ИСТОРИИ ПРОСМОТРОВ
    List<Task> getHistory();

    // БЛОК УДАЛЕНИЯ ЗАДАЧ ИЗ ИСТОРИИ ПРОСМОТРОВ
    void removeTaskFromHistory(int id);
}
