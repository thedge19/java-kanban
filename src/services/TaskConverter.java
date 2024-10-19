package services;

import tasks.Task;

public class TaskConverter {

    public String toString(Task task) {
        return task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription() + "\n";
    }
}
