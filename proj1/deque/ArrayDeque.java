package deque;

import static java.lang.Math.abs;

public class ArrayDeque<T> {

    private T[] items;
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
        if (nextFirst < nextLast || nextLast == 0) {
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
        int nextFirst = items.length - 1;
        int nextLast = size;
    }

    public void addFirst(T item) {
        // resize condition

        items[nextFirst] = item;
        size +=1;
        nextFirst -=1;
        if (nextFirst < 0) { // if reach front border, point to end
            nextFirst = items.length - 1;
        }
    }

    public void addLast(T item) {
        // resize condition

        items[nextLast] = item;
        size += 1;
        nextLast += 1;
        if (nextLast > items.length - 1) {
            nextLast = 0;
        }
    }

    // Returns true if deque is empty, false otherwise.
    public boolean isEmpty() {
        if (nextLast - nextFirst == 1 || nextFirst - nextLast == items.length - 1) {
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
        nextFirst += 1;
        size -= 1;
        if (nextFirst > items.length - 1) {
            nextFirst = 0;
        }
        return items[nextFirst];
    }

    // Removes and returns the item at the back of the deque.
    // If no such item exists, returns null.
    public T removeLast() {
        // empty check
        if (isEmpty()) {
            return null;
        }
        nextLast -= 1;
        size -= 1;
        if (nextLast < 0) {
            nextLast = items.length;
        }
        return items[nextLast];
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
        if (nextFirst < nextLast || nextLast == 0) {
            return items[nextFirst + 1 + index];
        }
        else {
            if (nextFirst == items.length - 1) { // [2,2,4,5, , , ]
                return items[index];
            }
            else { // [5, , , , ,2,2,4]
                int L = nextFirst + 1 + index - items.length;
                return items[L];
            }
        }
    }


//    public Iterator<T> iterator() {
//
//    }
//
//    public boolean equals(Object o) {
//
//
//    }
}
