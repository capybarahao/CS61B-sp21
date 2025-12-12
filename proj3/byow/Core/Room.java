package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;
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

    public boolean overlapWithCurRooms(List<Room> rooms) {
        // iterate each room in the list
        for (Room other: rooms) {
            if (this.overlap(other)) {
                return true;
            }
        }
        return false;
    }

    public boolean overlap(Room other) {
        // No overlap if one rectangle is completely to the left, right, above, or below the other
        if (this.pos.x + this.width <= other.pos.x || other.pos.x + other.width <= this.pos.x) {
            return false;
        }
        if (this.pos.y + this.height <= other.pos.y || other.pos.y + other.height <= this.pos.y) {
            return false;
        }
        return true;
    }


}
