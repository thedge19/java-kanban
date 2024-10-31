package manager;

import org.junit.jupiter.api.Test;

class InMemoryHistoryManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    protected InMemoryTaskManager getManager() {
        return new InMemoryTaskManager(new InMemoryHistoryManager());
    }

    @Override
    @Test
    public void shouldNotDoublesInHistory() {
        super.shouldNotDoublesInHistory();
    }

    @Override
    @Test
    public void shouldDeletedTaskIsDeletedFromHistory() {
        super.shouldDeletedTaskIsDeletedFromHistory();
    }

    @Override
    @Test
    public void shouldDeletingEpicsAndSubTasksAlsoDeletingFromHistory() {
        super.shouldDeletingEpicsAndSubTasksAlsoDeletingFromHistory();
    }
}