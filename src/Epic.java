import java.util.ArrayList;

public class Epic extends Task {

    protected ArrayList<Integer> subTasksIds;

    public Epic(String name, String description, Status status, TaskType type, ArrayList<Integer> subTasksIds) {
        super(name, description, status, type);
        this.subTasksIds = subTasksIds;
    }

    public ArrayList<Integer> getSubTasksIds() {
        return subTasksIds;
    }

    public void addSubTaskId(int subTaskId) {
        subTasksIds.add(subTaskId);
    }
}
