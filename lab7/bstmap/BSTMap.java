package bstmap;


import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V>  implements Map61B<K, V> {

    int size = 0;

    @Override
    public void clear() {
        size = 0;
        node = null;
    }

    @Override
    public V get(K sk) {
        if (node == null) {
            return null;
        }
        BSTNode lookup = node.get(sk);
        if (lookup == null) {
            return null;
        }
        return lookup.val;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    /* Inserts the key-value pair of KEY and VALUE into this dictionary,
     *  replacing the previous value associated to KEY, if any. */
    public void put(K ik, V iv) {
        this.put(ik, iv, node);
    }

    private BSTNode put(K ik, V iv, BSTNode n) {
        if (n == null) {
            return new BSTNode(ik, iv, null, null);
        }
        int cmp = ik.compareTo(n.key);
        if (cmp < 0) {
            n.left = put(ik, iv, n.left);
        }
        else if (cmp > 0) {
            n.right = put(ik, iv, n.right);
        }
        size = size + 1;
        return n;
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