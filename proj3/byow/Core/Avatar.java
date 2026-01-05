package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;

public class Avatar implements Serializable {

    public final TETile meLook = Tileset.AVATAR;
    public int curKeyNum = 0;
    public final int viewRange = 5;
    public Position curPos;
    /** full constructor
     *
     */
    public Avatar(Position spawnPos, TETile[][] world) {
        this.curPos = spawnPos;
        world[curPos.x][curPos.y] = meLook;
    }

    /**
     * generate one avatar in a random floor position
     * @param mapFrame
     * @return
     */
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


    public void moveTo(Position tPos, TETile[][] world) {
        // if move to key position, add key
        if (world[tPos.x][tPos.y].description().equals(Tileset.KEY.description())) {
            this.curKeyNum += 1;
            world[curPos.x][curPos.y] = Room.floor;
            this.curPos = tPos;
            world[curPos.x][curPos.y] = meLook;
        }
        // if movable , move to the floor tile
        else if (world[tPos.x][tPos.y].description().equals(Room.floor.description())) {
            world[curPos.x][curPos.y] = Room.floor;
            this.curPos = tPos;
            world[curPos.x][curPos.y] = meLook;
        } else if (world[tPos.x][tPos.y].description().equals(Room.wall.description())) { // if wall
            return;
        }
        else if (world[tPos.x][tPos.y].description().equals(Tileset.LOCKED_DOOR.description())) {
            // todo
            // if enough keys win
            if (this.curKeyNum == MapGenerator.mapKeyNum) {
                Engine.gameSuccess = true;
            }
        }
        else {
            return;
        }
    }

}
