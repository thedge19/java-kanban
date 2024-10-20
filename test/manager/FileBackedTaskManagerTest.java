package manager;

import enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest {
    FileBackedTaskManager manager;

    File file = File.createTempFile("data", null);
    Epic epic;
    Task task;
    SubTask subTask1;
    SubTask subTask2;
    SubTask subTask3;

    public FileBackedTaskManagerTest() throws IOException {
    }

    @BeforeEach
    public void beforeEach() {
        manager = new FileBackedTaskManager(file);

        task = new Task(1, "Test addNewTask", "Test addNewTask description", Status.NEW);
        epic = new Epic(2, "Test addNewEpic", "Test addNewEpic description", Status.NEW);
        subTask1 = new SubTask(3, "Test addNewSubTask", "Test addNewSubTask description1", Status.NEW, 2);
        subTask2 = new SubTask(4, "Test addNewSubTask1", "Test addNewSubTask description2", Status.NEW, 2);
        subTask3 = new SubTask(5, "Test addNewSubTask2", "Test addNewSubTask description3", Status.NEW, 2);
    }

    @Test
    public void shouldCorrectlySave() {
        manager.addTask(task);
        try (final FileReader reader = new FileReader(file, UTF_8); final BufferedReader bR = new BufferedReader(reader)) {
            bR.readLine();
            String line = bR.readLine();
            assertEquals(line, "1,TASK,Test addNewTask,NEW,Test addNewTask description");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldCorrectlyRead() {
        manager.addTask(task);
        FileBackedTaskManager.loadFromFile(file);
        try (final FileReader reader = new FileReader(file, UTF_8); final BufferedReader bR = new BufferedReader(reader)) {
            bR.readLine();
            String line = bR.readLine();
            assertEquals(line, "1,TASK,Test addNewTask,NEW,Test addNewTask description");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
