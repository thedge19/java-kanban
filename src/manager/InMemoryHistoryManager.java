package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new ArrayList<>(10);

    @Override
    public void addTask(Task viewedTask) {
        if (history.size() == 10) {
            history.removeFirst();
        }
        history.add(viewedTask);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
