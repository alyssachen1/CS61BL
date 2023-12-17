/** A data structure to represent a Linked List of Integers.
 * Each SLList represents one node in the overall Linked List.
 */

public class SLList<T> {

    /** The object stored by this node. */
    public T item;
    /** The next node in this SLList. */
    public SLList<T> next;

    /** Constructs an SLList storing ITEM and next node NEXT. */
    public SLList(T item, SLList<T> next) {
        this.item = item;
        this.next = next;
    }

    /** Constructs an SLList storing ITEM and no next node. */
    public SLList(T item) {
        this(item, null);
    }

    /**
     * Returns [position]th item in this list. Throws IllegalArgumentException
     * if index out of bounds.
     *
     * @param position, the position of element.
     * @return The element at [position]
     */
    public T get(int position) {
        //TODO: your code here!
        SLList<T> pointer = this;
        int counter = 0;
        if (position < 0) {
            throw new IllegalArgumentException();
        }
            while (pointer != null) {
                if (counter == position) {
                    return pointer.item;
                }
                counter++;
                pointer = pointer.next;
            }
        throw new IllegalArgumentException();
    }


    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "1 2 3".
     *
     * @return The String representation of the list.
     */
    public String toString() {
        //TODO: your code here!
        SLList<T> pointer = this;
        String result = "";
        if (pointer.item == null){
            return " ";
        }
        while (pointer != null){
            if (pointer.next != null) {
                result += pointer.item + " ";
            }
            else {
                result += pointer.item;
            }
            pointer = pointer.next;
        }
        return result;
    }

    /**
     * Returns whether this and the given list or object are equal.
     *
     * NOTE: A full implementation of equals requires checking if the
     * object passed in is of the correct type, as the parameter is of
     * type Object. This also requires we convert the Object to an
     * SLList<T>, if that is legal. The operation we use to do this is called
     * casting, and it is done by specifying the desired type in
     * parenthesis. This has already been implemented for you.
     *
     * @param obj, another list (object)
     * @return Whether the two lists are equal.
     */

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof SLList)) { //or not an instance of SLList class
            return false;
        }
        SLList<T> argLst = (SLList<T>) obj;
        SLList<T> lst = this;
        while (lst != null && argLst != null) {
            if (!lst.item.equals(argLst.item)){
                return false;
            }
            lst = lst.next;
            argLst = argLst.next;
        }
        if (lst == null && argLst == null){ //they are the same length
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Adds the given value at the end of the list.
     *
     * @param value, the int to be added.
     */
    public void add(T value) {
        //TODO: your code here!
        SLList<T> pointer = this;
        while (pointer != null){
            if (pointer.next == null){
                pointer.next = new SLList<T>(value, null);
                break;
            }
            pointer = pointer.next;
        }

    }
}