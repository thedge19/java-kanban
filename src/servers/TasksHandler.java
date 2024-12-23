package servers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import enums.RequestMethod;
import manager.TaskManager;
import services.GsonConverter;
import tasks.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class TasksHandler implements HttpHandler {

    private final TaskManager taskManager;

    public TasksHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        RequestMethod requestMethod = RequestMethod.valueOf(exchange.getRequestMethod());
        String requestQuery = exchange.getRequestURI().getQuery();
        String response;
        int statusCode = 200;

        switch (requestMethod) {
            case RequestMethod.GET:
                if (requestQuery == null) {
                    response = GsonConverter.getDefaultGson().toJson(taskManager.getTasks());
                } else {
                    int id = Integer.parseInt(requestQuery.split("=")[1]);
                    Task task = taskManager.getTask(id);
                    if (task != null) {
                        response = GsonConverter.getDefaultGson().toJson(task);
                    } else {
                        statusCode = 404;
                        response = "Задача не найдена";
                    }
                }
                break;
            case RequestMethod.POST:
                try {
                    String bodyRequest = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

                    Task task = GsonConverter.getDefaultGson().fromJson(bodyRequest, Task.class);
                    if (task.getStartTime() == null) {
                        task.setStartTime(LocalDateTime.now());
                    }

                    if (task.getDuration() == 0) {
                        task.setDuration(task.getConstantDuration());
                    }

                    int responseOfAddition;
                    if (task.getId() == null) {
                        responseOfAddition = taskManager.addTask(task);
                    } else {
                        responseOfAddition = taskManager.updateTask(task);
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
            case RequestMethod.DELETE:
                if (requestQuery == null) {
                    taskManager.deleteAllTasks();
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
        } finally {
            exchange.close();
        }
    }
}
