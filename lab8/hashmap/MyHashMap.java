package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    @Override
    public void clear() {
        size = 0;
        keys = null;
        buckets = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (keys == null) {
            return false;
        }
        return keys.contains(key);
    }

    @Override
    public V get(K key) {
        if (findNode(key) == null) {
            return null;
        }
        return findNode(key).value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        // resize if reach load factor
        if ((double) size / bktSize >= loadFactor) {
            resize();
        }

        if (findNode(key) == null) {
            int hc = key.hashCode();
            int index = Math.floorMod(hc, bktSize);
            // add new node to this bucket
            buckets[index].add(new Node(key, value));
            size += 1;
            keys.add(key);
            return;
        }
        // find the node and replace value
        findNode(key).value = value;
    }

    private void resize() {
        int newBktSize = bktSize * 2;
        Collection[] newBuckets = new Collection[newBktSize];
        for (int i = 0; i < newBktSize; i++) {
            newBuckets[i] = this.createBucket();
        }

        for (int i = 0; i < bktSize; i++) {
            if (buckets[i] != null) {
                for (Node node: buckets[i]) {
                    int hc = node.key.hashCode();
                    int index = Math.floorMod(hc, newBktSize);
                    newBuckets[index].add(node);
                }
            }
        }
        buckets = newBuckets;
        bktSize = newBktSize;

    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Not supported");
    }

    /* returns an Iterator that iterates over the stored keys */
    @Override
    public Iterator<K> iterator() {
        return new HashMapIterator();
    }

    private class HashMapIterator implements Iterator<K> {
        private Iterator<K> keysIterator;

        public void HashMapIterator() {
            keysIterator = keys.iterator(); // borrowing HashSet's iterator
        }

        @Override
        public boolean hasNext() {
            return keysIterator.hasNext();
        }

        @Override
        public K next() {
            return keysIterator.next();
        }
    }
    /* helper */
    private Node findNode (K key) {
        if (buckets == null) {
            return null;
        }
        int hc = key.hashCode();
        int index = Math.floorMod(hc, bktSize);
        if (buckets[index] != null) {
            for (Node node: buckets[index]) {
                if (node.key.equals(key)) {
                    return node;
                }
            }
        }
        return null;
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int size = 0;
    /* default settings */
    private int bktSize = 16;
    private double loadFactor = 0.75;
    /* hold all keys */
    private HashSet<K> keys = new HashSet<>();

    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[bktSize];
        for (int i = 0; i < bktSize; i++) {
            buckets[i] = this.createBucket();
        }
    }

    public MyHashMap(int initialSize) {
        bktSize = initialSize;
        buckets = new Collection[bktSize];
        for (int i = 0; i < bktSize; i++) {
            buckets[i] = this.createBucket();
        }

    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        bktSize = initialSize;
        loadFactor = maxLoad;
        buckets = new Collection[bktSize];
        for (int i = 0; i < bktSize; i++) {
            buckets[i] = this.createBucket();
        }

    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return null;
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return null;
    }
}
