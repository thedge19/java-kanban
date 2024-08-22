import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TaskManager {
    static HashMap<Integer, Task> tasks = new HashMap<>();
    static HashMap<Integer, Epic> epics = new HashMap<>();
    static HashMap<Integer, SubTask> subTasks = new HashMap<>();

    int id = 0;

    public void constructTask(int taskTypeId, String name, String description, int epicId) {
        id += 1;
        if (taskTypeId == 1) {
            Task task = new Task(name, description, Status.NEW, TaskType.TASK);
            tasks.put(id, task);
            System.out.println("Задача *" + name + "* создана");
            System.out.println("-----------------------------");
        } else if (taskTypeId == 2) {
            Epic epic = new Epic(name, description, Status.NEW, TaskType.EPIC, new ArrayList<>());
            epics.put(id, epic);
            System.out.println("Большая задача *" + name + "* создана");
            System.out.println("-----------------------------");
        } else if (taskTypeId == 3) {
            if (epics.get(epicId) != null) {
                SubTask subTask = new SubTask(name, description, Status.NEW, TaskType.SUBTASK, epicId);
                subTasks.put(id, subTask);
                epics.get(epicId).addSubTaskId(id);
                System.out.println("Подзадача *" + name + "* создана");
                System.out.println("-----------------------------");
            } else {
                System.out.println("Большой задачи с таким id не существует");
            }
        }
    }

    public void showTasks() {
        for (int id : tasks.keySet()) {
            Task task = tasks.get(id);
            System.out.println(id + "." + task.name + " " + task.type + " " + task.status);
            System.out.println("=============================");
        }
        for (int id : epics.keySet()) {
            Epic epic = epics.get(id);
            System.out.println(id + "." + epic.name + " " + epic.type + " " + epic.status);
            int i = 1;
            for (int subtaskId : subTasks.keySet()) {
                SubTask subTask = subTasks.get(subtaskId);
                if (subTasks.get(subtaskId).getEpicId() == id) {
                    System.out.println(" " + subTask.name + " id: " + subtaskId + " " + subTask.type + " " + subTask.status);
                    i += 1;
                }
            }
            System.out.println("=============================");
        }
    }



//    public String getTaskNameByTaskId(int id) {
//        return tasks.get(id).getName();
//    }



    public void cs() {
        tasks.put(1, new Task("Почесать кошку", "Почесать с применением паровой щётки", Status.NEW, TaskType.TASK));
        epics.put(2, new Epic("Переезд", "Переехать в деревню", Status.NEW, TaskType.EPIC, new ArrayList<>(Arrays.asList(4, 5, 6))));
        epics.put(3, new Epic("Ремонт", "Отремонтировать сарай", Status.NEW, TaskType.EPIC, new ArrayList<>(Arrays.asList(7, 8))));
        subTasks.put(4, new SubTask("Упаковать вещи", "Упаковать вещи", Status.NEW, TaskType.SUBTASK, 2));
        subTasks.put(5, new SubTask("Упаковать кошку", "Упаковать кошку", Status.NEW, TaskType.SUBTASK, 2));
        subTasks.put(6, new SubTask("Подготовить дом", "Подготовить дом", Status.NEW, TaskType.SUBTASK, 2));
        subTasks.put(7, new SubTask("Поправить забор", "Поправить забор", Status.NEW, TaskType.SUBTASK, 3));
        subTasks.put(8, new SubTask("Покрасить пол", "Покрасить пол", Status.NEW, TaskType.SUBTASK, 3));
    }


    public boolean tasksIsEmpty() {
        return tasks.isEmpty() && epics.isEmpty() && subTasks.isEmpty();
    }

    public boolean epicsIsEmpty() {
        return epics.isEmpty();
    }

    public void deleteTaskById(int id) {
        if (tasks.get(id) != null) {
            System.out.println("Задача " + tasks.remove(id).getName() + " удалена.");
            System.out.println();
        } else if (subTasks.get(id) != null) {
            String removedName = subTasks.get(id).name;
            Epic epic = epics.get(subTasks.get(id).epicId);
            ArrayList<Integer> epicSubTasksIdsList = epic.getSubTasksIds();
            epicSubTasksIdsList.remove((Integer) id);
            subTasks.remove(id);
            System.out.println("Задача " + removedName + " удалена.");
        } else if (epics.get(id) != null) {
            Epic epic = epics.get(id);
            ArrayList<Integer> epicSubTasksIdsList = epic.getSubTasksIds();
            for (Integer integer : epicSubTasksIdsList) {
                System.out.println("Подзадача " + subTasks.remove(integer).getName() + " удалена.");
            }
            System.out.println("Задача " + epics.remove(id).getName() + " удалена.");
            System.out.println();
        } else {
            System.out.println("Задачи с таким id не существует");
            System.out.println();
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
        epics.clear();
        subTasks.clear();
        System.out.println("Все задачи удалены");
        System.out.println();
    }
}
