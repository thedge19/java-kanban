package servers.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;
import services.GsonConverter;
import tasks.SubTask;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class SubTasksHandler implements HttpHandler {

    private final TaskManager taskManager;

    public SubTasksHandler(TaskManager taskManager) {
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
                    response = GsonConverter.getDefaultGson().toJson(taskManager.getSubTasks());
                } else {
                    int id = Integer.parseInt(requestQuery.split("=")[1]);
                    SubTask subTask = taskManager.getSubTask(id);
                    if (subTask != null) {
                        response = GsonConverter.getDefaultGson().toJson(subTask);
                    } else {
                        statusCode = 404;
                        response = "Задача не найдена";
                    }
                }
                break;
            case "POST":
                try {
                    String bodyRequest = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

                    int responseOfAddition;
                    SubTask subTask = GsonConverter.getDefaultGson().fromJson(bodyRequest, SubTask.class);

                    if (subTask.getId() == null) {
                        subTask.setStartTime(LocalDateTime.now());
                        subTask.setDuration(10);
                        responseOfAddition = taskManager.addSubTask(subTask);
                    } else {
                        SubTask oldSubTask = taskManager.getSubTask(subTask.getId());
                        subTask.setStartTime(oldSubTask.getStartTime());
                        subTask.setDuration(oldSubTask.getDuration());
                        responseOfAddition = taskManager.updateSubTask(subTask);
                    }

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
                    taskManager.deleteAllSubTasks();
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
