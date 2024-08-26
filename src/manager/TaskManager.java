package manager;

import enums.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();

    private int id = 0;

    // БЛОК ПОЛУЧЕНИЯ СПИСКОВ ЗАДАЧ
    // Метод проверки пустоты хэшмапов
    public boolean tasksIsEmpty() {
        return tasks.isEmpty() && epics.isEmpty() && subTasks.isEmpty();
    }

    // метод получения списка задач
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasksList = new ArrayList<>();
        for (int taskId : tasks.keySet()) {
            tasksList.add(tasks.get(taskId));
        }
        return tasksList;
    }

    // метод получения списка эпиков
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> epicsList = new ArrayList<>();
        for (int epicId : epics.keySet()) {
            epicsList.add(epics.get(epicId));
        }
        return epicsList;
    }

    // метод получения списка подзадач
    public ArrayList<SubTask> getSubTasks() {
        ArrayList<SubTask> subTasksList = new ArrayList<>();
        for (int subTaskId : subTasks.keySet()) {
            subTasksList.add(subTasks.get(subTaskId));
        }
        return subTasksList;
    }

    // БЛОК ПОЛУЧЕНИЯ ЗАДАЧИ ПО ИДЕНТИФИКАТОРУ
    // метод получения задачи по идентификатору
    public Task showTask(int id) {
        return tasks.get(id);
    }

    // метод получения эпика по идентификатору
    public Epic showEpic(int id) {
        return epics.get(id);
    }

    // метод получения подзадачи по идентификатору
    public SubTask showSubTask(int id) {
        return subTasks.get(id);
    }

    // БЛОК ДОБАВЛЕНИЯ ЗАДАЧ
    // метод добавления задачи
    public void constructTask(Task task) {
        id += 1;
        tasks.put(id, task);
    }

    // метод проверки наличия эпика перед созданием подзадачи
    public boolean isEpicExist(int epicId) {
        return epics.get(epicId) != null;
    }

    // метод добавления эпика
    public void constructEpic(Epic epic) {
        id += 1;
        epics.put(id, epic);
    }

    // метод добавления подзадачи
    public void constructSubTask(SubTask subTask) {
        id += 1;
        int epicId = subTask.getEpicId();
        epics.get(epicId).getSubTasksIds().add(id); // добавление идентификатора подзадачи в список связанного эпика
        subTasks.put(id, subTask);
    }


    // БЛОК ОБНОВЛЕНИЯ ЗАДАЧ
    // Возвращение в main типа задачи
    public String checkTypeById(int taskId) {
        if (tasks.get(taskId) != null) {
            return "task";
        } else if (epics.get(taskId) != null) {
            return "epic";
        } else if (subTasks.get(taskId) != null) {
            return "subTask";
        } else {
            return "unknownDataType";
        }
    }

    // метод изменения задачи
    public void updateTask(int taskId, Task task) {
        tasks.put(taskId, task);
    }

    // Метод изменения эпика
    public void updateEpic(int epicId, Epic epic) {
        tasks.put(epicId, epic);
    }

    // метод изменения подзадачи
    public void updateSubTask(int subTaskId, SubTask subTask) {
        int updateEpicId = subTask.getEpicId();
        subTasks.put(subTaskId, subTask);
        updateEpicStatus(updateEpicId);
    }

    // метод обновления статуса эпика после обновления статуса подзадачи
    private void updateEpicStatus(int updateEpicId) {
        Epic epic = epics.get(updateEpicId);
        ArrayList<Integer> epicSubTasksArray = epic.getSubTasksIds();
        int inProgressCounter = 0;
        int doneCounter = 0;
        int newCounter = 0;
        for (int subTaskId : epicSubTasksArray) {
            SubTask checkedSubTask = subTasks.get(subTaskId);
            if (checkedSubTask.getStatus() == Status.IN_PROGRESS) {
                inProgressCounter++;
            } else if (checkedSubTask.getStatus() == Status.DONE) {
                doneCounter++;
            } else if (checkedSubTask.getStatus() == Status.NEW) {
                newCounter++;
            }
        }
        if (inProgressCounter > 0 || newCounter > 0 && newCounter < epic.getSubTasksIds().size()) {
            epic.setStatus(Status.IN_PROGRESS);
        } else if (doneCounter == epic.getSubTasksIds().size()) {
            epic.setStatus(Status.DONE);
        } else if (newCounter == epic.getSubTasksIds().size()) {
            epic.setStatus(Status.NEW);
        }
    }

    // БЛОК УДАЛЕНИЯ ПО ИДЕНТИФИКАТОРУ
    // метод выбора задачи по типу
    public void deleteById(int deleteId) {
        if (tasks.get(deleteId) != null) {
            deleteTaskById(deleteId);
        } else if (epics.get(deleteId) != null) {
            deleteEpicById(deleteId);
        } else if (subTasks.get(deleteId) != null) {
            deleteSubTaskById(deleteId);
        }
    }

    // метод удаления типа по идентификатору
    private void deleteTaskById(int deleteTaskId) {
        tasks.remove(deleteTaskId);
    }

    // метод удаления эпика по идентификатору
    private void deleteEpicById(int deleteEpicId) {
        Epic epic = epics.get(deleteEpicId);
        ArrayList<Integer> deletingEpicSubTasks = epic.getSubTasksIds();
        for (int subTaskId : deletingEpicSubTasks) { // удаление всех подзадач эпика
            subTasks.remove(subTaskId);
        }
        epics.remove(deleteEpicId);
    }

    // метод удаления подзадачи по идентификатору
    private void deleteSubTaskById(int deleteSubTaskId) {
        SubTask subTask = subTasks.get(deleteSubTaskId);
        Epic epic = epics.get(subTask.getEpicId());
        epic.getSubTasksIds().remove((Integer) deleteSubTaskId); // удаление подзадачи из списка свяанного эпика
        subTasks.remove(deleteSubTaskId);
        updateEpicStatus(subTask.getEpicId()); // пересчёт статуса эпика
    }

    // БЛОК УДАЛЕНИЯ ВСЕХ ЗАДАЧ
    public void deleteAllTasks() {
        tasks.clear();
        epics.clear();
        subTasks.clear();
    }

    // БЛОК ПОЛУЧЕНИЯ ВСЕХ ПОДЗАДАЧ ЭПИКА
    public ArrayList<SubTask> showSubTaskList(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<SubTask> epicSubTasksList = new ArrayList<>();
        for (Integer subTaskId : epic.getSubTasksIds()) {
            SubTask subTask = subTasks.get(subTaskId);
            epicSubTasksList.add(subTask);
        }
        return epicSubTasksList;
    }
}