package tasks;

import enums.Status;
import enums.TaskType;

public class SubTask extends Task {


    private final TaskType type = TaskType.SUBTASK;

    public SubTask(Integer id, String name, String description, Status status, int epicId) {
        super(id, name, description, status, epicId);
    }

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status);
    }
}
