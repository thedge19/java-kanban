package tasks;

import enums.Status;
import enums.TaskType;

import java.util.ArrayList;

public class Epic extends Task {

    private final TaskType type = TaskType.EPIC;

    public Epic(Integer id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }
}
