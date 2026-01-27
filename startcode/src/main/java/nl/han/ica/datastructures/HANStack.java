package nl.han.ica.datastructures;

import java.util.NoSuchElementException;

public class HANStack<T> implements IHANStack<T> {

    private final IHANLinkedList<T> list = new HANLinkedList<>();

    @Override
    public void push(T value) {
        list.addFirst(value);
    }

    @Override
    public T pop() {
        if (list.getSize() == 0) {
            throw new NoSuchElementException("Stack is empty");
        }
        T top = list.getFirst();
        list.removeFirst();
        return top;
    }

    @Override
    public T peek() {
        if (list.getSize() == 0) {
            throw new NoSuchElementException("Stack is empty");
        }
        return list.getFirst();
    }
}
