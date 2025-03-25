package nl.han.ica.datastructures;

public class HANStack<T> implements IHANStack<T> {
    private HANLinkedList stack;

    public HANStack() {
        this.stack = new HANLinkedList();
    }

    @Override
    public void push(T value) {
        stack.addFirst(value);
    }

    @Override
    public T pop() {
        if (stack.getSize() == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        T value = (T) stack.getFirst();
        stack.removeFirst();
        return value;
    }

    @Override
    public T peek() {
        if (stack.getSize() == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        return (T) stack.getFirst();
    }
}

