public class SubTask extends Task {

    protected int epicId;

    public SubTask(String name, String description, Status status, TaskType type, int epicId) {
        super(name, description, status, type);
        this.epicId = epicId;
    }
}
