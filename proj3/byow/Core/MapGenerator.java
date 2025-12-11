package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

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

        // generate rooms
        // pass TETile[][] world, RANDOM,
        Room newRoom = Room.randomSizePosRoom();
        addARoomToWorld(newRoom, world);


        // generate hallways


        return world;
    }

    public static void addARoomToWorld(Room room, TETile[][] world) {
        // validate if it can be added(overlap check, out of map check


        // add a room to world
        for (int i = 0; i < room.width; i++) {
            for (int j = 0; j < room.height; j++) {
                world[room.pos.x + i][room.pos.y + j] = room.floor;
            }
        }


    }


}
