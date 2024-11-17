package manager;

import com.sun.net.httpserver.HttpServer;
import enums.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import services.GsonConverter;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HttpTaskServerTest extends TaskManagerTest<InMemoryTaskManager> {

    File file = File.createTempFile("data", null);
    int PORT = 8080;
    HttpServer httpServer;

    // создаём экземпляр InMemoryTaskManager
    @Override
    protected InMemoryTaskManager getManager() {
        return new FileBackedTaskManager(new InMemoryHistoryManager(), file);
    }

    public HttpTaskServerTest() throws IOException {
    }
    // передаём его в качестве аргумента в конструктор HttpTaskServer
    @BeforeEach
    public void setUp() {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
            httpServer.start();

            System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterEach
    public void shutDown() {
        httpServer.stop(1);
    }
}
