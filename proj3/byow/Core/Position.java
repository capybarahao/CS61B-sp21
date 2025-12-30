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

    public static Position randomPosAnyWhere() {

        // todo: Ensure it's within bounds (x from 1 to WIDTH - width - 1, same for y).

        int x = RandomUtils.uniform(Engine.RANDOM, Engine.WIDTH - Room.maxWidth);
        int y = RandomUtils.uniform(Engine.RANDOM, Engine.HEIGHT- Room.maxHeight);
        Position pos = new Position(x, y);
        return pos;
    }
}
