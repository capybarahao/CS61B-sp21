package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MapGenerator {

    // change room numbers(possible) here
    public static final int minRoomNum = 14;
    public static final int maxRoomNum = 20; // excluded

    public static final int mapKeyNum = 2;
    public static final TETile keyLook = Tileset.KEY;

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

        List<Room> rooms = new ArrayList<>();

        // set desired room numbers range
        int roomNum = RandomUtils.uniform(Engine.RANDOM, minRoomNum, maxRoomNum);

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
        Room.sortByPosX(rooms);

        for (int i = 0; i < rooms.size() - 1; i++) { // connect sorted rooms one by one, by generate hallway floor
            addHallways(world, rooms.get(i).randomPosInsideRoom(), rooms.get(i+1).randomPosInsideRoom());
        }

        // generate a locked door on room wall
        addDoor(rooms, world);

        // generate keys in random places
        List<Position> keys = addKeys(rooms, world);

        return world;
    }

    /**
     * "draw this room to the world"
     * @param room
     * @param world
     */
    private static void addARoomToWorld(Room room, TETile[][] world) {
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
            world[room.pos.x + i][room.pos.y] = randomPinkWall();
            world[room.pos.x + i][room.pos.y + room.height - 1] = randomPinkWall();
        }
        for (int i = 0; i < room.height; i++) {
            world[room.pos.x][room.pos.y + i] = randomPinkWall();
            world[room.pos.x + room.width - 1][room.pos.y + i] = randomPinkWall();
        }
    }

    /**
     * add a random hallway to connect two position rooms.
     * @param world
     * @param a a random position inside room
     * @param b
     */
    private static void addHallways(TETile[][] world, Position a, Position b) {
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

    private static void addWallToHallwayTile(TETile[][] world, int x, int y) {

        if (world[x][y+1] == Tileset.NOTHING) {
            world[x][y+1] = randomPinkWall();
        }
        if (world[x][y-1] == Tileset.NOTHING) {
            world[x][y-1] = randomPinkWall();
        }
        if (world[x+1][y] == Tileset.NOTHING) {
            world[x+1][y] = randomPinkWall();
        }
        if (world[x-1][y] == Tileset.NOTHING) {
            world[x-1][y] = randomPinkWall();
        }
    }

    private static List<Position> addKeys(List<Room> rooms, TETile[][] world) {
        List<Position> keyPosList = new ArrayList<>();

        for (int i = 0; i < MapGenerator.mapKeyNum; i++) {
            Room randRoom = Room.getARandomRoom(rooms);
            Position keyPos = randRoom.randomPosInsideRoom();
            keyPosList.add(keyPos);
        }

        for(Position keyPos: keyPosList) {
            world[keyPos.x][keyPos.y] = Tileset.KEY;
        }
        return keyPosList;
    }

    private static void addDoor(List<Room> rooms, TETile[][] world) {
        Room randRoom = Room.getARandomRoom(rooms);

        Position doorPos = randRoom.randomPosOnWall();
        while (!world[doorPos.x][doorPos.y].description().equals(Tileset.WALL.description())) {
            doorPos = randRoom.randomPosOnWall();
        }
        world[doorPos.x][doorPos.y] = Tileset.LOCKED_DOOR;
    }

    public static final Color[] PINK_PALETTE = {
            new Color(204, 61, 132),   // Softer Bright Pink
            new Color(204, 72, 144),   // Softer Deep Pink
            new Color(204, 120, 162),  // Softer Hot Pink
            new Color(204, 61, 169),   // Softer Vivid Pink
            new Color(204, 110, 179),  // Softer Vibrant Pink
            new Color(136, 69, 100)    // Softer Dark Pink // Dark Pink
    };

    public static TETile randomPinkWall() {
        int colorIndex = Engine.RANDOM.nextInt(PINK_PALETTE.length);
        return new TETile('#', PINK_PALETTE[colorIndex], Color.darkGray, "wall");
    }
}
