package deque;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;


public class ArrayDeque<T> implements Deque<T> {

    private int size;
    private int nextFirstindex; //index where the next "first" value will be added
    private int nextLastindex; //index where the next "last" value will be added

    private T[] barray;



    public ArrayDeque() {

        this.barray = (T[]) new Object[8];
        this.nextFirstindex = 3;
        this.nextLastindex = 4;
    }
    private void resized(int capacity) {

        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < capacity; i++) {
            a[i] = this.get(i);
        }
        this.nextLastindex = size;
        barray = a;
        this.nextFirstindex = barray.length - 1;
    }

    private void resizeu(int capacity) {

        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < barray.length; i++) {
            a[i] = this.get(i);
        }

        this.nextLastindex = barray.length;
        barray = a;
        this.nextFirstindex = barray.length - 1;

    }
    @Override
        public void addFirst(T x) {

        if (size == barray.length) {
            resizeu(size * 2);
        }

        barray[nextFirstindex] = x;
        size++;

        if (nextFirstindex == 0) {
            nextFirstindex = barray.length - 1;
        } else {
            nextFirstindex--;
        }
    }

    @Override
        public void addLast(T x) {

        if (size == barray.length) {
            resizeu(size * 2);
        }

        barray[nextLastindex] = x;
        size++;

        if (nextLastindex == barray.length - 1) {
            nextLastindex = 0;
        } else {
            nextLastindex += 1;
        }

    }

    @Override
        public List toList() {

        ArrayList<T> answer = new ArrayList<>();
        for (T element: barray) {
            if (element != null) {
                answer.add((T) element);
            }
        }
        return answer;
    }

    @Override
        public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size > 0) {
            if (size < barray.length / 4) {
                resized(barray.length / 2);
            }
            T outie;
            if (nextFirstindex + 1 >= barray.length) {
                outie = barray[0];
                barray[0] = null;
                nextFirstindex = 0;
            } else {
                outie = barray[nextFirstindex + 1];
                barray[nextFirstindex + 1] = null;
                nextFirstindex++;
            }
            size--;
            return outie;
        }
        return null;
    }


    @Override
    public T removeLast() {
        if (size > 0) {
            if (size < barray.length / 4) {
                resized(barray.length / 2);
            }
            T outie;
            if (nextLastindex - 1 < 0) {
                outie = barray[barray.length - 1];
                barray[barray.length - 1] = null;
                nextLastindex = barray.length - 1;
            } else {
                outie = barray[nextLastindex - 1];
                barray[nextLastindex - 1] = null;
                nextLastindex--;
            }
            size--;
            return outie;
        }
        return null;
    }



    @Override
        public T get(int index) {
        if (index < 0 || index >= barray.length) {
            return null;
        }
        return barray[(nextFirstindex + 1 + index) % barray.length];
    }

    @Override
    public T getRecursive(int index) {
        return get(index);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Deque)) {
            return false;
        }
        Deque<T> otherDeque = (Deque<T>) other;
        if (otherDeque.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!(otherDeque.get(i).equals(this.get(i)))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public String toString() {
        return toList().toString();
    }


    private class ArrayDequeIterator implements Iterator<T> {

        private int index;

        public ArrayDequeIterator() {
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return get(index) != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T element = get(index);
            index++;
            return element;
        }
    }

}

