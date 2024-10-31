package tasks;

import enums.Status;
import enums.TaskType;

import java.util.ArrayList;

public class Epic extends Task {

    private final TaskType type = TaskType.EPIC;

    private final ArrayList<Integer> subTaskIds;

    public Epic(Integer id, String name, String description, Status status) {
        super(id, name, description, status);
        this.subTaskIds = new ArrayList<>();
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        this.subTaskIds = new ArrayList<>();
    }

    public TaskType getType() {
        return type;
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void addSubTaskId(int id) {
        subTaskIds.add(id);
    }

    public void deleteSubTaskId(int id) {
        subTaskIds.remove(id);
    }
}
