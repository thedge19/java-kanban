import enums.Status;
import manager.FileBackedTaskManager;
import manager.Managers;
import services.TimeConverter;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    static TimeConverter tc = new TimeConverter();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {


        System.out.println("Введите имя файла:");
        String filename = scanner.nextLine();

        File file = new File(filename + ".csv");

        FileBackedTaskManager manager = Managers.getDefault(file);

        if (file.exists()) {
            manager = FileBackedTaskManager.loadFromFile(file);
        } else {
            Files.createFile(Paths.get(filename + ".csv"));
        }

        while (true) {
            printMenu();

            String command = scanner.nextLine().trim();

            switch (command) {
                case "1":
                    if (manager.tasksIsEmpty()) {
                        System.out.println("* Список задач пуст *");
                        break;
                    } else {
                        manager.getTasks();
                        manager.getEpics();
                        manager.getSubTasks();
                    }
                    System.out.println();
                    break;

                case "2":
                    System.out.println("Введите id искомой задачи:");
                    int taskId = Integer.parseInt(scanner.nextLine());
                    String taskType = manager.checkTypeById(taskId); // проверка типа задачи
                    switch (taskType) {
                        case "task":
                            manager.getTask(taskId);
                            break;
                        case "epic":
                            manager.getEpic(taskId);
                            break;
                        case "subTask":
                            manager.getSubTask(taskId);
                            break;
                        default:
                            return;
                    }
                    break;

                case "3":
                    printTasksTypesMenu();

                    int taskTypeId = Integer.parseInt(scanner.nextLine().trim()); // Вводится номер типа задачи
                    System.out.println("Введите наименование:");
                    String name = scanner.nextLine().trim();
                    System.out.println("Введите описание задачи");
                    String description = scanner.nextLine().trim();
                    int taskDuration = 30;
                    LocalDateTime startTime = LocalDateTime.now();

                    if (taskTypeId != 2) {
                        System.out.println("Хотите ввести время начала выполнения задачи? 1 - да, любое другое число - временем начала будет текущий момент");
                        int startTimeChoice = Integer.parseInt(scanner.nextLine().trim());
                        if (startTimeChoice == 1) {
                            startTime = getNewStartTime();
                        }
                        System.out.println("Хотите ввести время на выполнение задачи? 1 - Да, любое другое число - Нет");
                        int durationChoice = Integer.parseInt(scanner.nextLine().trim());
                        if (durationChoice == 1) {
                            System.out.println("Введите продолжительность задачи");
                            taskDuration = Integer.parseInt(scanner.nextLine().trim());
                        }
                    }

                    switch (taskTypeId) {
                        case 1:
                            Task task = new Task(null, name, description, Status.NEW, startTime, taskDuration);
                            if (manager.checkTime(task)) {
                                manager.addTask(task);
                            } else {
                                System.out.println("Задача пересекается по времени с другими задачами. Попробуйте другое время.");
                            }
                            break;
                        case 2:
                            Epic epic = new Epic(null, name, description, Status.NEW, null, null, 0);
                            manager.addEpic(epic);
                            break;
                        case 3:
                            System.out.println("Введите номер эпика подзадачи:");
                            int epicId = Integer.parseInt(scanner.nextLine().trim());
                            if (manager.isEpicExist(epicId)) {
                                SubTask subTask = new SubTask(null, name, description, Status.NEW, epicId, startTime, taskDuration);
                                if (manager.checkTime(subTask)) {
                                    manager.addSubTask(subTask);
                                } else {
                                    System.out.println("Задача пересекается по времени с другими задачами. Попробуйте другое время.");
                                }
                                break;
                            }
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Некорректная команда");
                            return;
                    }
                    break;

                case "4":
                    System.out.println("Введите id задачи, которую необходимо обновить:");
                    int updateTaskId = Integer.parseInt(scanner.nextLine().trim());
                    String updatedTaskType = manager.checkTypeById(updateTaskId);

                    switch (updatedTaskType) {
                        case "task":
                            Task oldTask = manager.getTask(updateTaskId);
                            int newTaskId = oldTask.getId();
                            String newTaskName = oldTask.getName();
                            String newTaskDescription = oldTask.getDescription();
                            Status newTaskStatus = oldTask.getStatus();
                            LocalDateTime newTaskStartTime = oldTask.getStartTime();
                            int newTaskDuration = oldTask.getDuration();
                            System.out.println("Обновить наименование задачи? Да - введите 1, нет - любой другое число");
                            int taskUpdateNameAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (taskUpdateNameAnswer == 1) {
                                System.out.println("Введите новое наименование задачи:");
                                newTaskName = scanner.nextLine().trim();
                            }
                            System.out.println("Обновить описание задачи? Да - введите 1, нет - любой другое число");
                            int taskUpdateDescriptionAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (taskUpdateDescriptionAnswer == 1) {
                                System.out.println("Введите новое описание задачи:");
                                newTaskDescription = scanner.nextLine().trim();
                            }
                            System.out.println("Обновить статус задачи? Да - введите 1, нет - любой другое число");
                            int taskUpdateStatusAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (taskUpdateStatusAnswer == 1) {
                                System.out.println("Введите новый статус - IN_PROGRESS, DONE");
                                newTaskStatus = Status.valueOf(scanner.nextLine());
                            }
                            System.out.println("Обновить время начала выполнения задачи? Да - введите 1, нет - любой другое число");
                            int taskUpdateStartTimeAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (taskUpdateStartTimeAnswer == 1) {
                                newTaskStartTime = getNewStartTime();
                            }
                            System.out.println("Хотите обновить время на выполнение задачи? 1 - Да, любое другое число - Нет");
                            int taskUpdateDurationChoice = Integer.parseInt(scanner.nextLine().trim());
                            if (taskUpdateDurationChoice == 1) {
                                System.out.println("Введите продолжительность задачи");
                                newTaskDuration = Integer.parseInt(scanner.nextLine().trim());
                            }

                            Task newTask = new Task(newTaskId, newTaskName, newTaskDescription, newTaskStatus, newTaskStartTime, newTaskDuration);
                            if (manager.checkTime(newTask)) {
                                manager.updateTask(newTask);
                            } else {
                                System.out.println("Задача пересекается по времени с другими задачами. Попробуйте другое время.");
                            }
                            break;
                        case "epic":
                            Epic oldEpic = manager.getEpic(updateTaskId);
                            int newEpicId = oldEpic.getId();
                            String newEpicName = oldEpic.getName();
                            String newEpicDescription = oldEpic.getDescription();
                            Status newEpicStatus = oldEpic.getStatus();
                            System.out.println("Обновить наименование эпика? Да - введите 1, нет - любой другое число");
                            int epicUpdateNameAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (epicUpdateNameAnswer == 1) {
                                System.out.println("Введите новое наименование эпика:");
                                newEpicName = scanner.nextLine().trim();
                            }
                            System.out.println("Обновить описание эпика? Да - введите 1, нет - любой другое число");
                            int updateEpicDescriptionAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (updateEpicDescriptionAnswer == 1) {
                                System.out.println("Введите новое описание эпика:");
                                newEpicDescription = scanner.nextLine().trim();
                            }
                            Epic newEpic = new Epic(newEpicId, newEpicName, newEpicDescription, newEpicStatus, null, null, 0);
                            manager.updateEpic(newEpic);
                            break;
                        case "subTask":
                            SubTask oldSubTask = manager.getSubTask(updateTaskId);
                            int newSubTaskId = oldSubTask.getId();
                            String newSubTaskName = oldSubTask.getName();
                            String newSubTaskDescription = oldSubTask.getDescription();
                            Status newSubTaskStatus = oldSubTask.getStatus();
                            LocalDateTime newSubTaskStartTime = oldSubTask.getStartTime();
                            int newSubTaskDuration = oldSubTask.getDuration();
                            int newSubtasksEpicId = oldSubTask.getEpicId();
                            System.out.println("Обновить наименование подзадачи? Да - введите 1, нет - любой другое число");
                            int subtaskUpdateNameAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (subtaskUpdateNameAnswer == 1) {
                                System.out.println("Введите новое наименование подзадачи:");
                                newSubTaskName = scanner.nextLine().trim();
                            }
                            System.out.println("Обновить описание подзадачи? Да - введите 1, нет - любой другое число");
                            int subtaskUpdateDescriptionAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (subtaskUpdateDescriptionAnswer == 1) {
                                System.out.println("Введите новое наименование подзадачи:");
                                newSubTaskDescription = scanner.nextLine().trim();
                            }
                            System.out.println("Обновить статус подзадачи? Да - введите 1, нет - любой другое число");
                            int subtaskUpdateStatusAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (subtaskUpdateStatusAnswer == 1) {
                                System.out.println("Введите новый статус - IN_PROGRESS, DONE");
                                newSubTaskStatus = Status.valueOf(scanner.nextLine());
                            }
                            System.out.println("Обновить время начала выполнения задачи? Да - введите 1, нет - любой другое число");
                            int subTaskUpdateStartTimeAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (subTaskUpdateStartTimeAnswer == 1) {
                                newSubTaskStartTime = getNewStartTime();
                            }
                            System.out.println("Хотите обновить время на выполнение задачи? 1 - Да, любое другое число - Нет");
                            int subTaskUpdateDurationChoice = Integer.parseInt(scanner.nextLine().trim());
                            if (subTaskUpdateDurationChoice == 1) {
                                System.out.println("Введите продолжительность задачи");
                                newSubTaskDuration = Integer.parseInt(scanner.nextLine().trim());
                            }
                            SubTask newSubTask = new SubTask(newSubTaskId, newSubTaskName, newSubTaskDescription, newSubTaskStatus, newSubtasksEpicId, newSubTaskStartTime, newSubTaskDuration);
                            if (manager.checkTime(newSubTask)) {
                                manager.updateSubTask(newSubTask);
                            } else {
                                System.out.println("Задача пересекается по времени с другими задачами. Попробуйте другое время.");
                            }
                            break;
                        default:
                            return;
                    }
                    break;

                case "5":
                    System.out.println("Введите id задачи, которую Вы хотите удалить:");
                    manager.deleteById(Integer.parseInt(scanner.nextLine().trim()));
                    break;

                case "6":
                    System.out.println("Введите тип задачи, который Вы хотите удалить. 1 - task, 2 - epic, 3 - subtask");
                    int taskTypeNumber = Integer.parseInt(scanner.nextLine().trim());
                    switch (taskTypeNumber) {
                        case 1:
                            manager.deleteAllTasks();
                            break;
                        case 2:
                            manager.deleteAllEpics();
                            break;
                        case 3:
                            manager.deleteAllSubTasks();
                            break;
                    }
                    break;

                case "7":
                    System.out.println("Введите id эпика, список подзадач которого Вы хотите получить:");
                    int epicId = Integer.parseInt(scanner.nextLine().trim());
                    manager.returnSubTaskList(epicId);
                    break;

                case "8":
                    manager.getHistory();
                    break;

                case "0":
                    return;

                default:
                    System.out.println("Некорректная команда");
                    break;
            }
        }
    }


    public static void printMenu() {
        System.out.println("Выберите интересующий Вас пункт меню");
        System.out.println("1 - Получить список задач");
        System.out.println("2 - Получить задачу по идентификатору");
        System.out.println("3 - Добавить задачу");
        System.out.println("4 - Обновить задачу");
        System.out.println("5 - Удалить задачу по идентификатору");
        System.out.println("6 - Удалить все задачи");
        System.out.println("7 - Получить все подзадачи эпика");
        System.out.println("8 - Получить список просмотренных задач");
        System.out.println("0 - Выход");
    }

    public static void printTasksTypesMenu() {
        System.out.println("Выберите тип задачи");
        System.out.println("введите 1 - для создания обычной задачи");
        System.out.println("введите 2 - для создания большой задачи");
        System.out.println("введите 3 - для создания подзадачи");
        System.out.println("введите 0 - для отмены");
    }

    public static LocalDateTime getNewStartTime() {
        System.out.println("Введите число в формате dd.MM.yyyy");
        String newDate = scanner.nextLine().trim();
        System.out.println("Введите время в формате hh.mm");
        String newTime = scanner.nextLine().trim();
        return tc.localDateTimeFromConsole(newDate, newTime);
    }

}
