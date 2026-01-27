package nl.han.ica.datastructures;

import java.util.LinkedList;

public class HANStack<T> implements IHANStack<T> {

    LinkedList<T> list = new LinkedList<>();

    @Override
    public void push(T value) {
        list.addFirst(value);
    }

    @Override
    public T pop() {
        T first = list.getFirst();
        list.removeFirst();
        return first;
    }

    @Override
    public T peek() {
        return list.peekFirst();
    }
}