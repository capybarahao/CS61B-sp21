package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Room {

    public final int width;
    public final int height;
    public final Position pos;
    public static final TETile wall = Tileset.WALL;
    public static final TETile floor = Tileset.SAND;
    // change room size(possible) here
    public static final int minWidth = 6;
    public static final int minHeight = 6;
    public static final int maxWidth = 10;
    public static final int maxHeight = 10;


    /** full constructor
     *
     */
    public Room(int width, int height, Position pos){

        this.width = width;
        this.height = height;
        this.pos = pos;
    }

    public static Room randomSizePosRoom() {

        int w = RandomUtils.uniform(Engine.RANDOM, minWidth, maxWidth);
        int h = RandomUtils.uniform(Engine.RANDOM, minHeight, maxHeight);
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

    /**
     * sort rooms in order of x, that is, the left most will be placed in the front
     * @param rooms
     */
    public static void sortByPosX(List<Room> rooms) {
        rooms.sort(Comparator.comparingInt(r -> r.pos.x));
    }

    /**
     *
     * @return a random position inside this room, excluding wall
     */
    public Position randomConnectPos(){
        int x = RandomUtils.uniform(Engine.RANDOM, pos.x + 1, pos.x + width - 2);
        int y = RandomUtils.uniform(Engine.RANDOM, pos.y + 1, pos.y + height - 2);
        Position randomConnectPos = new Position(x, y);
        return randomConnectPos;
    }

}
