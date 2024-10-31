package services;

import enums.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

public class TaskConverter {

    public String taskToString(Task task) {
        return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription() + "\n";
    }

    public String epicToString(Epic epic) {
        return epic.getId() + "," + epic.getType() + "," + epic.getName() + "," + epic.getStatus() + "," + epic.getDescription() + "\n";
    }

    public String subTaskToString(SubTask subTask) {
        return subTask.getId() + "," + subTask.getType() + "," + subTask.getName() + "," + subTask.getStatus() + "," + subTask.getDescription() + "," + subTask.getEpicId() + "\n";
    }

    public Task taskFromArray(String[] arr) {
        return new Task(Integer.parseInt(arr[0]), arr[2], arr[4],
                Status.valueOf(arr[3]));
    }

    public Epic epicFromArray(String[] arr) {
        return new Epic(Integer.parseInt(arr[0]), arr[2], arr[4],
                Status.valueOf(arr[3]));
    }

    public SubTask subTaskFromArray(String[] arr) {
        return new SubTask(Integer.parseInt(arr[0]), arr[2], arr[4],
                Status.valueOf(arr[3]), Integer.parseInt(arr[5]));
    }
}
