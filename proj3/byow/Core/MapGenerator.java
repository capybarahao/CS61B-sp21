package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

/**
 *
 */
public class MapGenerator {


    // temp main for phase 1
    public static void main(String[] args) {

        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(Engine.WIDTH, Engine.HEIGHT);

        Engine engine = new Engine();
        TETile[][] dungeonWorld = engine.interactWithInputString(args[0]);

        // draw
        ter.renderFrame(dungeonWorld);
    }


    /**
     * return a TETile[][] of world map
     */
    public static TETile[][] generate() {

        // initialize
        TETile[][] world = new TETile[Engine.WIDTH][Engine.HEIGHT];
        // fill with tile.nothing
        for (int x = 0; x < Engine.WIDTH; x += 1) {
            for (int y = 0; y < Engine.HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // generate rooms, a list of rooms
        //

        List<Room> rooms = new ArrayList<>();

        // desired room numbers range
        int roomNum = RandomUtils.uniform(Engine.RANDOM, 10, 15);

        int maxAttempts = 1000;
        while (rooms.size() < roomNum) {
            Room newRoom = Room.randomSizePosRoom();

            if (!newRoom.overlapWithCurRooms(rooms)) {
                rooms.add(newRoom);
            }

            // avoid infinite loops.
            maxAttempts -= 1;
            if (maxAttempts == 0) {
                break;
            }
        }

        for (Room room: rooms) {
            addARoomToWorld(room, world);
        }

        // generate hallways
        // todo

        Room.sortByPosX(rooms);

        // connect sorted rooms by generate hallway floor
        for (int i = 0; i < rooms.size() - 1; i++) {
            addHallwayFloor(world, rooms.get(i).randomConnectPos(), rooms.get(i+1).randomConnectPos());
        }


        return world;
    }


    /**
     * "draw this room to the world"
     * @param room
     * @param world
     */
    public static void addARoomToWorld(Room room, TETile[][] world) {
        // validate if it can be added(overlap check, out of map check


        // add a room to world
        // all floor first
        for (int i = 0; i < room.width; i++) {
            for (int j = 0; j < room.height; j++) {
                world[room.pos.x + i][room.pos.y + j] = Room.floor;
            }
        }

        // walls then
        for (int i = 0; i < room.width; i++) {
            world[room.pos.x + i][room.pos.y] = Room.wall;
            world[room.pos.x + i][room.pos.y + room.height - 1] = Room.wall;
        }
        for (int i = 0; i < room.height; i++) {
            world[room.pos.x][room.pos.y + i] = Room.wall;
            world[room.pos.x + room.width - 1][room.pos.y + i] = Room.wall;
        }
    }

    /**
     * add a random hallway (just floor) to connect two position rooms.
     * @param world
     * @param a a random position inside room
     * @param b
     */
    public static void addHallwayFloor(TETile[][] world, Position a, Position b) {
        int dx = b.x - a.x;
        int dy = b.y - a.y;

        // decide randomly, from pos a, go horizontal first or vertical.
        boolean drawHorizontalFirst = RandomUtils.bernoulli(Engine.RANDOM);

        // bit complicated but it works :) don't change
        int stepX = Integer.signum(dx);
        int stepY = Integer.signum(dy);

        int x = a.x;
        int y = a.y;

        if (drawHorizontalFirst) {
            // draw horizontal first
            for (int i = 0; i != dx; i += stepX) {
                world[x][y] = Room.floor;
                addWallToHallwayTile(world, x, y);
                x += stepX;
            }
            // then vertical
            for (int i = 0; i != dy; i += stepY) {
                world[x][y] = Room.floor;
                addWallToHallwayTile(world, x, y);
                y += stepY;
            }
        } else {
            // draw vertical first
            for (int i = 0; i != dy; i += stepY) {
                world[x][y] = Room.floor;
                addWallToHallwayTile(world, x, y);
                y += stepY;
            }
            // then horizontal
            for (int i = 0; i != dx; i += stepX) {
                world[x][y] = Room.floor;
                addWallToHallwayTile(world, x, y);
                x += stepX;
            }
        }
    }

    public static void addWallToHallwayTile(TETile[][] world, int x, int y) {
        if (world[x][y+1] == Tileset.NOTHING) {
            world[x][y+1] = Room.wall;
        }
        if (world[x][y-1] == Tileset.NOTHING) {
            world[x][y-1] = Room.wall;
        }
        if (world[x+1][y] == Tileset.NOTHING) {
            world[x+1][y] = Room.wall;
        }
        if (world[x-1][y] == Tileset.NOTHING) {
            world[x-1][y] = Room.wall;
        }
    }
}
