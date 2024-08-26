package tasks;

import enums.Status;
import enums.TaskType;

public class Task {

    protected String name;
    protected String description;
    protected Status status;
    protected TaskType type;

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(TaskType type) {
        this.type = type;
    }
}
