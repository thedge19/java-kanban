package servers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import enums.TaskType;
import manager.TaskManager;
import services.GsonConverter;
import tasks.Epic;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class EpicsHandler implements HttpHandler {

    private final TaskManager taskManager;

    public EpicsHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String requestMethod = exchange.getRequestMethod();
        String requestQuery = exchange.getRequestURI().getQuery();

        String response;
        int statusCode = 200;

        switch (requestMethod) {
            case "GET":
                if (requestQuery == null) {
                    response = GsonConverter.getDefaultGson().toJson(taskManager.getEpics());
                } else {
                    int id = Integer.parseInt(requestQuery.split("=")[1]);
                    Epic epic = taskManager.getEpic(id);
                    if (epic != null) {
                        response = GsonConverter.getDefaultGson().toJson(epic);
                    } else {
                        statusCode = 404;
                        response = "Задача не найдена";
                    }
                }
                break;
            case "POST":
                try {
                    String bodyRequest = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

                    Epic epic = GsonConverter.getDefaultGson().fromJson(bodyRequest, Epic.class);
                    System.out.println(epic.getType());

                    int responseOfAddition = taskManager.addEpic(epic);

                    System.out.println((responseOfAddition));

                    if (responseOfAddition == -1) {
                        statusCode = 406;
                        response = "Задача пересекается с существующими";
                    } else {
                        statusCode = 201;
                        response = "Запрос обработан. ID обработанной задачи " + responseOfAddition;
                    }
                } catch (Exception e) {
                    statusCode = 400;
                    response = "Ошибка при обработке запроса " + e.getMessage();
                }
                break;
            case "DELETE":
                if (requestQuery == null) {
                    taskManager.deleteAllEpics();
                    response = "Все задачи удалены";
                } else {
                    int id = Integer.parseInt(requestQuery.split("=")[1]);
                    int responseOfDelete = taskManager.deleteById(id);
                    if (responseOfDelete != -1) {
                        response = "Задача " + responseOfDelete + " удалена";
                    } else {
                        statusCode = 404;
                        response = "Задача не найдена";
                    }
                }
                break;
            default:
                statusCode = 400;
                response = "Некорректный запрос";
        }

        exchange.sendResponseHeaders(statusCode, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
