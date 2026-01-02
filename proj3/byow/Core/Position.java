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

    /**
     * pass char other than WASD return current me position
     * @param me
     * @param direction
     * @return
     */
    public static Position nextMovePos(Avatar me, char direction) {
        int x = me.curPos.x;
        int y = me.curPos.y;
        switch (direction) {
            case 'W':
                x = me.curPos.x;
                y = me.curPos.y + 1;
                break;
            case 'A':
                x = me.curPos.x - 1;
                y = me.curPos.y;
                break;
            case 'S':
                x = me.curPos.x;
                y = me.curPos.y - 1;
                break;
            case 'D':
                x = me.curPos.x + 1;
                y = me.curPos.y;
                break;
            default:
                break;
        }
        return new Position(x, y);
    }
}
