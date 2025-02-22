package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimes1() {
        IntList lst = IntList.of(33, 52, 16, 11, 89);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("33 -> 52 -> 16 -> 121 -> 7921", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimes2() {
        IntList lst = IntList.of(22, 44, 22, 1060, 500);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("22 -> 44 -> 22 -> 1060 -> 500", lst.toString());
        assertFalse(changed);
    }

    @Test
    public void testSquarePrimes3() {
        IntList lst = IntList.of(71, 44, 97, 41, 47);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("5041 -> 44 -> 9409 -> 1681 -> 2209", lst.toString());
        assertTrue(changed);
    }

    }
