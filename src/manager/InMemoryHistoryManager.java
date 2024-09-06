package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    @Override
    public void addTask(Task viewedTask, List<Task> history) {
        if (history.size() == 10) {
            history.removeFirst();
        }
        history.add(viewedTask);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>();
    }
}
