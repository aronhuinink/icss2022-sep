package nl.han.ica.datastructures;

import java.util.NoSuchElementException;

public class HANQueue<T> implements IHANQueue<T> {

    private static final class Node<T> {
        T value;
        Node<T> next;
        Node(T value) { this.value = value; }
    }

    private final Node<T> header = new Node<>(null); // sentinel
    private Node<T> tail = header;
    private int size = 0;

    @Override
    public void clear() {
        header.next = null;
        tail = header;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void enqueue(T value) {
        Node<T> n = new Node<>(value);
        tail.next = n;
        tail = n;
        size++;
    }

    @Override
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        Node<T> first = header.next;
        header.next = first.next;
        size--;
        if (size == 0) {
            tail = header; // reset tail
        }
        return first.value;
    }

    @Override
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        return header.next.value;
    }

    @Override
    public int getSize() {
        return size;
    }
}
