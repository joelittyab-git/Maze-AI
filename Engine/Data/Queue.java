package Engine.Data;

import Engine.Data.Structure.Storage;

import java.util.ArrayList;
import java.util.ListIterator;

public class Queue<T> implements Storage<T> {

    private final ArrayList<T> list;

    public Queue(){
        list = new ArrayList<>();
    }

    public int size() {
        return list.size();
    }


    public boolean isEmpty() {
        return list.isEmpty();
    }


    public void add(T o) {
        list.add(o);
    }

    public void add(T[] o){
        for(T object : o){
            if(object != null){
                this.add(object);
            }
        }
    }

    public T pop() {
        return list.removeFirst();
    }
}
