import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TaskManager manager = new TaskManager();
        Checkers checkers = new Checkers();

        while (true) {
            printMenu();

            String command = scanner.nextLine().trim();

            switch (command) {
                case "1":
                    if (manager.tasksIsEmpty()) {
                        System.out.println("*********************");
                        System.out.println("* Список задач пуст *");
                        System.out.println("******************+++");
                        break;
                    } else {
                        manager.showTasks();
                    }
                    System.out.println();
                    break;
                case "2":
                    System.out.println("Введите id искомоц задачи:");
                    int taskId = Integer.parseInt(scanner.nextLine());
                    manager.showTaskById(taskId);
                case "3":
                    manager.cs();
                    break;
//                    printTasksTypesMenu();
//                    int taskTypeId = Integer.parseInt(scanner.nextLine().trim());
//                    if (manager.epicsIsEmpty() && taskTypeId == 3) {
//                        System.out.println("Создайте сначала большую задачу");
//                    } else if (taskTypeId > 0 && taskTypeId < 4) {
//                        System.out.println("Введите наименование задачи");
//                        String name = scanner.nextLine().trim();
//                        System.out.println("Введите описание задачи");
//                        String description = scanner.nextLine().trim();
//                        int epicId = 0;
//                        if (taskTypeId == 3) {
//                            System.out.println("Введите id большой задачи");
//                            epicId = Integer.parseInt(scanner.nextLine().trim());
//                        }
//                        manager.constructTask(taskTypeId, name, description, epicId);
//                    } else {
//                        System.out.println("Некорректная команда");
//                    }
//                    break;
                case "4":
                    manager.showTasks();
                    System.out.println("Введите id задачи, у которой необходимо изменить статус:");
                    int changeTaskId = Integer.parseInt(scanner.nextLine().trim());
                    break;

                case "5":
                    System.out.println("Введите id задачи, которую Вы хотите удалить:");
                    int removeId = Integer.parseInt(scanner.nextLine().trim());
                    manager.deleteTaskById(removeId);
                    break;
                case "6":
                    manager.deleteAllTasks();
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
        System.out.println("4 - Изменить статус задачи");
        System.out.println("5 - Удалить задачу по идентификатору");
        System.out.println("6 - Удалить все задачи");
        System.out.println("0 - Выход");
    }

    public static void printSubMenu() {
        System.out.println("введите 1 - для изменения статуса на *В процессе*");
        System.out.println("введите 2 - для изменения статуса на *Сделано*");
        System.out.println("введите 0 - для отмены");
    }

    public static void printTasksTypesMenu() {
        System.out.println("Выберите тип задачи");
        System.out.println("введите 1 - для создания обычной задачи");
        System.out.println("введите 2 - для создания большой задачи");
        System.out.println("введите 3 - для создания подзадачи");
        System.out.println("введите 0 - для отмены");
    }
}
