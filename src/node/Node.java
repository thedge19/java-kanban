package node;

import tasks.Task;

import java.util.Objects;

public class Node<T extends Task> {
    public Node<T> prev;
    public final Task data;
    public Node<T> next;

    public Node(Node<T> prev, Task data, Node<T> next) {
        this.prev = prev;
        this.data = data;
        this.next = next;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || (obj.getClass() != getClass())) {
            return false;
        }
        Node<T> node = (Node<T>) obj;
        return (prev.equals(node.prev)) &&
                (data.equals(node.data)) &&
                (next.equals(node.next));
    }
}
