package services;

import enums.Status;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.time.LocalDateTime;

public class TaskConverter {

    TimeConverter tc = new TimeConverter();

    public String taskToString(Task task) {
        return task.getId() + "," + task.getType() + "," + task.getName()
                + "," + task.getStatus() + "," + task.getDescription()
                + "," + task.getEpicId() + "," + tc.timeToString(task.getStartTime()) + "," + task.getDuration() + "\n";
    }

    public String epicToString(Epic epic) {
        return epic.getId() + "," + epic.getType() + "," + epic.getName()
                + "," + epic.getStatus() + "," + epic.getDescription()
                + "," + epic.getEpicId() + "," + tc.timeToString(epic.getStartTime()) + "," + epic.getDuration() + "\n";
    }

    public String subTaskToString(SubTask subTask) {
        return subTask.getId() + "," + subTask.getType() + "," + subTask.getName()
                + "," + subTask.getStatus() + "," + subTask.getDescription()
                + "," + subTask.getEpicId() + "," + tc.timeToString(subTask.getStartTime()) + "," + subTask.getDuration()  + "\n";
    }

    public Task taskFromArray(String[] arr) {
        return new Task(Integer.parseInt(arr[0]), arr[2], arr[4],
                Status.valueOf(arr[3]), LocalDateTime.parse(arr[6]), Integer.parseInt(arr[7]));
    }

    public Epic epicFromArray(String[] arr) {
        return new Epic(Integer.parseInt(arr[0]), arr[2], arr[4],
                Status.valueOf(arr[3]), null, tc.epicLocalDateTime(arr[6]), Integer.parseInt(arr[7]));
    }

    public SubTask subTaskFromArray(String[] arr) {
        return new SubTask(Integer.parseInt(arr[0]), arr[2], arr[4],
                Status.valueOf(arr[3]), Integer.parseInt(arr[5]), LocalDateTime.parse(arr[6]), Integer.parseInt(arr[7]));
    }
}
