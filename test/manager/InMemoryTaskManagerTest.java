package manager;

import org.junit.jupiter.api.Test;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    public InMemoryTaskManager getManager() {
        return new InMemoryTaskManager(new InMemoryHistoryManager());
    }

    @Override
    @Test
    void shouldGetTask() {
        super.shouldGetTask();
    }

    @Override
    @Test
    void shouldAddTask() {
        super.shouldAddTask();
    }

    @Override
    @Test
    void shouldAddSubTask() {
        super.shouldAddSubTask();
    }

    @Override
    @Test
    void shouldAddEpic() {
        super.shouldAddEpic();
    }

    @Override
    @Test
    void shouldAddDifferentTasks() {
        super.shouldAddDifferentTasks();
    }

    @Override
    @Test
    void shouldGetDifferentTaskById() {
        super.shouldGetDifferentTaskById();
    }

    @Override
    @Test
    public void shouldUnchangeableFields() {
        super.shouldUnchangeableFields();
    }

    @Override
    @Test
    public void epicBoundaryValues() {
        super.epicBoundaryValues();
    }

    @Override
    @Test
    public void checkingIntersectionsTimelines() {
        super.checkingIntersectionsTimelines();
    }
}
