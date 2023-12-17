package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Deque<T> {

    private Node sentinel;
    private int size = 0;



    public LinkedListDeque() {
        this.sentinel = new Node();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }


    @Override
    public void addFirst(T x) {
        Node babynode = new Node(sentinel.next, x, sentinel);
        sentinel.next.prev = babynode;
        sentinel.next = babynode;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node babynode = new Node(sentinel, x, sentinel.prev);
        sentinel.prev.next = babynode;
        sentinel.prev = babynode;
        size++;

    }

    @Override
    public List<T> toList() {
        if (size == 0) {
            return new ArrayList<>();
        }
        List<T> answer = new ArrayList<>();
        Node currnode = sentinel.next;
        while (currnode != sentinel) {
            answer.add(currnode.value);
            currnode = currnode.next;
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
        if (size == 0) {
            return null;
        }
        T answer = sentinel.next.next.prev.value;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return answer;

    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T answer = sentinel.prev.prev.next.value;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return answer;

    }

    @Override
    public T get(int index) {
        if (index < 0) {
            return null;
        }
        if (index >= size) {
            return null;
        }

        Node currentnode = sentinel.next;
        while (index > 0) {
            currentnode = currentnode.next;
            index--;
        }

        return currentnode.value;

    }

    @Override
    public T getRecursive(int index) {
        if (index < 0) {
            return null;
        }
        if (index >= size) {
            return null;
        }
        return helper(sentinel.next, index);

    }

    private T helper(Node noder, int index) {
        if (index == 0) {
            return noder.value;
        }
        return helper(noder.next, index - 1);
    }

    @Override
    public Iterator<T> iterator() {
        return new DequeIterator();
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
        for (int i = 0; i < size; i += 1) {
            if (!(otherDeque.get(i).equals(this.get(i)))) {
                return false;
            }
        }
        return true;

    }

    @Override
    public String toString() {
        return toList().toString();
    }

    private class DequeIterator implements Iterator<T> {

        private Node currNode;

        public DequeIterator() {
            this.currNode = sentinel.next;

        }

        @Override
        public boolean hasNext() {
            return currNode != sentinel;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T result = currNode.value;
            currNode = currNode.next;
            return result;
        }
    }

    private class Node {

        private Node prev;
        private Node next;
        private T value;

        public Node(Node next, T value, Node prev) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
        public Node(Node next, T value) {
            this.next = next;
            this.value = value;
            this.prev = null;
        }

        public Node() {
            this.next = null;
            this.value = null;
            this.prev = null;
        }

        public Node getNext() {
            return next;
        }


    }
}
