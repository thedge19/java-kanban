package tasks;

import enums.Status;
import enums.TaskType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Epic extends Task {

    private final TaskType type = TaskType.EPIC;

    private final Map<Integer, LocalDateTime[]> subTaskIds;

    public Epic(Integer id, String name, String description, Status status, Integer EpicId, LocalDateTime startTime, int duration) {
        super(id, name, description, status, startTime, duration);
        this.subTaskIds = new HashMap<>();
        this.epicId = null;
    }

    public TaskType getType() {
        return type;
    }

    public Map<Integer, LocalDateTime[]> getSubTaskIds() {
        return subTaskIds;
    }

    public void addSubTaskId(int id, LocalDateTime[] times) {
        subTaskIds.put(id, times);
    }

    public void deleteSubTaskId(int id) {
        subTaskIds.remove(id);
    }
}