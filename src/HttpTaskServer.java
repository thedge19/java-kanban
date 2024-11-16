import com.sun.net.httpserver.HttpServer;
import manager.FileBackedTaskManager;
import manager.TaskManager;
import servers.handlers.*;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class HttpTaskServer {
    private static final int PORT = 8080;
    static Scanner scanner = new Scanner(System.in);
    static File file;
    static TaskManager taskManager = new FileBackedTaskManager(file);

    public static void main(String[] args) throws IOException {
//        System.out.println("Введите имя файла:");
//        String filename = scanner.nextLine();

//        File file = new File(filename + ".csv");
        File file = new File("test.csv");

        if (file.exists()) {
            taskManager = FileBackedTaskManager.loadFromFile(file);
        } else {
//            Files.createFile(Paths.get(filename + ".csv"));
            Files.createFile(Paths.get("test.csv"));
        }
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
            httpServer.start();

            httpServer.createContext("/tasks/", new TasksHandler(taskManager));
            httpServer.createContext("/epics/", new EpicsHandler(taskManager));
            httpServer.createContext("/subTasks/", new SubTasksHandler(taskManager));
            httpServer.createContext("/prioritizedTasks/", new PrioritizedTasksHandler(taskManager));
            httpServer.createContext("/history/", new HistoryHandler(taskManager));

            System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
            System.out.println("Для остановки сервера введите exit");
            String exit = scanner.nextLine();
            if (exit.equals("exit")) {
                httpServer.stop(1);
            } else {
                System.out.println("Некорректная команда");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
