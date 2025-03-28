package deque;

import static java.lang.Math.abs;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<T> implements Iterable<T>, Deque<T>{

    public T[] items;
    private int size;

    // circular array
    int nextFirst = 0;
    int nextLast = 1;

    // create an empty LinkedListDeque
    public ArrayDeque() {
        items = (T[]) new Object[100];
        size = 0;

    }
    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        // empty check
        if (isEmpty()) {
            items = a;
            return;
        }
        // in situation where actual items[] is like:
        // [ , , , ,2,2,4,5, , , ,] or [ , , , , ,2,2,4,5]  direct copy
        if (nextFirst < nextLast && abs(nextLast - nextFirst) != 1 || (nextLast == 0 && nextFirst != items.length - 1)) {
            System.arraycopy(items, nextFirst + 1, a, 0, size);
        }
        else {
            if (nextFirst == items.length - 1) { // [2,2,4,5, , , ] direct copy
                System.arraycopy(items, 0, a, 0, size);
            }
            else { // [5, , , , ,2,2,4] copy two parts
                int L = items.length - (nextFirst + 1);
                System.arraycopy(items, nextFirst + 1, a, 0, L);
                System.arraycopy(items, 0, a, L, size - L);
            }
        }

        items = a;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    public void addFirst(T item) {
        // resize condition
        if (size > 0 && items.length == size) {
            resize(size * 4);
        }

        items[nextFirst] = item;
        size +=1;
        nextFirst -=1;
        if (nextFirst < 0) { // if reach front border, point to end
            nextFirst = items.length - 1;
        }
    }

    public void addLast(T item) {
        // resize condition
        if (size > 0 && items.length == size) {
            resize(size * 4);
        }
        items[nextLast] = item;
        size += 1;
        nextLast += 1;
        if (nextLast > items.length - 1) {
            nextLast = 0;
        }
    }

    // Returns true if deque is empty, false otherwise.
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    // Prints the items in the deque from first to last, separated by a space.
    // Once all the items have been printed, print out a new line.
    public void printDeque() {
        if (isEmpty()) { // Correct empty check
            System.out.print("Empty deque");
        }
        int indexF = 0;
        // in situation where actual items[] is like:
        // [ , , , ,2,2,4,5, , , ,] or [ , , , , ,2,2,4,5]
        if (nextFirst < nextLast || nextLast == 0) {
            indexF = nextFirst + 1;
            for (int i = 0; i < size; i++) {
                System.out.print(items[indexF + i] + " ");
            }
        }
        else {
            if (nextFirst == items.length - 1) { // [2,2,4,5, , , ]
                indexF = 0;
                for (int i = 0; i < size; i++) {
                    System.out.print(items[indexF + i] + " ");
                }
            }
            else { // [5, , , , ,2,2,4]
                // [0,1,2,3,4,5,6,7,8]
                // [3,4, , , , ,0,1,2]
                indexF = nextFirst + 1;
                int L = items.length - (nextFirst + 1);

                for (int i = 0; i < L; i++) {
                    System.out.print(items[indexF + i] + " ");
                }
                for (int i = 0; i < size - L; i++) {
                    System.out.print(items[i] + " ");
                }
            }
        }
        System.out.print("\n");
    }

    // Removes and returns the item at the front of the deque.
    // If no such item exists, returns null.
    public T removeFirst() {
        // empty check
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        nextFirst += 1;
        if (nextFirst > items.length - 1) {
            nextFirst = 0;
        }
        T fst = items[nextFirst];
        // resize
        if (size > 0 && items.length / 4 > size) {
            resize(size * 4);
        }

        return fst;
    }

    // Removes and returns the item at the back of the deque.
    // If no such item exists, returns null.
    public T removeLast() {
        // empty check
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        nextLast -= 1;
        if (nextLast < 0) {
            nextLast = items.length - 1;
        }
        T lst = items[nextLast];

        // resize
        if (size > 0 && items.length / 4 > size) {
            resize(size * 4);
        }

        return lst;
    }

    // Gets the item at the given index,
    // where 0 is the front, 1 is the next item, and so forth.
    // If no such item exists, returns null
    public T get(int index) {
        // empty check
        if (isEmpty()) {
            return null;
        }
        // check index valid number
        if (index >= size || index < 0) {
            return null;
        }
        // [0,1,2,3,4,5,6,7,8]
        // [3,4, , , , ,0,1,2]
        // in situation where actual items[] is like:
        // [ , , , ,2,2,4,5, , , ,] or [ , , , , ,2,2,4,5]
        if (nextFirst < nextLast && abs(nextLast - nextFirst) != 1 || (nextLast == 0 && nextFirst != items.length - 1)){
            return items[nextFirst + 1 + index];
        }
        else {
            if (nextFirst == items.length - 1) { // [2,2,4,5, , , ]
                return items[index];
            }
            else { // [5, , , , ,2,2,4]
                int L;
                if (nextFirst + 1 + index >= items.length) {
                    L = nextFirst + 1 + index - items.length;
                }
                else {
                    L = nextFirst + 1 + index;
                }
                return items[L];
            }
        }
    }

    /** returns an iterator (a.k.a. seer) into ME */
    public Iterator<T> iterator() {
        return new ADequeIterator();
    }

    private class ADequeIterator implements Iterator<T> {
        private int currentPos;    // Current position in iteration
        private int elementsLeft;  // Number of elements yet to iterate

        public ADequeIterator() {
            // Start at the position AFTER nextFirst (where first element is)
            currentPos = (nextFirst + 1) % items.length;
            elementsLeft = size;
        }

        public boolean hasNext() {
            return elementsLeft > 0;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T returnItem = items[currentPos];
            if (returnItem == null) {
                throw new IllegalStateException("Unexpected null element at position " + currentPos);
            }
            currentPos = (currentPos + 1) % items.length;
            elementsLeft--;
            return returnItem;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof ArrayDeque)) {
            return false;
        }

        @SuppressWarnings("unchecked") // Safe due to instanceof check
        ArrayDeque<?> oo = (ArrayDeque<?>) o; // Use wildcard for type safety

        if (this.size() != oo.size()) {
            return false;
        }

        Iterator<T> thisIter = this.iterator();
        Iterator<?> ooIter = oo.iterator();

        while (thisIter.hasNext() && ooIter.hasNext()) {
            T thisItem = thisIter.next();
            Object ooItem = ooIter.next();
            if (thisItem == null) {
                if (ooItem != null) {
                    return false;
                }
            } else if (!thisItem.equals(ooItem)) {
                return false;
            }
        }

        // Ensure both iterators are exhausted (same number of elements)
        return !thisIter.hasNext() && !ooIter.hasNext();
    }
}
