package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T>{

    // node with double pointers
    public class Node {
        public Node prev;
        public T item;
        public Node next;
        public Node (Node m, T i, Node n) {
            prev = m;
            item = i;
            next = n;
        }
    }

    // first item in list will be sentinel.next
    private final Node sentinel;
    private int size;

    // create an empty LinkedListDeque
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {

        sentinel.next = new Node(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;

    }

    @Override
    public void addLast(T item) {
        sentinel.prev = new Node (sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    // Prints the items in the deque from first to last, separated by a space.
    // Once all the items have been printed, print out a new line.
    @Override
    public void printDeque() {
        if (isEmpty()) { // Correct empty check
            System.out.print("Empty deque");
        }
        Node nodeGuide = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(nodeGuide.item + " ");
            nodeGuide = nodeGuide.next;
        }
        System.out.print("\n");
    }

    // Removes and returns the item at the front of the deque.
    // If no such item exists, returns null.
    @Override
    public T removeFirst() {
        if (isEmpty()) { // Correct empty check
            return null;
        }
        Node nodeFirst = sentinel.next;
        T itemFirst = nodeFirst.item;

        sentinel.next = nodeFirst.next;
        nodeFirst.next.prev = sentinel;
        size -= 1;
        return itemFirst;
    }

    // Removes and returns the item at the back of the deque.
    // If no such item exists, returns null.
    @Override
    public T removeLast() {
        if (isEmpty()) { // Correct empty check
            return null;
        }
        Node nodeLast = sentinel.prev;
        T itemLast = nodeLast.item;

        sentinel.prev = nodeLast.prev;
        nodeLast.prev.next = sentinel;
        size -= 1;
        return itemLast;
    }

    // Gets the item at the given index,
    // where 0 is the front, 1 is the next item, and so forth.
    // If no such item exists, returns null
    @Override
    public T get(int index) {
        if (isEmpty()) { // Correct empty check
            return null;
        }
        // check index valid
        if (index >= size || index < 0) {
            return null;
        }
        Node nodeGuide = sentinel;
        for (int i = 0; i <= index; i++) {
            nodeGuide = nodeGuide.next;
        }
        return nodeGuide.item;
    }

    // Same as get, but uses recursion.
    public T getRecursive(int index) {
        if (index == 0) {
            return sentinel.next.item;
        }

        return getRecursive(index - 1);
    }


    /** returns an iterator (a.k.a. seer) into ME */
    @Override
    public Iterator<T> iterator() {
        return new LLDequeIterator();
    }

    // private class used only in method iterator()
    private class LLDequeIterator implements Iterator<T> {
        private Node wizPos;
        public LLDequeIterator() {
            wizPos = sentinel.next;
        }

        public boolean hasNext() {
            return wizPos != sentinel;
        }

        public T next() {
            T returnItem = wizPos.item;
            wizPos = wizPos.next;
            return returnItem;
        }
    }

    //  considered equal if:
    //  o is a Deque and
    //  o contains the same contents (as goverened by the generic T’s equals method) in the same order
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }

        if (o instanceof LinkedListDeque) {
            LinkedListDeque<T> oo = (LinkedListDeque<T>) o;
            int index = 0;
            if (oo.size() != this.size()) {
                return false;
            }
            for (T item : this) {
                T itemoo = oo.get(index);
                if (!itemoo.equals(item)) {
                    return false;
                }
                index ++;
            }
        }
        else {
            return false;
        }

        return true;
    }

}
