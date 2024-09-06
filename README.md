# java-kanban
Repository for homework project.

## Тесты
* проверьте, что экземпляры класса Task равны друг другу, если равен их id; <span style='color: green'> _- реализовано в тесте InMemoryTaskManagerTest метод addAndGetNewTask_</span>
* проверьте, что наследники класса Task равны друг другу, если равен их id; <span style='color: green'> _- реализовано в тесте InMemoryTaskManagerTest метод addAndGetNewTask_</span>
* проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;  <span style='color: green'> _- реализовано в тесте InMemoryTaskManagerTest метод shouldSubtaskOwnEpic_</span>
* проверьте, что объект Subtask нельзя сделать своим же эпиком;  <span style='color: green'> _- реализовано в тесте InMemoryTaskManagerTest метод shouldSubtaskOwnEpic_</span>
* убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;<span style='color: green'> _- реализовано в тестах ManagersTest_</span>
* проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id; <span style='color: green'> _- реализовано в тесте InMemoryTaskManagerTest метод addAndGetNewTask_</span>
* проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера; <span style='color: green'> _- реализовано в тесте InMemoryTaskManagerTest метод shouldConflictAddingAndGeneratingIds_</span>
* создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер <span style='color: green'> _- реализовано в тесте InMemoryTaskManagerTest метод shouldUnchangeableFields_</span>
* убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных. <span style='color: green'> _- реализовано в тесте InMemoryHistoryManagerTest метод shouldSavePreviousVersion_</span>

## Исправление замечаний первого ревью
* при объявлении методов лучше использовать интерфейсы, а не их классы реализации. Т.е. интерфейс List, а не класс ArrayList  <span style='color: green'> _- выполнено_</span>
* в InMemoryTaskManager нужно добавить экземпляр класса HistoryManager через метод getDefaultHistory() из класса Managers  <span style='color: green'> _- выполнено_</span>
* теперь и эти хранилища можно объявить через интерфейс Map, а не класс HashMap  <span style='color: green'> _- выполнено_</span>
* просмотренную задачу нужно добавить в историю <span style='color: green'> _- выполнено_</span>
* чтобы не открывать доступ к private переменной, можно history обернуть в new ArrayList<>()  <span style='color: green'> _- выполнено_</span>
* в этом интерфейсе не хватает метода getHistory() <span style='color: green'> _- выполнено_</span> 

## Исправление замечаний второго ревью
* верни хранилище  private final List<Task> history = new ArrayList<>(10);  обратно; этот метод тоже верни как был))   <span style='color: green'> _- всё возвернул_</span>
* я писал обернуть history в new ArrayList<>(), т.е. сделать вот так return new ArrayList<>(history ) - это создает новый список на базе коллекции history, иными словами копию. И метод бы вернул копию, тем самым оставив оригинальный список недоступным для манипуляций. А сейчас вместо истории просмотров, пользователь всегда получает пустой список. <span style='color: green'> _- всё обернул_</span>
* это хранилище здесь не нужно, его место в классе InMemoryHistoryManager  <span style='color: green'> _- нет хранилища такого больше_</span>
* в этом методе нужно будет прокинуть результат одноименного метода из класса InMemoryHistoryManager  через переменную historyManager  <span style='color: green'> _- прокинул_</span>