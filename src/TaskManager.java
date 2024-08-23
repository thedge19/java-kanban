import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TaskManager {
    static HashMap<Integer, Task> tasks = new HashMap<>();
//    static HashMap<Integer, Epic> epics = new HashMap<>();
//    static HashMap<Integer, SubTask> subTasks = new HashMap<>();

//    int id = 0;

//    public void constructTask(int taskTypeId, String name, String description, int epicId) {
//        id += 1;
//        if (taskTypeId == 1) {
//            Task task = new Task(name, description, Status.NEW, TaskType.TASK);
//            tasks.put(id, task);
//            System.out.println("Задача *" + name + "* создана");
//            System.out.println("-----------------------------");
//        } else if (taskTypeId == 2) {
//            Epic epic = new Epic(name, description, Status.NEW, TaskType.EPIC, new ArrayList<>());
//            epics.put(id, epic);
//            System.out.println("Большая задача *" + name + "* создана");
//            System.out.println("-----------------------------");
//        } else if (taskTypeId == 3) {
//            if (epics.get(epicId) != null) {
//                SubTask subTask = new SubTask(name, description, Status.NEW, TaskType.SUBTASK, epicId);
//                subTasks.put(id, subTask);
//                epics.get(epicId).addSubTaskId(id);
//                System.out.println("Подзадача *" + name + "* создана");
//                System.out.println("-----------------------------");
//            } else {
//                System.out.println("Большой задачи с таким id не существует");
//            }
//        }
//    }

    public void showTasks() {
        for (int id : tasks.keySet()) {
            if (tasks.get(id).type != TaskType.SUBTASK) {
                Task task = tasks.get(id);
                System.out.println(id + "." + task.name + " " + task.type + " " + task.status);
                if (tasks.get(id).type == TaskType.EPIC) {
                    Epic epic = (Epic) tasks.get(id);
                    for (int subtaskId : epic.getSubTasksIds()) {
                        SubTask subTask = (SubTask) tasks.get(subtaskId);
                        System.out.println(" " + subtaskId + "." + subTask.name + " " + subTask.type + " " + subTask.status);
                    }
                }
                System.out.println("=============================");
            }
        }
//        for (int id : epics.keySet()) {
//            Epic epic = epics.get(id);
//            System.out.println(id + "." + epic.name + " " + epic.type + " " + epic.status);
//            int i = 1;
//            for (int subtaskId : subTasks.keySet()) {
//                SubTask subTask = subTasks.get(subtaskId);
//                if (subTasks.get(subtaskId).getEpicId() == id) {
//                    System.out.println(" " + subTask.name + " id: " + subtaskId + " " + subTask.type + " " + subTask.status);
//                    i += 1;
//                }
//            }
//            System.out.println("=============================");
//        }
    }

    public void showTaskById(int id) {
        if (tasks.get(id) != null) {
            Task task = tasks.get(id);
            System.out.println(id + "." + task.name + " " + task.type + " " + task.status);
            System.out.println();
//        else if (subTasks.get(id) != null) {
//            SubTask subTask = subTasks.get(id);
//            System.out.println(id + "." + subTask.name + " " + subTask.type + " " + subTask.status);
//            System.out.println();
//        } else if (epics.get(id) != null) {
//            Epic epic = epics.get(id);
//            System.out.println(id + "." + epic.name + " " + epic.type + " " + epic.status);
//            System.out.println();
        } else {
            idError();
        }
    }

//    public String getTaskNameByTaskId(int id) {
//        return tasks.get(id).getName();
//    }


    public void cs() {
        tasks.put(1, new Task("Почесать кошку", "Почесать с применением паровой щётки", Status.NEW, TaskType.TASK));
        tasks.put(2, new Epic("Переезд", "Переехать в деревню", Status.NEW, TaskType.EPIC, new ArrayList<>(Arrays.asList(4, 5, 6))));
        tasks.put(3, new Epic("Ремонт", "Отремонтировать сарай", Status.NEW, TaskType.EPIC, new ArrayList<>(Arrays.asList(7, 8))));
        tasks.put(4, new SubTask("Упаковать вещи", "Упаковать вещи", Status.NEW, TaskType.SUBTASK, 2));
        tasks.put(5, new SubTask("Упаковать кошку", "Упаковать кошку", Status.NEW, TaskType.SUBTASK, 2));
        tasks.put(6, new SubTask("Подготовить дом", "Подготовить дом", Status.NEW, TaskType.SUBTASK, 2));
        tasks.put(7, new SubTask("Поправить забор", "Поправить забор", Status.NEW, TaskType.SUBTASK, 3));
        tasks.put(8, new SubTask("Покрасить пол", "Покрасить пол", Status.NEW, TaskType.SUBTASK, 3));
    }


    public boolean tasksIsEmpty() {
        return tasks.isEmpty();
    }

//    public boolean epicsIsEmpty() {
//        return epics.isEmpty();
//    }

    public void updateTask(int changeTaskId, String updatedName, String updatedDescription, Status updatedStatus) {
        Task oldTask = tasks.get(changeTaskId);
        String newName = updatedName.isEmpty() ? oldTask.name : updatedName;
        String newDescription = updatedDescription.isEmpty() ? oldTask.description : updatedDescription;
        Status newStatus = updatedStatus.equals(oldTask.status) ? oldTask.status : updatedStatus;

        if (tasks.get(changeTaskId) != null) {
            if (tasks.get(changeTaskId).type == TaskType.TASK) {
                Task newTask = new Task(newName, newDescription, newStatus, oldTask.type);
                tasks.put(changeTaskId, newTask);
            } else if (tasks.get(changeTaskId) != null && tasks.get(changeTaskId).type == TaskType.SUBTASK) {
                SubTask oldSubTask = (SubTask) tasks.get(changeTaskId);
                SubTask newSubtask = new SubTask(newName, newDescription, newStatus, oldSubTask.type, oldSubTask.epicId);
                tasks.put(changeTaskId, newSubtask);

                Epic changedEpic = (Epic) tasks.get(oldSubTask.epicId);

                int doneCounter = 0;
                for (Integer subtaskId : changedEpic.subTasksIds) {
                    if (tasks.get(subtaskId).getStatus() == Status.IN_PROGRESS) {
                        Status newEpicStatus = Status.IN_PROGRESS;
                        changedEpic.setStatus(newEpicStatus);
                    } else if (tasks.get(subtaskId).getStatus() == Status.DONE) {
                        doneCounter++;
                    }
                }
                if (doneCounter == changedEpic.subTasksIds.size()) {
                    changedEpic.setStatus(Status.DONE);
                }

            } else if (tasks.get(changeTaskId) != null && tasks.get(changeTaskId).type == TaskType.EPIC) {
                Epic oldEpic = (Epic) tasks.get(changeTaskId);


                Epic newEpic = new Epic(newName, newDescription, newStatus, oldEpic.type, oldEpic.subTasksIds);
                tasks.put(changeTaskId, newEpic);
//            newName = updatedName.isEmpty() ? oldSubTask.name : updatedName;
//            newDescription = updatedDescription.isEmpty() ? oldSubTask.description : updatedDescription;
//            newStatus = updatedStatus.equals(oldSubTask.status) ? oldSubTask.status : updatedStatus;
//            newName = updatedName.isEmpty() ? oldEpic.name : updatedName;
//            newDescription = updatedDescription.isEmpty() ? oldEpic.description : updatedDescription;
//            newStatus = updatedStatus.equals(oldEpic.status) ? oldEpic.status : updatedStatus;
//            System.out.println("Задача " + newName + " обновлена.");
//            System.out.println("Задача " + newName + " обновлена.");
//            System.out.println();
            }
            System.out.println("Задача " + newName + " обновлена.");

        } else {
            idError();
        }
    }

    public void deleteTaskById(int id) {
        if (tasks.get(id) != null) {
            if (tasks.get(id).type == TaskType.EPIC) {
                Epic epic = (Epic) tasks.get(id);
                ArrayList<Integer> epicSubTasksIdsList = epic.getSubTasksIds();
                for (Integer subTaskId : epicSubTasksIdsList) {
                    System.out.println("Подзадача " + tasks.remove(subTaskId).getName() + " удалена.");
                }
            } else if (tasks.get(id).type == TaskType.SUBTASK) {
                SubTask subTask = (SubTask) tasks.get(id);
                Epic epic = (Epic) tasks.get(subTask.epicId);
                ArrayList<Integer> epicSubTasksIdsList = epic.getSubTasksIds();
                epicSubTasksIdsList.remove((Integer) id);
            }
            System.out.println("Задача " + tasks.remove(id).getName() + " удалена.");
            System.out.println();
        } else {
            idError();
        }
    }

//        } else if (subTasks.get(id) != null) {
//            String removedName = subTasks.get(id).name;
//            System.out.println("Задача " + removedName + " удалена.");
//        } else if (epics.get(id) != null) {
//            Epic epic = epics.get(id);
//            ArrayList<Integer> epicSubTasksIdsList = epic.getSubTasksIds();
//            System.out.println("Задача " + epics.remove(id).getName() + " удалена.");
//            System.out.println();


    public void deleteAllTasks() {
        tasks.clear();
//        epics.clear();
//        subTasks.clear();
        System.out.println("Все задачи удалены");
        System.out.println();
    }

    public void showSubTaskList(int epicId) {
        if (checkIsEpic(epicId)) {
            Epic epic = (Epic) tasks.get(epicId);
            ArrayList<Integer> epicSubTasksIdsList = epic.getSubTasksIds();
            for (Integer subTaskId : epicSubTasksIdsList) {
                SubTask subTask = (SubTask) tasks.get(subTaskId);
                System.out.println(" " + subTaskId + "." + subTask.name + " " + subTask.type + " " + subTask.status);
            }
        } else {
            idError();
        }
    }

    public Status checkOldStatus(int id) {
        if (tasks.get(id) != null) {
            return tasks.get(id).status;
        } else return Status.NEW;
    }

    public boolean checkIsEpic(int id) {
        return tasks.get(id).type == TaskType.EPIC;
    }

    public void idError() {
        System.out.println("Задачи с таким id не существует");
        System.out.println();
    }
}
