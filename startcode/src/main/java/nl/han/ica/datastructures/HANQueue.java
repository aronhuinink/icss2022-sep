package nl.han.ica.datastructures;

public class HANQueue<T> implements IHANQueue<T> {

    HANLinkedList<T> list = new HANLinkedList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public void enqueue(T value) {
        if(isEmpty())
        {
            list.addFirst(value);
        }
        else
        {
            list.insert(getSize(), value);
        }
    }

    @Override
    public T dequeue() {
        T first = list.getFirst();
        list.removeFirst();
        return first;
    }

    @Override
    public T peek() {
        return list.getFirst();
    }

    @Override
    public int getSize() {
        return list.getSize();
    }
}