package bstmap;


import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V>  implements Map61B<K, V> {

    int size = 0;

    @Override
    public void clear() {

    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void put(K key, V value) {

    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    // prints out BSTMap in order of increasing Key
    public void printInOrder() {

    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Not supported");
    }

    private BSTNode node;

    private class BSTNode {
        K key;
        V val;
        BSTNode left;
        BSTNode right;

        BSTNode(K k, V v, BSTNode l, BSTNode r) {
            key = k;
            val = v;
            left = l;
            right = r;
        }
        BSTNode get(K sk) {
            if (this == null) {
                return null;
            }
            if (sk.equals(this.key)) {
                return this;
            }
            else if (sk.compareTo(this.key) < 0) {
                return left.get(sk);
            }
            else if (sk.compareTo(this.key) > 0) {
                return right.get(sk);
            }
            return null;
        }
    }
}