package tasks;

import enums.Status;
import enums.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {

    private Integer id;
    private String name;
    private String description;
    private Status status;
    private final TaskType type = TaskType.TASK;
    protected Integer epicId;
    // время
    private LocalDateTime startTime;
    private int duration;


    public Task(Integer id, String name, String description, Status status, LocalDateTime startTime, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.epicId = null;
        this.startTime = startTime;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TaskType getType() {
        return type;
    }

    public Integer getEpicId() {
        return null;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public String toString() {
        return String.valueOf(getId()) + "," + getType() + "," + getName() + "," + getStatus()
                + "," + getDescription() + "," + getEpicId();
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(Duration.ofMinutes(duration));
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
