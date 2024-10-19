package manager;

import services.TaskConverter;

import java.io.File;
import java.io.IOException;

public class Managers {

    private Managers() {

    }

    public static FileBackedTaskManager getDefault() throws IOException {
        return new FileBackedTaskManager(new InMemoryHistoryManager(), new TaskConverter(), new File("testFile.csv"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
