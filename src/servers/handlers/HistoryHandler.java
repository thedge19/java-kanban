package servers.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;
import services.GsonConverter;

import java.io.IOException;
import java.io.OutputStream;

public class HistoryHandler implements HttpHandler {
    private final TaskManager taskManager;

    public HistoryHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String response;
        int statusCode = 200;

        if (requestMethod.equals("GET")) {
            response = GsonConverter.getDefaultGson().toJson(taskManager.getHistory());
        } else {
            statusCode = 400;
            response = "Некорректный запрос";
        }
        exchange.sendResponseHeaders(statusCode, 0);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
