package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Room {

    public final int width;
    public final int height;
    public final Position pos;
    public final TETile wall = Tileset.WALL;
    public final TETile floor = Tileset.SAND;
    // change room size(possible) here
    public static final int maxWidth = 8;
    public static final int maxHeight = 8;


    /** full constructor
     *
     */
    public Room(int width, int height, Position pos){

        this.width = width;
        this.height = height;
        this.pos = pos;
    }

    public static Room randomSizePosRoom() {

        int w = RandomUtils.uniform(Engine.RANDOM, 3, maxWidth);
        int h = RandomUtils.uniform(Engine.RANDOM, 3, maxHeight);
        Position pos = Position.randomPos();

        Room rRoom = new Room(w, h, pos);
        return rRoom;
    }
}
