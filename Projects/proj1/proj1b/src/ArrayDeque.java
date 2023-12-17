import java.util.List;
import java.util.ArrayList;

public class ArrayDeque<T> implements Deque {
    private int size;
    private int nextFirstindex;
    private int nextLastindex;

    private T[] barray;

    public ArrayDeque() {
        this.barray = new T[8];
        this.nextFirstindex = 3;
        this.nextLastindex = 4;
    }
    @Override
    public void addFirst(T x) {
        barray[nextFirstindex] = x;
        size ++;
        nextFirstindex = (nextFirstindex - 1 + barray.length) % barray.length;

        if (nextFirstindex < 0) {
            int nextie = barray.length - 1;
            while (barray[nextie] != null) {
                nextie--;
            }
            nextFirstindex = nextie;
        }
    }

    @Override
    public void addLast(T x) {


    }

    @Override
    public List toList() {
        ArrayList<T> answer = new ArrayList<>();
        for (Object element: barray){
            answer.add((T) element);
        }
        return answer;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object removeFirst() {
        if (size == 0) {
            return null;
        }
        int outie = (nextFirstindex + 1) % barray.length;
        T outed = barray[outie];
        barray[outie] = null;
        nextFirstindex = outie;
        size--;
        return outed;
    }

    @Override
    public Object removeLast() {
        if (size == 0){
            return null;
        }
        int outie = (nextLastindex - 1 + barray.length) % barray.length;
        Object outed = barray[outie];
        barray[outie] = null;
        nextLastindex = outie;
        size --;
        return outed;
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index >= barray.length || index >= size) {
            return null;
        }
        int arrayIndex = (nextFirstindex + 1 + index) % barray.length;
        return barray[arrayIndex];
    }
}
