package tasks;

import enums.TaskType;

public class SubTask extends Task {

    private int epicId;
    private final TaskType type = TaskType.SUBTASK;

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
