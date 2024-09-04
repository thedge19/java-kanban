package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new ArrayList<>();

    @Override
    public void addTask(Task viewedTask) {
        history.add(viewedTask);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
