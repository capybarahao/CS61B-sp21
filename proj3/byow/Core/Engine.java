package byow.Core;

import byow.Input.InputSources;
import byow.Input.KeyboardInput;
import byow.Input.StringInput;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int wOffset = 5;
    public static final int hOffset = 5; // free space for bottom UI
    public static Random RANDOM;
    public static boolean gameSuccess = false;

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The SAVE file. */
    public static final File saveFile = Utils.join(CWD,  "byow/savefile.txt");


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        // initialize TERenderer
        ter.initialize(Engine.WIDTH + wOffset, Engine.HEIGHT + Engine.hOffset, Engine.wOffset, Engine.hOffset);
        TETile[][] mapFrame = null;
        Avatar me = null;
        long seed = 0;
        boolean wholeView = false;

        showMainMenu(); // change main menu style here
        char cmd = Utils.solicitCommand();
        switch (cmd) {
            case 'N':
                //starNewGame;
                String seedString = Utils.solicitSeed();
                seed = Utils.getSeedFromInput(seedString);
                RANDOM = new Random(seed);

                mapFrame = MapGenerator.generate();// generate map. since RANDOM is set, no input needed
                me = Avatar.generateNewAvatar(mapFrame);
                break;
            case 'L':
                //load, set data
                Utils.checkSavefileExist(saveFile);
                Save loadedSave = Utils.readObject(saveFile, Save.class);
                mapFrame = loadedSave.getMapFrame();
                me = loadedSave.getMe();
                seed = loadedSave.getSeed();
                RANDOM = new Random(seed);

                break;
            case 'Q':
                System.out.println("quited");
                System.exit(0);
        }

        // Add state variables outside the loop
        boolean awaitingCommand = false;  // Tracks if we're waiting for a command after ':'

        while (!gameSuccess) {
            InputSources inputSource = new KeyboardInput();  // Could be created outside loop if not changing

            // Non-blocking input handling using StdDraw directly for check
            if (StdDraw.hasNextKeyTyped()) {
                char c = inputSource.getNextKey();  // This now returns immediately since we checked hasNextKeyTyped()

                if (awaitingCommand) {
                    // We were waiting after ':'
                    if (c == 'Q') {
                        // Full :Q detected—save and quit
                        saveProgressAndQuit(mapFrame, me, seed);
                    } else {
                        // Not Q: Ignore or handle as new input
                        // For example, process this c as a regular key if desired
                        if (c == 'V') {
                            wholeView = !wholeView;
                        } else {
                            me.moveTo(Position.nextMovePos(me, c), mapFrame);
                        }
                    }
                    awaitingCommand = false;  // Reset state
                } else if (c == ':') {
                    // Start awaiting the next key for command
                    awaitingCommand = true;
                    // Check if there's already a next key queued (for fast typing)
                    if (StdDraw.hasNextKeyTyped() && inputSource.getNextKey() == 'Q') {
                        saveProgressAndQuit(mapFrame, me, seed);
                    }
                } else if (c == 'V') {
                    wholeView = !wholeView;
                } else {
                    // Handle movement
                    me.moveTo(Position.nextMovePos(me, c), mapFrame);
                }
            }

            // Always render/update display, even without input
            if (wholeView) {
                ter.renderFrame(mapFrame);
            } else {
                ter.renderLimitFrame(mapFrame, me);
            }
            drawUI(me);
            drawRigidUI();
            showMouseHover(mapFrame);  // Updates in real-time

            // Show the frame and pause
            StdDraw.show();
            StdDraw.pause(20);  // ~50 FPS
        }

        showSuccess();

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

        // if it's load game, read save, get map, get avatar, and seed
        else if (uprInput.charAt(0) == 'L') {
            Utils.checkSavefileExist(saveFile);
            Save loadedSave = Utils.readObject(saveFile, Save.class);
            mapFrame = loadedSave.getMapFrame();
            me = loadedSave.getMe();
            seed = loadedSave.getSeed();
            RANDOM = new Random(seed);

        } else { // not start with N or L, error arguments
            System.out.println("input format error");
            System.exit(0);
        }

        //  MOVE according to following input after"N1212S" or "L"
        //  will get like "" "wwsssdddd" "was:q" ":q"
        InputSources inputSource = new StringInput(Utils.getMoveFromStringInput(uprInput));

        /// //////// this part align with interactKeyboard. change both
        while (inputSource.possibleNextInput()) {
            char direction = inputSource.getNextKey();
            if (direction == ':' && inputSource.possibleNextInput() && inputSource.getNextKey() == 'Q') { // detect :q
                // save and quit
                saveProgressAndQuit(mapFrame, me, seed);
            }
            me.moveTo(Position.nextMovePos(me, direction), mapFrame);
        }
        /// ////////

        return mapFrame;
    }

    private static void saveProgressAndQuit(TETile[][] finalMap, Avatar me, long seed) {
        try {
            saveFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Save newSave = new Save(finalMap, me , seed);
        Utils.writeObject(saveFile, newSave);
        System.out.println("saved");
        System.exit(0);
    }

    private static void showMainMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font originFont = StdDraw.getFont();
        Font font = new Font("Monospaced", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(Engine.WIDTH /2, Engine.HEIGHT /2 +5, "CS61B: Maze");
        StdDraw.text(Engine.WIDTH /2, Engine.HEIGHT /2 +2, "New Game (N)");
        StdDraw.text(Engine.WIDTH /2, Engine.HEIGHT /2, "Load Game (L)");
        StdDraw.text(Engine.WIDTH /2, Engine.HEIGHT /2 -2, "Quit (Q)");

        // set font back
        StdDraw.setFont(originFont);
        StdDraw.show();
    }

    private static void showSuccess() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font originFont = StdDraw.getFont();
        Font font = new Font("Monospaced", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(Engine.WIDTH /2, Engine.HEIGHT /2 +5, "Phew, that was fun...");

        // set font back
        StdDraw.setFont(originFont);

        StdDraw.show();
    }

    private static void drawUI(Avatar me) {

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.line(0, 2, Engine.WIDTH + wOffset, 2);
        Font originFont = StdDraw.getFont();
        Font font = new Font("SansSerif", Font.BOLD, 14);
        StdDraw.setFont(font);

        int curKeyNum = me.curKeyNum;
        StdDraw.text(Engine.WIDTH/2, 1, "Key: " + String.valueOf(curKeyNum));

        String reminder;
        if (me.curKeyNum == MapGenerator.mapKeyNum) {
            reminder = "Find Door!";

        }
        else {
            reminder = "Find All Keys!";
        }
        StdDraw.text(Engine.WIDTH/2, 3, reminder);

        // set font back
        StdDraw.setFont(originFont);

        StdDraw.show();
    }
    private static void showMouseHover(TETile[][] mapFrame) {
        // Get mouse coordinates
        double mouseXDouble = StdDraw.mouseX();
        double mouseYDouble = StdDraw.mouseY();

        // Convert to tile indices, subtracting offsets
        int tileX = (int) mouseXDouble - Engine.wOffset;
        int tileY = (int) mouseYDouble - Engine.hOffset;

        // Check if mouse is within map bounds (after offset adjustment)
        int width = mapFrame.length;  // Should be Engine.WIDTH
        int height = mapFrame[0].length;  // Should be Engine.HEIGHT
        if (tileX >= 0 && tileX < width && tileY >= 0 && tileY < height) {
            TETile tile = mapFrame[tileX][tileY];
            String tileType = tile.description();  // Assuming description() returns the type string

            // Draw the tile type (position adjusted if needed; here kept as bottom-right)
            StdDraw.setPenColor(Color.WHITE);
            Font originFont = StdDraw.getFont();
            Font font = new Font("SansSerif", Font.BOLD, 14);
            StdDraw.setFont(font);
            StdDraw.text(Engine.WIDTH - 4, 1, tileType);  // Add offsets to text position if UI is shifted
            StdDraw.setFont(originFont);  // Reset font
        }
    }

    private static void drawRigidUI() {
        StdDraw.setPenColor(Color.WHITE);
        Font originFont = StdDraw.getFont();
        Font font = new Font("SansSerif", Font.BOLD, 14);
        StdDraw.setFont(font);

        StdDraw.text(7, 4, "Move - WASD View - V");
        StdDraw.text(6, 3, "Save & Quit - :q");

        // set font back
        StdDraw.setFont(originFont);

        StdDraw.show();
    }


}
