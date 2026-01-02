package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;

public class Avatar {

    public final TETile meLook = Tileset.AVATAR;
    public int keyNum = 0;
    public final int viewRange = 5;
    public Position curPos;
    /** full constructor
     *
     */
    public Avatar(Position spawnPos, TETile[][] world) {
        this.curPos = spawnPos;
        world[curPos.x][curPos.y] = meLook;
    }


    public static Avatar generateNewAvatar(TETile[][] mapFrame) {
        // find a valid random pos in map
        int x = 0;
        int y = 0;
        while (!mapFrame[x][y].equals(Room.floor)) {
            x = RandomUtils.uniform(Engine.RANDOM, Engine.WIDTH);
            y = RandomUtils.uniform(Engine.RANDOM, Engine.HEIGHT);
        }
        Position randomPos = new Position(x, y);

        // create avatar
        return new Avatar(randomPos, mapFrame);
    }


    public void moveTo(Position targetPos, TETile[][] world) {
        // if move to key position, add key
        if (world[targetPos.x][targetPos.y].equals(Tileset.KEY)) {
            this.keyNum += 1;
            world[curPos.x][curPos.y] = Room.floor;
            this.curPos = targetPos;
            world[curPos.x][curPos.y] = meLook;
        }
        // if movable , move to the floor tile
        else if (world[targetPos.x][targetPos.y].equals(Room.floor)) {
            world[curPos.x][curPos.y] = Room.floor;
            this.curPos = targetPos;
            world[curPos.x][curPos.y] = meLook;
        } else if (world[targetPos.x][targetPos.y].equals(Room.wall)) { // if wall
            return;
        }
        else if (world[targetPos.x][targetPos.y].equals(Tileset.LOCKED_DOOR)) {
            // todo
            // win



        }
        else {
            return;
        }

    }

}
