package tasks;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subTasksIds;

    public ArrayList<Integer> getSubTasksIds() {
        return subTasksIds;
    }

    public void setSubTasksIds(ArrayList<Integer> subTasksIds) {
        this.subTasksIds = subTasksIds;
    }
}
