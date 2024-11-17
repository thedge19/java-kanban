package manager;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    File file = File.createTempFile("data", null);


    @Override
    protected InMemoryTaskManager getManager() {
        return new FileBackedTaskManager(new InMemoryHistoryManager(), file);
    }

    public FileBackedTaskManagerTest() throws IOException {
    }

    @Test
    void shouldCorrectlySaveAndRead() {
        try (final FileReader reader = new FileReader(file, UTF_8); final BufferedReader bR = new BufferedReader(reader)) {
            bR.readLine();
            String line = bR.readLine();
            assertEquals(line, "1,TASK,task,NEW,task," + line.split(",")[5] + "," + "10");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
