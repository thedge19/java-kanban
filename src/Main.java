import enums.Status;
import manager.InMemoryHistoryManager;
import manager.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TaskManager manager = new TaskManager();
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

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
                            Task viewedTask = manager.returnTask(taskId);
                            historyManager.addTask(viewedTask);
                            break;
                        case "epic":
                            Task viewedEpic = manager.returnEpic(taskId);
                            historyManager.addTask(viewedEpic);
                            break;
                        case "subTask":
                            Task viewedSubTask = manager.returnSubTask(taskId);
                            historyManager.addTask(viewedSubTask);
                            break;
                        default:
                            return;
                    }
                    break;
                case "3":
                    printTasksTypesMenu();

                    int taskTypeId = Integer.parseInt(scanner.nextLine().trim()); // Вводится номер типа задачи

                    switch (taskTypeId) {
                        case 1:
                            Task task = new Task();
                            System.out.println("Введите наименование задачи");
                            task.setName(scanner.nextLine().trim());
                            System.out.println("Введите описание задачи");
                            task.setDescription(scanner.nextLine().trim());
                            manager.createTask(task);
                            break;
                        case 2:
                            Epic epic = new Epic();
                            System.out.println("Введите наименование эпика");
                            epic.setName(scanner.nextLine().trim());
                            System.out.println("Введите описание эпика");
                            epic.setDescription(scanner.nextLine().trim());
                            manager.createEpic(epic);
                            break;
                        case 3:
                            System.out.println("Введите номер эпика подзадачи:");
                            int epicId = Integer.parseInt(scanner.nextLine().trim());
                            if (manager.isEpicExist(epicId)) {
                                SubTask subTask = new SubTask();
                                System.out.println("Введите наименование подзадачи");
                                subTask.setName(scanner.nextLine().trim());
                                System.out.println("Введите описание подзадачи");
                                subTask.setDescription(scanner.nextLine().trim());
                                subTask.setEpicId(epicId);
                                manager.createSubTask(subTask);
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
                            Task newTask = new Task();
                            Task oldTask = manager.returnTask(updateTaskId);
                            newTask.setId(oldTask.getId());
                            System.out.println("Обновить наименование задачи? Да - введите 1, нет - любой другое число");
                            int taskUpdateNameAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (taskUpdateNameAnswer == 1) {
                                System.out.println("Введите новое наименование задачи:");
                                newTask.setName(scanner.nextLine().trim());
                            } else {
                                newTask.setName(oldTask.getName());
                            }
                            System.out.println("Обновить описание задачи? Да - введите 1, нет - любой другое число");
                            int taskUpdateDescriptionAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (taskUpdateDescriptionAnswer == 1) {
                                System.out.println("Введите новое описание задачи:");
                                newTask.setDescription(scanner.nextLine().trim());
                            } else {
                                newTask.setDescription(oldTask.getDescription());
                            }
                            System.out.println("Обновить статус задачи? Да - введите 1, нет - любой другое число");
                            int taskUpdateStatusAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (taskUpdateStatusAnswer == 1) {
                                System.out.println("Введите новый статус - IN_PROGRESS, DONE");
                                newTask.setStatus(Status.valueOf(scanner.nextLine()));
                            } else {
                                newTask.setStatus(oldTask.getStatus());
                            }
                            manager.updateTask(newTask);
                            break;
                        case "epic":
                            Epic newEpic = new Epic();
                            Epic oldEpic = manager.returnEpic(updateTaskId);
                            newEpic.setId(oldEpic.getId());
                            System.out.println("Обновить наименование эпика? Да - введите 1, нет - любой другое число");
                            int epicUpdateNameAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (epicUpdateNameAnswer == 1) {
                                System.out.println("Введите новое наименование эпика:");
                                newEpic.setName(scanner.nextLine().trim());
                            } else {
                                newEpic.setName(oldEpic.getName());
                            }
                            System.out.println("Обновить описание эпика? Да - введите 1, нет - любой другое число");
                            int updateEpicDescriptionAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (updateEpicDescriptionAnswer == 1) {
                                System.out.println("Введите новое описание эпика:");
                                newEpic.setDescription(scanner.nextLine().trim());
                            } else {
                                newEpic.setDescription(oldEpic.getDescription());
                            }
                            manager.updateEpic(newEpic);
                            break;
                        case "subTask":
                            SubTask newSubTask = new SubTask();
                            SubTask oldSubTask = manager.returnSubTask(updateTaskId);
                            newSubTask.setId(oldSubTask.getId());
                            System.out.println("Обновить наименование подзадачи? Да - введите 1, нет - любой другое число");
                            int subtaskUpdateNameAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (subtaskUpdateNameAnswer == 1) {
                                System.out.println("Введите новое наименование подзадачи:");
                                newSubTask.setName(scanner.nextLine().trim());
                            } else {
                                newSubTask.setName(oldSubTask.getName());
                            }
                            System.out.println("Обновить описание подзадачи? Да - введите 1, нет - любой другое число");
                            int subtaskUpdateDescriptionAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (subtaskUpdateDescriptionAnswer == 1) {
                                System.out.println("Введите новое наименование подзадачи:");
                                newSubTask.setDescription(scanner.nextLine().trim());
                            } else {
                                newSubTask.setDescription(oldSubTask.getDescription());
                            }
                            System.out.println("Обновить статус подзадачи? Да - введите 1, нет - любой другое число");
                            int subtaskUpdateStatusAnswer = Integer.parseInt(scanner.nextLine().trim());
                            if (subtaskUpdateStatusAnswer == 1) {
                                System.out.println("Введите новый статус - IN_PROGRESS, DONE");
                                newSubTask.setStatus(Status.valueOf(scanner.nextLine()));
                            } else {
                                newSubTask.setStatus(oldSubTask.getStatus());
                            }
                            newSubTask.setEpicId(oldSubTask.getEpicId());
                            manager.updateSubTask(newSubTask);
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
                    historyManager.getHistory();
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
}
