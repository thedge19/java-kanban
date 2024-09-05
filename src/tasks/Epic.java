package tasks;

import enums.Status;
import enums.TaskType;

import java.util.ArrayList;

public class Epic extends Task {

    private final TaskType type = TaskType.EPIC;
    private ArrayList<Integer> subTasksIds;

    public Epic(Integer id, String name, String description, Status status, ArrayList<Integer> subTasksIds) {
        super(id, name, description, status);
        this.subTasksIds = subTasksIds;
    }

    public Epic(String name, String description, Status status, ArrayList<Integer> subTasksIds) {
        super(name, description, status);
        this.subTasksIds = subTasksIds;
    }

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
