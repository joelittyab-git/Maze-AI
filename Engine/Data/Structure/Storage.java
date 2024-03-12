package Engine.Data.Structure;


public interface Storage<T> {
    boolean isEmpty();
    void add(T object);
    void add(T[] objects);
    T pop();
    int size();

}
