package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static Random RANDOM;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        // get the long seed and pass it to random
        // once set, this random object won't change
        long seed = getSeedFromInput(input);
        RANDOM = new Random(seed);

        // Map generator
        TETile[][] finalWorldFrame = MapGenerator.generate();

        return finalWorldFrame;
    }

    private long getSeedFromInput(String input) {

        String upprInput = input.toUpperCase();

        if (upprInput.charAt(0) != 'N') {
            System.out.println("input format: N#######SWWWWAASSS\nStart with N");
            System.exit(0);
        }
        int i = 1;
        String seedString = new String();
        while (upprInput.charAt(i) != 'S') {
            char nextNum = upprInput.charAt(i);
            seedString = seedString + nextNum;
            i++;
            if (i == upprInput.length()) {
                System.out.println("input format: N#######SWWWWAASSS\nSeed end with S");
                System.exit(0);
            }
        }

        // parse seed to long
        long seed = Long.parseLong(seedString);
        return seed;

    }
}
