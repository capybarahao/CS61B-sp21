package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import com.sun.tools.internal.ws.wsdl.document.Input;

import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int wOffset = 0;
    public static final int hOffset = 5; // free space for bottom UI
    public static Random RANDOM;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        ter.initialize(Engine.WIDTH, Engine.HEIGHT + Engine.hOffset, Engine.wOffset, Engine.hOffset);

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
        ter.initialize(Engine.WIDTH, Engine.HEIGHT + Engine.hOffset, Engine.wOffset, Engine.hOffset);

        String uprInput = input.toUpperCase();
        TETile[][] mapFrame = null;
        Avatar me = null;
        long seed;

        // if it's new game, generate initial map, generate avatar

        if (uprInput.charAt(0) == 'N') {
            // get the long seed and pass it to random
            // once set, this random object won't change
            seed = getSeedFromInput(uprInput);
            RANDOM = new Random(seed);

            mapFrame = MapGenerator.generate();// generate map. since RANDOM is set, no input needed
            me = generateNewAvatar(mapFrame);
        }

        // todo if it's load game, read save, get map, get avatar, and seed?
        else if (uprInput.charAt(0) == 'L') {

        } else { // not start with N or L, error arguments
            System.out.println("input format error");
            System.exit(0);
        }

        //  move according to following input. will get "wwsssdddd" "was:q"
        InputSource inputSource = new StringInputDevice(getMoveFromInput(uprInput));


        // render final map
        ter.renderFrame(mapFrame);
        // if :q save and quit


        return mapFrame;
    }

    private static long getSeedFromInput(String uprInput) {

        int i = 1;
        String seedString = new String();
        while (uprInput.charAt(i) != 'S') {
            char nextNum = uprInput.charAt(i);
            seedString = seedString + nextNum;
            i++;
            if (i == uprInput.length()) {
                System.out.println("input format: N#######SWWWWAASSS\nSeed end with S");
                System.exit(0);
            }
        }

        // parse seed to long
        long seed = Long.parseLong(seedString);
        return seed;
    }

    private String getMoveFromInput(String uprInput) {
        String movements = new String();

        if (uprInput.charAt(0) == 'L') {
            movements = uprInput.substring(1); // if only "L", get an empty string
        }
        else if (uprInput.charAt(0) == 'N') {
            for (int i = 0; i < uprInput.length(); i++) {
                if (uprInput.charAt(i) == 'S') {
                    movements = uprInput.substring(i+1); // if only "N141S", get an empty string
                }
            }
        }
        return movements;

    }

    private static void saveAndQuit(TETile[][] finalMap, Avatar me) {

    }


    private Avatar generateNewAvatar(TETile[][] mapFrame) {
        // find a random pos in map
        int x = 0;
        int y = 0;
        while (!mapFrame[x][y].equals(Room.floor)) {
            x = RandomUtils.uniform(Engine.RANDOM, WIDTH);
            y = RandomUtils.uniform(Engine.RANDOM, HEIGHT);
        }
        Position randomPos = new Position(x, y);

        // create avatar
        return new Avatar(randomPos, mapFrame);
    }
}
