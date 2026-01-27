package nl.han.ica.datastructures;

public class HANLinkedList<T> implements IHANLinkedList<T> {
    Node<T> front;
    Node<T> back;

    private class Node<T> {
        T element;
        Node<T> next;
    }

    @Override
    public void addFirst(T value) {
        Node<T> newNode = new Node<>();
        newNode.element = value;
        newNode.next = back;
        if (front != null) {
            newNode.next = front;
            if (back == null) {
                back = front;
            }

        }
        front = newNode;
    }

    @Override
    public void clear() {
        Node<T> current = front;
        while (current != null) {
            Node<T> next = current.next;
            current.next = null;
            current = next;
        }
    }

    @Override
    public void insert(int index, T value) {
        Node<T> newNode = new Node<>();
        newNode.element = value;
        Node<T> current = front;

        try {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } catch (NullPointerException e) {
            System.out.println("Index out of bounds");
        }

        newNode.next = current.next;
        current.next = newNode;
    }

    @Override
    public void delete(int pos) {
        Node<T> current = front;

        try {
            for (int i = 0; i < pos; i++) {
                current = current.next;
            }
        } catch (NullPointerException e) {
            System.out.println("Position out of bounds");
        }

        current.next.element = null;
        current.next = current.next.next;
    }

    @Override
    public T get(int pos) {
        Node<T> current = front;

        try {
            for (int i = 0; i < pos; i++) {
                current = current.next;
            }
        } catch (NullPointerException e) {
            System.out.println("Position out of bounds");
        }

        return current.element;
    }

    @Override
    public void removeFirst() {
        if (front == null) {
            return;
        }
        front.element = null;
        front = front.next;
    }

    @Override
    public T getFirst() {
        return this.front.element;
    }

    @Override
    public int getSize() {
        if (front == null) {
            return 0;
        }
        int size = 0;
        Node<T> current = front;
        while (front.next != null) {
            current = current.next;
            size++;
        }

        return size;
    }
}