package tasks;

import enums.Status;
import enums.TaskType;

import java.time.LocalDateTime;

public class SubTask extends Task {

    private final TaskType type = TaskType.SUBTASK;

    private final LocalDateTime[] times;

    public SubTask(Integer id, String name, String description, Status status, int epicId, LocalDateTime startTime, int duration) {
        super(id, name, description, status, startTime, duration);
        this.epicId = epicId;
        this.times = new LocalDateTime[]{startTime, getEndTime()};
    }

    @Override
    public Integer getEpicId() {
        return epicId;
    }

    public TaskType getType() {
        return type;
    }

    public LocalDateTime[] getTimes() {
        return times;
    }
}
