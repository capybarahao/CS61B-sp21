package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator<T> comparator;
    // creates a MaxArrayDeque with the given Comparator.
    // Comparator<string> lencomp = new MaxArrayDeque.getLengthComparator()
    // MaxArrayDeque<String> strDeque = new MaxArrayDeque<>(lencomp)
    public MaxArrayDeque(Comparator<T> c){
        this.comparator = c;
    }
    // returns the maximum element in the deque as governed by the previously given Comparator.
    // If the MaxArrayDeque is empty, simply return null.
    // use: cat max = catArray.max()
    public T max(){
        // check empty
        if (this.isEmpty()) {
            return null;
        }
        // set a max-till-now index
        T maxCurr = get(0);
        // loop thru array to find max. return the max-till-now index
        for (int i = 0; i < size(); i++) {
            T curr = get(i);
            if (comparator.compare(curr, maxCurr) > 0) {
                maxCurr = curr;
            }
        }
        // return the element
        return maxCurr;
    }
    // returns the maximum element in the deque as governed by the parameter Comparator c.
    // If the MaxArrayDeque is empty, simply return null.
    // use: cat max = catArray.max(comp)
    public T max(Comparator<T> c){
        // check empty
        if (this.isEmpty()) {
            return null;
        }
        // set a max-till-now index
        T maxCurr = get(0);
        // loop thru array to find max. return the max-till-now index
        for (int i = 0; i < size(); i++) {
            T curr = get(i);
            if (c.compare(curr, maxCurr) > 0) {
                maxCurr = curr;
            }
        }
        // return the element
        return maxCurr;

    }
    // Use: Comparator<string> lenComp = new MaxArrayDeque.getLengthComparator()
    private static class LengthComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            return s1.length() - s2.length(); // Compare based on string length
        }
    }
    public Comparator<String> getLengthComparator() {
        return new LengthComparator();
    }

}
