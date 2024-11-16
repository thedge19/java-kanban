package services;

import enums.Status;
import enums.TaskType;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.time.LocalDateTime;

public class TaskConverter {

    TimeConverter tc = new TimeConverter();

    public String taskToString(Task task) {
        return task.getId() + "," + TaskType.TASK + "," + task.getName()
                + "," + task.getStatus() + "," + task.getDescription()
                + "," + tc.timeToString(task.getStartTime()) + "," + task.getDuration() + "\n";
    }

    public String epicToString(Epic epic) {
        return epic.getId() + "," + TaskType.EPIC + "," + epic.getName()
                + "," + epic.getStatus() + "," + epic.getDescription() + "\n";
    }

    public String subTaskToString(SubTask subTask) {
        return subTask.getId() + "," + TaskType.SUBTASK + "," + subTask.getName()
                + "," + subTask.getStatus() + "," + subTask.getDescription()
                + "," + subTask.getEpicId() + "," + tc.timeToString(subTask.getStartTime()) + "," + subTask.getDuration() + "\n";
    }

    public Task taskFromArray(String[] arr) {
        return new Task(Integer.parseInt(arr[0]), arr[2], arr[4],
                Status.valueOf(arr[3]), LocalDateTime.parse(arr[5]), Integer.parseInt(arr[6]));
    }

    public Epic epicFromArray(String[] arr) {
        return new Epic(Integer.parseInt(arr[0]), arr[2], arr[4],
                Status.valueOf(arr[3]));
    }

    public SubTask subTaskFromArray(String[] arr) {
        return new SubTask(Integer.parseInt(arr[0]), arr[2], arr[4],
                Status.valueOf(arr[3]), Integer.parseInt(arr[5]), LocalDateTime.parse(arr[6]), Integer.parseInt(arr[7]));
    }

    private static LocalDateTime parseTime(String time) {
        if (time.equals("null")) {
            return null;
        }
        return LocalDateTime.parse(time);
    }
}
