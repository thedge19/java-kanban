package tasks;

import enums.Status;
import enums.TaskType;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private final transient TaskType type;
    private final List<Integer> subTaskIds;

    public Epic(Integer id, String name, String description, Status status) {
        super(id, name, description, status);
        this.subTaskIds = new ArrayList<>();
        this.type = TaskType.EPIC;
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        this.subTaskIds = new ArrayList<>();
        this.type = TaskType.EPIC;
    }

    public TaskType getType() {
        return type;
    }

    public List<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void addSubTaskId(int id) {
        subTaskIds.add(id);
    }

    public void deleteSubTaskId(int id) {
        subTaskIds.remove(id);
    }
}