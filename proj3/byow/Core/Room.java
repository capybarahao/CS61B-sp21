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
    public static final TETile floor = Tileset.FLOOR;
    // change room size(possible) here
    public static final int minWidth = 6;
    public static final int minHeight = 6;
    public static final int maxWidth = 11; // excluded
    public static final int maxHeight = 11; // excluded


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
        Position pos = Position.randomPosAnyWhere();

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
    public Position randomPosInsideRoom(){
        int x = RandomUtils.uniform(Engine.RANDOM, this.pos.x + 1, this.pos.x + this.width - 1);
        int y = RandomUtils.uniform(Engine.RANDOM, this.pos.y + 1, this.pos.y + this.height - 1);
        Position randomPosInsideRoom = new Position(x, y);
        return randomPosInsideRoom;
    }
    //  ######
    // #      #
    // #      #
    //  ######
    public Position randomPosOnWall(){
        List<Position> wallTiles = new ArrayList<>();

        for (int i = 0; i < this.width - 2; i++) {
            int x = this.pos.x + 1 + i;
            int y = this.pos.y;

            wallTiles.add(new Position(x, y));
            wallTiles.add(new Position(x, y + this.height -1));
        }


        for (int i = 0; i < this.height - 2; i++) {
            int x = this.pos.x;
            int y = this.pos.y  + 1 + i;

            wallTiles.add(new Position(x, y));
            wallTiles.add(new Position(x + this.width - 1, y));
        }
        int randIndex = RandomUtils.uniform(Engine.RANDOM, 0, wallTiles.size());
        Position randPosOnWall = wallTiles.get(randIndex);
        return randPosOnWall;
    }

    public static Room getARandomRoom(List<Room> rooms) {
        // shuffle rooms and get the first one room
        int randIndex = RandomUtils.uniform(Engine.RANDOM, 0, rooms.size());
        Room randRoom = rooms.get(randIndex);
        return randRoom;
    }


}
