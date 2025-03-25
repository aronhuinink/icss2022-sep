package nl.han.ica.datastructures;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HANLinkedList implements IHANLinkedList{

    List linkedList = new LinkedList();

    @Override
    public void addFirst(Object value) {
        linkedList.add(0, value);
    }

    @Override
    public void clear() {
        linkedList.clear();
    }

    @Override
    public void insert(int index, Object value) {
        linkedList.add(index, value);
    }

    @Override
    public void delete(int pos) {
        linkedList.remove(pos);
    }

    @Override
    public Object get(int pos) {
        return linkedList.get(pos);
    }

    @Override
    public void removeFirst() {
        linkedList.remove(0);
    }

    @Override
    public Object getFirst() {
        return linkedList.get(0);
    }

    @Override
    public int getSize() {
        return linkedList.size();
    }

}

