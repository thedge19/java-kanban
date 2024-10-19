package tasks;

import enums.Status;
import enums.TaskType;

public class SubTask extends Task {

    private final TaskType type = TaskType.SUBTASK;
    private final Integer epicId;

    public SubTask(Integer id, String name, String description, Status status, Integer epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    @Override
    public Integer getEpicId() {
        return epicId;
    }

}
