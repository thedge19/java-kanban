package manager;

import exceptions.ManagerSaveException;
import services.TaskConverter;
import tasks.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final TaskConverter taskConverter;
    private final File file;

    public FileBackedTaskManager(HistoryManager historyManager, TaskConverter taskConverter, File file) {
        super(historyManager);
        this.taskConverter = taskConverter;
        this.file = file;
    }

    public void save() {
        try(final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : getTasks()) {
                writer.append(taskConverter.toString(task));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка в файле: " +  file.getAbsolutePath(), e);
        }
    }
}
