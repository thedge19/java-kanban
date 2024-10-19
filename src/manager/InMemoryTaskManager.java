package manager;

import enums.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private final HistoryManager historyManager;

    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, SubTask> subTasks;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
    }

    private int counter = 0;

    // БЛОК ПОЛУЧЕНИЯ СПИСКОВ ЗАДАЧ
    // Метод проверки пустоты хэшмапов
    @Override
    public boolean tasksIsEmpty() {
        return tasks.isEmpty() && epics.isEmpty() && subTasks.isEmpty();
    }

    // метод получения списка задач
    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    // метод получения списка эпиков
    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    // метод получения списка подзадач
    @Override
    public List<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    // БЛОК ПОЛУЧЕНИЯ ЗАДАЧИ ПО ИДЕНТИФИКАТОРУ
    // метод получения задачи по идентификатору
    @Override
    public Task getTask(int taskId) {
        Task gettingTask = tasks.get(taskId);
        historyManager.addTask(gettingTask);
        return gettingTask;
    }

    // метод получения эпика по идентификатору
    @Override
    public Epic getEpic(int epicId) {
        Epic gettingEpic = epics.get(epicId);
        historyManager.addTask(gettingEpic);
        return gettingEpic;
    }

    // метод получения подзадачи по идентификатору
    @Override
    public SubTask getSubTask(int subTaskId) {
        SubTask gettingSubTask = subTasks.get(subTaskId);
        historyManager.addTask(gettingSubTask);
        return gettingSubTask;
    }

    // БЛОК ДОБАВЛЕНИЯ ЗАДАЧ
    // метод добавления задачи
    @Override
    public void addTask(Task task) {
        counter += 1;
        task.setId(counter);
        tasks.put(counter, task);
    }

    @Override
    // метод проверки наличия эпика перед созданием подзадачи
    public boolean isEpicExist(int epicId) {
        return epics.get(epicId) != null;
    }

    // метод добавления эпика
    @Override
    public void addEpic(Epic epic) {
        counter += 1;
        epic.setId(counter);
        epics.put(counter, epic);
    }

    // метод добавления подзадачи
    @Override
    public void addSubTask(SubTask subTask) {
        counter += 1;
        subTask.setId(counter);
        int epicId = subTask.getEpicId();
        if (isEpicExist(epicId)) {
            subTasks.put(counter, subTask);
        }
    }

    // БЛОК ОБНОВЛЕНИЯ ЗАДАЧ
    // Возвращение в main типа задачи
    @Override
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

    // метод проверки наличия задачи
    public boolean isTaskExist(int taskId) {
        return tasks.get(taskId) != null;
    }

    // метод изменения задачи
    @Override
    public void updateTask(Task task) {
        if (isTaskExist(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    // Метод изменения эпика
    @Override
    public void updateEpic(Epic epic) {
        if (isEpicExist(epic.getId())) {
            updateEpicStatus(epic.getId());
            epics.put(epic.getId(), epic);
        }
    }

    // метод проверки наличия подзадачи
    public boolean isSubTaskExist(int subTaskId) {
        return subTasks.get(subTaskId) != null;
    }

    // метод изменения подзадачи
    @Override
    public void updateSubTask(SubTask subTask) {
        if (isSubTaskExist(subTask.getId())) {
            int updateEpicId = subTask.getEpicId();
            subTasks.put(subTask.getId(), subTask);
            updateEpicStatus(updateEpicId);
        }
    }

    // метод обновления статуса эпика после обновления статуса подзадачи
    private void updateEpicStatus(int updateEpicId) {
        Epic epic = epics.get(updateEpicId);
        ArrayList<Integer> epicSubTasksArray = new ArrayList<>();
        for (SubTask sT : getSubTasks()) {
            if (sT.getEpicId() == epic.getId()) {
                epicSubTasksArray.add(sT.getId());
            }
        }
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
        if (epicSubTasksArray.isEmpty() || newCounter == epicSubTasksArray.size()) {
            epic.setStatus(Status.NEW);
        } else if (inProgressCounter > 0 || newCounter > 0 && newCounter < epicSubTasksArray.size()) {
            epic.setStatus(Status.IN_PROGRESS);
        } else if (doneCounter == epicSubTasksArray.size()) {
            epic.setStatus(Status.DONE);
        }
    }

    // БЛОК УДАЛЕНИЯ ПО ИДЕНТИФИКАТОРУ
    // метод выбора задачи по типу
    @Override
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
        removeTaskFromHistory(deleteTaskId);
        tasks.remove(deleteTaskId);
    }

    // метод удаления эпика по идентификатору
    private void deleteEpicById(int deleteEpicId) {
        removeTaskFromHistory(deleteEpicId);
        Epic epic = epics.get(deleteEpicId);
        for (SubTask subTask : getSubTasks()) {
            // удаление всех подзадач эпика
            if (epic.getId() == subTask.getEpicId()) {
                removeTaskFromHistory(subTask.getId());
                subTasks.remove(subTask.getId());
            }
        }
        epics.remove(deleteEpicId);
    }

    // метод удаления подзадачи по идентификатору
    private void deleteSubTaskById(int deleteSubTaskId) {
        SubTask subTask = subTasks.get(deleteSubTaskId);
        removeTaskFromHistory(deleteSubTaskId);
        subTasks.remove(deleteSubTaskId);
        updateEpicStatus(subTask.getEpicId()); // пересчёт статуса эпика
    }

    // БЛОК УДАЛЕНИЯ ВСЕХ ЗАДАЧ
    @Override
    public void deleteAllTasks() {
        for (int deletedTaskId : tasks.keySet()) {
            historyManager.remove(deletedTaskId);
        }
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        for (int deletedEpicId : epics.keySet()) {
            historyManager.remove(deletedEpicId);
        }
        epics.clear();
        clearAllSubTasksFromHistory();
        subTasks.clear(); // при удалении всех эпиков, подазадачи также перестают существовать
    }

    @Override
    public void deleteAllSubTasks() {
        clearAllSubTasksFromHistory();
        subTasks.clear();
    }

    private void clearAllSubTasksFromHistory() { // удаление всех подзадач из истории
        for (int deletedSubTaskId : subTasks.keySet()) {
            historyManager.remove(deletedSubTaskId);
        }
    }

    // БЛОК ПОЛУЧЕНИЯ ВСЕХ ПОДЗАДАЧ ЭПИКА
    @Override
    public List<SubTask> returnSubTaskList(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<SubTask> epicSubTasksList = new ArrayList<>();
        for (SubTask subTask : getSubTasks()) {
            if (epic.getId() == subTask.getEpicId()) {
                epicSubTasksList.add(subTask);
            }
        }
        return epicSubTasksList;
    }

    // БЛОК ПОЛУЧЕНИЯ ИСТОРИИ ПРОСМОТРОВ
    @Override
    public List<Task> getHistory() { // удалить
        return historyManager.getHistory();
    }

    // БЛОК УДАЛЕНИЯ ЗАДАЧ ИЗ ИСТОРИИ ПРОСМОТРОВ
    private void removeTaskFromHistory(int id) {
        historyManager.remove(id);
    }
}