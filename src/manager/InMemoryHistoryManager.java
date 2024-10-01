package manager;

import node.Node;
import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final NodeKeeperList history = new NodeKeeperList();

    @Override
    public void addTask(Task task) {
        if (task == null) {
            return;
        }
        history.linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (history.historyMap.containsKey(id)) {
            history.removeTask(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }

    private static class NodeKeeperList {
        private final Map<Integer, Node<Task>> historyMap = new HashMap<>();
        private Node<Task> head;
        private Node<Task> tail;

        private void linkLast(Task task) {
            if (historyMap.containsKey(task.getId())) {
                removeNode(historyMap.get(task.getId()));
            }

            Node<Task> newNode = new Node<>(tail, task, null);
            if (tail == null) {
                head = newNode;
            } else {
                tail.next = newNode;
            }
            tail = newNode;

            historyMap.put(task.getId(), newNode);
        }

        private void removeTask(int taskId) {
            removeNode(historyMap.get(taskId));
        }

        private void removeNode(Node<Task> node) {
            if (node.prev == null && node.next == null) { // Удаляем единственный элемент
                head = null;
                tail = null;
            } else if (node.prev == null) { // Удаляем head
                head = node.next;
                node.next.prev = null;
            } else if (node.next == null) { // Удаляем tail
                tail = node.prev;
                node.prev.next = null;
            } else { // Удаляем элемент из середины
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
        }

        private List<Task> getTasks() {
            List<Task> tasks = new ArrayList<>();
            if (head != null) {
                Node<Task> node = head;
                while (node != null) {
                    tasks.add(node.data);
                    node = node.next;
                }
            }
            return tasks;
        }
    }
}
