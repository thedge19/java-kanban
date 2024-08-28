package tasks;

import enums.TaskType;

import java.util.ArrayList;

public class Epic extends Task {

    private final TaskType type = TaskType.EPIC;
    private ArrayList<Integer> subTasksIds;

    public ArrayList<Integer> getSubTasksIds() {
        return subTasksIds;
    }

    @Override
    public TaskType getType() {
        return type;
    }

    public void setSubTasksIds(ArrayList<Integer> subTasksIds) {
        this.subTasksIds = subTasksIds;
    }
}
