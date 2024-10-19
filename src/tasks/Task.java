package tasks;

import enums.Status;
import enums.TaskType;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Task {

    private Integer id;
    private String name;
    private String description;
    private Status status;
    private final TaskType type = TaskType.TASK;
    private int epicId;

    public Task(Integer id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(Integer id, String name, String description, Status status, Integer epicId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return null;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

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

    public TaskType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return String.valueOf(getId()) + "," + getType() + "," + getName() + "," + getStatus()
                + "," + getDescription() + "\n";
    }
}
