import manager.FileBackedTaskManager;
import manager.Managers;
import manager.TaskManager;
import servers.HttpTaskServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
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

        HttpTaskServer taskServer = new HttpTaskServer(taskManager);
        taskServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

        System.out.println("Для остановки сервера введите exit");
        String exit = scanner.nextLine();
        if (exit.equals("exit")) {
            taskServer.stop();
        } else {
            System.out.println("Некорректная команда");
        }
    }
}
