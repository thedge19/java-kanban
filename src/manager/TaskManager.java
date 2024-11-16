package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

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
    int addTask(Task task);

    // метод добавления эпика
    int addEpic(Epic epic);

    // метод добавления подзадачи
    int addSubTask(SubTask subTask);

    // Возвращение в main типа задачи
    String checkTypeById(int taskId);

    // метод изменения задачи
    int updateTask(Task task);

    // Метод изменения эпика
    int updateEpic(Epic epic);

    // метод изменения подзадачи
    int updateSubTask(SubTask subTask);

    // БЛОК УДАЛЕНИЯ ПО ИДЕНТИФИКАТОРУ
    // метод выбора задачи по типу
    int deleteById(int deleteId);

    // БЛОК УДАЛЕНИЯ ВСЕХ ЗАДАЧ
    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubTasks();

    // БЛОК ПОЛУЧЕНИЯ ВСЕХ ПОДЗАДАЧ ЭПИКА
    List<SubTask> returnSubTaskList(int epicId);

    // БЛОК ПОЛУЧЕНИЯ ИСТОРИИ ПРОСМОТРОВ
    List<Task> getHistory();

    // БЛОК ВРЕМЕНИ
    List<Task> getPrioritizedTasks();
}
