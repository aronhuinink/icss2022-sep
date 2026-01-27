package nl.han.ica.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HANLinkedList<T> implements IHANLinkedList<T> {

    private static final class Node<T> {
        T value;
        Node<T> next;
        Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    // Header/sentinel node (telt NIET mee voor size)
    private final Node<T> header = new Node<>(null, null);
    private int size = 0;

    @Override
    public void addFirst(T value) {
        header.next = new Node<>(value, header.next);
        size++;
    }

    @Override
    public void clear() {
        header.next = null;
        size = 0;
    }

    @Override
    public void insert(int index, T value) {
        checkInsertIndex(index); // mag ook op size (append)
        Node<T> prev = nodeBefore(index);
        prev.next = new Node<>(value, prev.next);
        size++;
    }

    @Override
    public void delete(int pos) {
        checkElementIndex(pos);
        Node<T> prev = nodeBefore(pos);
        prev.next = prev.next.next;
        size--;
    }

    @Override
    public T get(int pos) {
        checkElementIndex(pos);
        return nodeAt(pos).value;
    }

    @Override
    public void removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty");
        }
        header.next = header.next.next;
        size--;
    }

    @Override
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty");
        }
        return header.next.value;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> current = header.next;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) throw new NoSuchElementException();
                T v = current.value;
                current = current.next;
                return v;
            }
        };
    }

    // Helpers

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
    }

    private void checkInsertIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
    }

    private Node<T> nodeBefore(int index) {
        // index==0 => header
        Node<T> prev = header;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        return prev;
    }

    private Node<T> nodeAt(int index) {
        Node<T> cur = header.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur;
    }
}
