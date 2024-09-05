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