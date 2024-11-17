package manager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import enums.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servers.HttpTaskServer;
import services.GsonConverter;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskServerTest {

    TaskManager manager;
    HttpTaskServer taskServer;
    HttpClient client;

    public HttpTaskServerTest() throws IOException {

    }

    @BeforeEach
    public void setUp() throws IOException {
        manager = new InMemoryTaskManager(new InMemoryHistoryManager());
        client = HttpClient.newHttpClient();
        taskServer = new HttpTaskServer(manager);
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    void taskGetByIdTest() throws IOException, InterruptedException {
        createTaskByHttp();

        URI url = URI.create("http://localhost:8080/tasks/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Неверный код ответа");

        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

        assertEquals(1, jsonObject.get("id").getAsInt(), "Неверное тело ответа");
    }

    @Test
    void epicGetByIdTest() throws IOException, InterruptedException {
        createEpicByHttp();

        URI url = URI.create("http://localhost:8080/epics/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Неверный код ответа");

        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

        assertEquals(1, jsonObject.get("id").getAsInt(), "Неверное тело ответа");
    }

    @Test
    void subTaskGetByIdTest() throws IOException, InterruptedException {
        createEpicByHttp();

        createSubTaskByHttp(1);
        URI url = URI.create("http://localhost:8080/subTasks/?id=2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

        assertEquals(2, jsonObject.get("id").getAsInt(), "Неверное тело ответа");
    }

    @Test
    void tasksDeleteByIdTest() throws IOException, InterruptedException {
        createTaskByHttp();

        URI url = URI.create("http://localhost:8080/tasks/?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Неверный код ответа");
        assertEquals("Задача 1 удалена", response.body(), "Неверное тело ответа");
    }

    @Test
    void tasksPostAddTest() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/");
        String json = GsonConverter.getDefaultGson().toJson(new Task("task2", "task2", Status.NEW));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode(), "Неверный код ответа");
        assertEquals("Запрос обработан. ID обработанной задачи 1", response.body(), "Неверное тело ответа");
    }

    private void createTaskByHttp() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/");

        String json = GsonConverter.getDefaultGson().toJson(new Task("task", "task", Status.NEW));
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private void createEpicByHttp() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/epics/");

        Epic epic = new Epic("epic1", "epic1", Status.NEW);

        String json = GsonConverter.getDefaultGson().toJson(epic);

        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private void createSubTaskByHttp(int epicId) throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/subTasks/");

        SubTask task = new SubTask("subTask1", "subTask1", Status.NEW,  epicId);

        String json = GsonConverter.getDefaultGson().toJson(task);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}