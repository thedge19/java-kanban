package manager;

public class Managers {

    private Managers() {

    }

    public static FileBackedTaskManager getDefault() {
        return new FileBackedTaskManager(getDefaultHistory());
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
