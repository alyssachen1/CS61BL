import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashMap<K, V> implements Map61BL<K, V> {

    /* TODO: Instance variables here */

    private static int defaultSize = 16;

    private Item<K, V>[] array;

    private static double load_factor = 0.75;

    private static int initialSize = 26;

    private int size = 0;

    /* TODO: Constructors here */


    public HashMap() {
        this(defaultSize, load_factor);
    }

    public HashMap(int initialSize) {
        this(initialSize, load_factor);
    }

    public HashMap(int initialSize, double load_factor) {
        this.initialSize = initialSize;
        this.load_factor = load_factor;
        array = (Item<K, V>[]) new Item[initialSize];
    }

    /* TODO: Interface methods here */
    @Override
    public void clear() {
        for (int i = 0; i < initialSize; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        //check if integers list is empty
        int pointer = Math.abs(key.hashCode() % initialSize);
        return (getHelper(key, array[pointer]) != null);
    }

    @Override
    public V get(K key) {
        int pointer = Math.abs(key.hashCode() % initialSize);
        return getHelper(key, array[pointer]);
    }

    private V getHelper(K key, Item item) {
        if (item == null) {
            return null;
        } else if (item.key.equals(key)) {
            return (V) item.value;
        } else {
            return (V) getHelper(key, item.next);
        }
    }

    @Override
    public void put(K key, V value) {
        int pointer = Math.abs(key.hashCode() % initialSize);
        if (array[pointer] == null) {
            array[pointer] = new Item<>(key, value);
            size ++;
        }
        else {
            putHelper(key, value, array[pointer]);
        }
        if ((double) size / initialSize > load_factor) {
            resize();
        }
    }

    /** Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (int i = 0; i < initialSize; i += 1) {
            Item item = array[i];
            while (item != null) {
                keys.add((K) item.key);
                item = item.next;
            }
        }
        return keys;
    }

    public void resize() {
        int newInitialSize = initialSize * 2;
        Item<K, V>[] newArray = (Item<K, V>[]) new Item[newInitialSize];

        for (K key : keySet()) {
            int newPointer = Math.abs(key.hashCode() % newInitialSize);
            if (newArray[newPointer] == null) {
                newArray[newPointer] = new Item<>(key, get(key));
            } else {
                putHelper(key, get(key), newArray[newPointer]);
            }
        }

        initialSize = newInitialSize;
        array = newArray;
    }


    private void putHelper(K key, V value, Item item) {
        if (item.key == key) {
            item.value = value;
        }
        else if (item.next == null) {
            item.next = new Item<>(key, value);
            size ++;
        }
        else {
            putHelper(key, value, item.next);
        }
    }

    @Override
    public V remove(K key) {
        int pointer = Math.abs(key.hashCode() % initialSize);
        Item<K, V> current = array[pointer];
        Item<K, V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    array[pointer] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    @Override
    public boolean remove(K key, V value) {
        int pointer = Math.abs(key.hashCode() % initialSize);
        Item<K, V> current = array[pointer];
        Item<K, V> previous = null;

        while (current != null) {
            if (current.key.equals(key) && current.value.equals(value)) {
                if (previous == null) {
                    array[pointer] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    public int capacity() {
        return this.initialSize;
    }

    private static class Item<K, V> {
        private K key;

        private V value;

        private Item next;


        public Item(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        private static class Entry<K, V> {

            private K key;
            private V value;


            Entry(K key, V value) {
                this.key = key;
                this.value = value;
            }

            /* Returns true if this key matches with the OTHER's key. */
            public boolean keyEquals(Entry other) {
                return key.equals(other.key);
            }

            /* Returns true if both the KEY and the VALUE match. */
            @Override
            public boolean equals(Object other) {
                return (other instanceof Entry
                        && key.equals(((Entry) other).key)
                        && value.equals(((Entry) other).value));
            }

            @Override
            public int hashCode() {
                //need to implement new one
                return super.hashCode();
            }
        }
    }
}

