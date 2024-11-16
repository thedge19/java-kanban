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

    private TaskConverter taskConverter;
    private final File file;

    public FileBackedTaskManager(File file) {
        this(Managers.getDefaultHistory(), file);
        taskConverter = new TaskConverter();
    }

    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
        taskConverter = new TaskConverter();
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        fileBackedTaskManager.loadFromFile();
        return fileBackedTaskManager;
    }

    private void save() {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.append("id,type,name,status,description,epic,startTime,duration\n");
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
    public int addTask(Task task) {
        int checkAnswer = super.addTask(task);
        save();
        return checkAnswer;
    }

    @Override
    public int addEpic(Epic epic) {
        int checkerAnswer = super.addEpic(epic);
        save();
        return checkerAnswer;
    }

    @Override
    public int addSubTask(SubTask subTask) {
        int checkerAnswer = super.addSubTask(subTask);
        save();
        return checkerAnswer;
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
    public int deleteById(int deleteId) {
        int checkAnswer = super.deleteById(deleteId);
        save();
        return checkAnswer;
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
                    if (checkTime(loadedTask)) {
                        addTask(loadedTask);
                    }
                } else if (Objects.equals(taskValues[1], "EPIC")) {
                    Epic loadedEpic = taskConverter.epicFromArray(taskValues);
                    addEpic(loadedEpic);
                } else if (Objects.equals(taskValues[1], "SUBTASK")) {
                    SubTask loadedSubTask = taskConverter.subTaskFromArray(taskValues);
                    if (checkTime(loadedSubTask)) {
                        calculateEpicStartTimeAndDuration(loadedSubTask);
                        addSubTask(loadedSubTask);
                    }
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
