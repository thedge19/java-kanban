package servers;

import com.sun.net.httpserver.HttpServer;
import manager.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;


public class HttpTaskServer {
    private static final int PORT = 8080;
    private final HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        httpServer.createContext("/tasks/", new TasksHandler(taskManager));
        httpServer.createContext("/epics/", new EpicsHandler(taskManager));
        httpServer.createContext("/subTasks/", new SubTasksHandler(taskManager));
        httpServer.createContext("/prioritizedTasks/", new PrioritizedTasksHandler(taskManager));
        httpServer.createContext("/history/", new HistoryHandler(taskManager));
    }

    public void start() {
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(1);
    }
}
