package node;

import tasks.Task;

import java.util.Objects;

public class Node {
    public Node prev;
    public final Task data;
    public Node next;

    public Node(Node prev, Task data, Node next) {
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
        Node node = (Node) obj;
        return (prev.equals(node.prev)) &&
                (data.equals(node.data)) &&
                (next.equals(node.next));
    }
}
