package manager;

import enums.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    HistoryManager historyManager = Managers.getDefaultHistory();

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();

    private int id = 0;

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
    public void createTask(Task task) {
        id += 1;
        task.setId(id);
        tasks.put(id, task);
    }

    @Override
    // метод проверки наличия эпика перед созданием подзадачи
    public boolean isEpicExist(int epicId) {
        return epics.get(epicId) != null;
    }

    // метод добавления эпика
    @Override
    public void createEpic(Epic epic) {
        id += 1;
        epic.setId(id);
        epics.put(id, epic);
    }

    // метод добавления подзадачи
    @Override
    public void createSubTask(SubTask subTask) {
        id += 1;
        subTask.setId(id);
        int epicId = subTask.getEpicId();
        if (isEpicExist(epicId)) {// проверка существования эпика с заданным id
            addSubTaskIdToEpic(epicId, id);
            subTasks.put(id, subTask);
        }
    }

    // добавление идентификатора подзадачи в список связанного эпика
    private void addSubTaskIdToEpic(int epicId, int id) {
        if (tasks.get(id) == null && epics.get(id) == null) {
            epics.get(epicId).getSubTasksIds().add(id);
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

    // метод обновления списка id подазадач
    private ArrayList<Integer> addSubTasksIds(Epic epic) {
        ArrayList<Integer> addingSubTaskIds = new ArrayList<>();
        for (SubTask subTask : subTasks.values()) {
            if (subTask.getEpicId() == epic.getId()) {
                addingSubTaskIds.add(subTask.getId());
            }
        }
        return addingSubTaskIds;
    }

    // Метод изменения эпика
    @Override
    public void updateEpic(Epic epic) {
        if (isEpicExist(epic.getId())) {
            ArrayList<Integer> addingSubTaskIds = addSubTasksIds(epic);
            epic.setSubTasksIds(addingSubTaskIds);
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
        if (epicSubTasksArray.isEmpty() || newCounter == epic.getSubTasksIds().size()) {
            epic.setStatus(Status.NEW);
        } else if (inProgressCounter > 0 || newCounter > 0 && newCounter < epic.getSubTasksIds().size()) {
            epic.setStatus(Status.IN_PROGRESS);
        } else if (doneCounter == epic.getSubTasksIds().size()) {
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
    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subTasks.clear(); // при удалении всех эпиков, подазадачи также перестают существовать
    }

    @Override
    public void deleteAllSubTasks() {
        subTasks.clear();
        for (int epicId : epics.keySet()) {
            epics.get(epicId).setSubTasksIds(new ArrayList<>());
            updateEpicStatus(epicId);
        }
    }

    // БЛОК ПОЛУЧЕНИЯ ВСЕХ ПОДЗАДАЧ ЭПИКА
    @Override
    public List<SubTask> returnSubTaskList(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<SubTask> epicSubTasksList = new ArrayList<>();
        for (Integer subTaskId : epic.getSubTasksIds()) {
            SubTask subTask = subTasks.get(subTaskId);
            epicSubTasksList.add(subTask);
        }
        return epicSubTasksList;
    }

    // БЛОК ПОЛУЧЕНИЯ ИСТОРИИ ПРОСМОТРОВ
    public List <Task> getHistory() {
        for (Task task : historyManager.getHistory()) {
            System.out.println(task.getId() + " " + task.getName());
        }
        return historyManager.getHistory();
    }
}