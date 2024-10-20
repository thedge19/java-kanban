package manager;

import exceptions.ManagerSaveException;
import services.TaskConverter;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.*;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileBackedTaskManager extends InMemoryTaskManager {

    TaskConverter taskConverter;
    private final File file;

    public FileBackedTaskManager(File file) {
        this(Managers.getDefaultHistory(), file);
        taskConverter = new TaskConverter();
    }

    public FileBackedTaskManager(HistoryManager historyManager) {
        this(historyManager, new File("testFile.csv"));
    }

    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
    }


    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        fileBackedTaskManager.loadFromFile();
        return fileBackedTaskManager;
    }

    public void save() {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append("id,type,name,status,description,epic\n");
            for (Task task : tasks.values()) {
                writer.append(taskConverter.taskToString(task));
            }
            for (Epic epic : epics.values()) {
                writer.append(taskConverter.epicToString(epic));
            }
            for (SubTask subTask : subTasks.values()) {
                writer.append(taskConverter.subTaskToString(subTask));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка в файле: " + file.getAbsolutePath(), e);
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void deleteById(int deleteId) {
        super.deleteById(deleteId);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    private void loadFromFile() {
        int maxId = 0;

        try (final FileReader reader = new FileReader(file, UTF_8); final BufferedReader bR = new BufferedReader(reader)) {
            bR.readLine();

            while (true) {
                String line = bR.readLine();
                if (line == null) {
                    return;
                }

                String[] taskValues = line.split(",");
                final int id = Integer.parseInt(taskValues[0]);

                if (Objects.equals(taskValues[1], "TASK")) {
                    Task loadedTask = taskConverter.taskFromArray(taskValues);
                    tasks.put(id, loadedTask);
                } else if (Objects.equals(taskValues[1], "EPIC")) {
                    Epic loadedEpic = taskConverter.epicFromArray(taskValues);
                    epics.put(id, loadedEpic);
                } else if (Objects.equals(taskValues[1], "SUBTASK")) {
                    SubTask loadedSubTask = taskConverter.subTaskFromArray(taskValues);
                    Epic epic = getEpic(loadedSubTask.getEpicId());
                    epic.addSubTaskId(loadedSubTask.getId());
                    subTasks.put(id, loadedSubTask);
                }
                if (maxId < id) {
                    maxId = id;
                }

                counter = maxId;
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка в файле: " + file.getAbsolutePath(), e);
        }
    }
}
