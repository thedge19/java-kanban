# java-kanban
Repository for homework project.

## Исправление замечаний ревью №1

Пункты по порядку исправления

1. <span style='color: green;'>_Убран класс checkers_</span>
2. Manager и задачи должны находиться в разных папках - <span style='color: green;'>_задачи разнесены в папку tasks, менеджер - в папку manager, type и status - в папку enums._</span>
3. Не надо делать мапы статическими, они должны быть привязаны к одному классу. При этом они должны иметь модификатор доступа. Также, не надо складывать все таски в одну мапу, это значительно повышает сложность методов, должно быть три мапы каждая из которых будет содержать свой тип задач. <span style='color: green;'>_Разнесено в три хэш-таблицы, убран метод static, добавлены модификаторы._</span>
4. Модификатор доступа int id = 0. <span style='color: green;'>_Присвоен модификатор доступа private_</span>
5. System.out.println("Задача *" + name + "* создана"); Менеджер не должен ничего выводить в консоль  <span style='color: green;'>_выполнено_</span>
6. В методы по созданию задач должны приходить уже готовые задачи, за исключением id <span style='color: green;'>_задачи создаются сеттерами прямо в мэйне, методы по созданию в менеджере разгружены_</span>
7. Переменные классов задач должны иметь максимально возможный закрытый уровень доступа, а все взаимодействие должно производиться через методы <span style='color: green;'>_выполнено_</span>
8. public void showSubTaskList(int epicId) Метод должен возвращать список<span style='color: green;'>_метод возвращает ArrayList<SubTask>_</span>
9. Перед создании подзадачи необходимо убедиться что ее эпик существует<span style='color: green;'>_модифицирован метод isEpicExist_</span>
10. Изменение статуса эпика необходимо вынести в отдельный метод, так как будет вызываться каждый раз при изменении подзадач<span style='color: green;'>_добавлен метод updateEpicStatus_</span>
11. У эпика можно менять только название и описание, за обновление подзадач будут отвечать другие методы <span style='color: green;'>_учтено в классе Main при обновлении эпика_</span>
12. public void deleteTaskById(int id) - Аналогично остальным методам, их должно быть три <span style='color: green;'>_создано три метода удаления по id для каждого типа_</span>
13. У эпика должен пересчитываться статус после удаления подзадачи <span style='color: green;'>_в метод удаления подзадачи добавлено обращение к методу изменения статуса эпика_</span>
14. Необходимо также удалять подзадачу из основной мамы <span style='color: green;'>_id подзадачи удаляется из списка id связанного с подзадачами эпика_</span>
15. Все методы, которые вызываются внутри класса, должны быть приватными <span style='color: green;'>_все методы, к которым нет обращения извне сделаны приватными_</span>
16. Также, у всех типов задач должны быть отдельные методы по созданию, модификации и удалению. Не стоит все типы задач реализовывать в одном методе <span style='color: green;'>_методы для создания, модификации и удаления реализованы для каждого типа в отдельности_</span>
17. После любого изменения подзадач в эпике, необходимо пересчитывать его статус <span style='color: green;'>_добавлен метод updateEpicStatus_</span>
18. В методы обновления также должны приходить только новые задачи, и больше ничего <span style='color: green;'>_методы реализованы таким образом, что помимо задач, туда приходит также id задачи в качестве параметра, я не понимаю, как это реализовать без передачи id_</span>
19. Методы по получению задач должны просто возвращать списки задач <span style='color: green;'>_выполнено_</span>
20. Менеджер ничего не должен выводить в консоль <span style='color: green;'>_выполнено_</span>
21. Методы по получению должны возвращать задачи, а не выводить их в консоль <span style='color: green;'>_выполнено_</span>
22. Менеджер ничего не должен выводить в консоль <span style='color: green;'>_выполнено_</span>

## Исправление замечаний ревью №2
1. public Task showTask(int id) - Метод не совсем соответствует названию, он возвращает задачу а не показывает ее. <span style='color: green;'>_заменено на returnTask и далее_</span>
2. public void constructTask(Task task) - Тут название метода в целом логично, но construct  обычно не используют в таких целях чтобы не было путаницы с конструктором, можно назвать put или create <span style='color: green;'>_заменено на createTask и далее_</span>
3. По удалению всех задач также должно быть три отдельных метода, каждый из которых будет очищать свое хранилище. При этом, например, при удалении всех подзадач необходимо не забыть очистить все списки с подзадачами эпиков и проставить им статус NEW =) <span style='color: green;'>_Удаление задач разнесено на три метода, соответствующие правки внесены в main(выбор типа задач для удаления, при удалении эпиков удаляются и подзадачи, т.к. они существуют только в привязке к эпику, при удалении всех подзадач списки индентификаторов подазадач эпиков очищаются, эпикам присваивается статус NEW - для этого в метод обновления статуса эпика добавлена проверка на пустоту списка подзадач_</span>
4. Не самый лучший вариант складывать все таски в цикле,  можно воспользоваться конструктором  ArrayList  <span style='color: green;'>_реализовано через new ArrayList<>(hashMap.values())_</span>
5. Сначала надо проверить, существует ли такой эпик <span style='color: green;'>_Проверка добавлена_</span>
6. Проставлять id в Main. По-сути, это ситуация, когда пользователь знает с какой id задачу ему необходимо обновить. В метод приходит задача с id, и его можно получить через геттер. <span style='color: green;'>_Добавлено поле id в класс Task_</span>
7. При обновлении задач необходимо сначала проверять, существуют ли такие задачи, иначе метод не отличается от создания. Также в метод должна приходить только задача, id необходимо брать из нее  <span style='color: green;'>_Проверки на существование добавлены, в методы приходят только задачи_</span>
