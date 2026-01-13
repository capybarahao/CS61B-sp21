package byow.Input;

/**
 * Created by hug.
 */
import byow.InputDemo.InputSource;
import edu.princeton.cs.introcs.StdDraw;

public class KeyboardInput implements InputSources {
    private static final boolean PRINT_TYPED_KEYS = false;

    public char getNextKey() {
        char c = Character.toUpperCase(StdDraw.nextKeyTyped());
        if (PRINT_TYPED_KEYS) {
            System.out.print(c);
        }
        return c;
    }

    public boolean possibleNextInput() {
        return true;  // As in the demo
    }
}
