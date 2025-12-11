package byow.Core;

import java.util.Random;

public class Position {

    public final int x;
    public final int y;
    /** full constructor
     *
     */
    public Position(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public static Position randomPos() {

        int x = RandomUtils.uniform(Engine.RANDOM, Engine.WIDTH);
        int y = RandomUtils.uniform(Engine.RANDOM, Engine.HEIGHT);
        Position pos = new Position(x, y);
        return pos;
    }
}
