import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T>{
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
        size ++;
    }

    @Override
    public void addLast(T x) {
        Node babynode = new Node(sentinel, x, sentinel.prev);
        sentinel.prev.next = babynode;
        sentinel.prev = babynode;
        size ++;
    }

    @Override
    public List<T> toList() {
        if (size == 0){
            return new ArrayList<>();
        }
        List<T> answer = new ArrayList<>();
        Node currnode = sentinel.next;
        while (currnode != sentinel.next){
            answer.add(currnode.value);
            currnode = currnode.next;
        }
        return answer;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0){
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
        if (size == 0){
            return null;
        }
        T answer = sentinel.next.next.prev.value;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size --;
        return answer;
    }

    @Override
    public T removeLast() {
        if (size == 0){
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
        if (index < 0){
            return null;
        }
        if (index >= size){
            return null;
        }
        Node currentNode = sentinel.next;
        while (index > 0){
            currentNode = currentNode.next;
            index --;
        }
        return currentNode.value;
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
    private class Node {
        private Node prev;
        private Node next;
        private T value;

        public Node(Node next, T value, Node prev){
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
        public Node(Node next, T value){
            this.next = next;
            this.value = value;
            this.prev = null;
        }
        public Node(){
            this.next = null;
            this.value = null;
            this.prev = null;
        }
        public Node getNext(){
            return next;
        }
    }
}
