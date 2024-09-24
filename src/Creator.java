import enums.Status;
import manager.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class Creator {

    private final TaskManager manager;

    public Creator(TaskManager manager) {
        this.manager = manager;
    }

    private final Task task1 = new Task("task1", "task1", Status.NEW);
    private final Task task2 = new Task("task2", "task2", Status.NEW);
    private final Epic epic1 = new Epic("epic1", "epic1", Status.NEW, new ArrayList<>());
    private final Epic epic2 = new Epic("epic2", "epic2", Status.NEW, new ArrayList<>());
    private final SubTask subTask1 = new SubTask("subtask1", "subtask1", Status.NEW, 3);
    private final SubTask subTask2 = new SubTask("subtask2", "subtask2", Status.NEW, 3);
    private final SubTask subTask3 = new SubTask("subtask3", "subtask3", Status.NEW, 3);
    private final SubTask subTask4 = new SubTask("subtask4", "subtask4", Status.NEW, 4);
    private final SubTask subTask5 = new SubTask("subtask5", "subtask5", Status.NEW, 4);

    public void addTasks() {
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);
        manager.addSubTask(subTask4);
        manager.addSubTask(subTask5);
    }

    public void getTasks() {
        List<Task> tasks = manager.getTasks();
        List<Epic> epics = manager.getEpics();
        List<SubTask> subTasks = manager.getSubTasks();
        for (Task task : tasks) {
            System.out.println(task.getId() + " " + task.getName() + " " + task.getStatus());
        }
        for (Epic epic : epics) {
            System.out.println(epic.getId() + " " + epic.getName() + " " + epic.getStatus() + " " + epic.getSubTasksIds());
        }
        for (SubTask subTask : subTasks) {
            System.out.println(subTask.getId() + " " + subTask.getName() + " " + subTask.getStatus() + " " + subTask.getEpicId());
        }
    }

    public void createHistory() {
        manager.getEpic(4);
        manager.getEpic(3);
        manager.getTask(1);
        manager.getTask(2);
        manager.getSubTask(8);
        manager.getSubTask(5);
        manager.getSubTask(7);
        manager.getSubTask(9);
        manager.getSubTask(6);
    }
}
