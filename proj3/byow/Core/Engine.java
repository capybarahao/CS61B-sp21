package byow.Core;

import byow.Input.InputSources;
import byow.Input.StringInput;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

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
        long seed = 0;

        // if it's new game, generate initial map, generate avatar,

        if (uprInput.charAt(0) == 'N') {
            // get the long seed and pass it to random
            // once set, this random object won't change
            seed = Utils.getSeedFromInput(uprInput);
            RANDOM = new Random(seed);

            mapFrame = MapGenerator.generate();// generate map. since RANDOM is set, no input needed
            me = Avatar.generateNewAvatar(mapFrame);
        }

        // todo if it's load game, read save, get map, get avatar, and seed
        else if (uprInput.charAt(0) == 'L') {
            
            
            
            

        } else { // not start with N or L, error arguments
            System.out.println("input format error");
            System.exit(0);
        }

        //  MOVE according to following input. will get like "" "wwsssdddd" "was:q" ":q"
        InputSources inputSource = new StringInput(Utils.getMoveFromInput(uprInput));

        while (inputSource.possibleNextInput()) {
            char c = inputSource.getNextKey();
            if (c == ':' && inputSource.possibleNextInput() && inputSource.getNextKey() == 'Q') { // detect :q
                // todo save and quit



                saveProgress(mapFrame, me, seed);
                return mapFrame;
            }
            Position targetPos = Position.nextMovePos(me, c);
            me.moveTo(targetPos, mapFrame);
        }

        // render final map
        ter.renderFrame(mapFrame);

        // if :q save and quit




        return mapFrame;
    }

    private static void saveProgress(TETile[][] finalMap, Avatar me, long seed) {
        Save newSave = new Save(finalMap, me , seed);




    }

    private static Save loadProgress() {
        Save save = null;





        return save;
    }
}
