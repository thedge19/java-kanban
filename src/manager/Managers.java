package manager;

import java.io.File;

public class Managers {

    private Managers() {
    }

    public static FileBackedTaskManager getDefault(File file) {
        return new FileBackedTaskManager(getDefaultHistory(), file);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
