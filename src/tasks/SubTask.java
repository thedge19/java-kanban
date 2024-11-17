package tasks;

import enums.Status;
import enums.TaskType;

import java.time.LocalDateTime;

public class SubTask extends Task {

    private final transient TaskType type;
    private final int epicId;

    public SubTask(Integer id, String name, String description, Status status, int epicId, LocalDateTime startTime, long duration) {
        super(id, name, description, status, startTime, duration);
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;
    }

    public SubTask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public TaskType getType() {
        return type;
    }
}
