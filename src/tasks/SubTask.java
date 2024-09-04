package tasks;

import enums.Status;
import enums.TaskType;

public class SubTask extends Task {

    private int epicId;
    private final TaskType type = TaskType.SUBTASK;

    public SubTask(int id, String name, String description, Status status, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public TaskType getType() {
        return type;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
