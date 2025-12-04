package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);


        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        addHexagon(5, world, 0, 10);

        ter.renderFrame(world);


    }


    //     aa        aaa         aaaa             aaaaa
    //    aaaa      aaaaa       aaaaaa           aaaaaaa
    //    aaaa     aaaaaaa     aaaaaaaa         aaaaaaaaa
    //     aa      aaaaaaa    aaaaaaaaaa       aaaaaaaaaaa
    //              aaaaa     aaaaaaaaaa      aaaaaaaaaaaaa
    //               aaa       aaaaaaaa       aaaaaaaaaaaaa
    //                          aaaaaa         aaaaaaaaaaa
    //                           aaaa           aaaaaaaaa
    //                                           aaaaaaa
    //                                            aaaaa
    /**
     * adds a hexagon of side length s to a given position in the world
     * center two row length (hexHeight): (s-1) * 3 + 1
     * hex height is exactly s * 2
     * @param X Y world position of the first character in upper row(of the center two rows)
     */
    private static void addHexagon(int s, TETile[][] world, int X, int Y) {
        int width = hexWidth(s);
        int halfHeight = s;
        for (int i = 0; i < halfHeight; i += 1) {
            for (int j = i; j < width - i; j += 1) {
                // top part of hex
                world[X+j][Y+i] = Tileset.WALL;
                // lower part of hex
                world[X+j][Y-1-i] = Tileset.WALL;
            }
        }
        return;
    }



    private static int hexWidth(int s) {
        int w = (s-1) *3 +1;
        return w;
    }

//    private static List<int[]> hexPos(int s, int X, int Y) {
//        int width = hexWidth(s);
//        int halfHeight = s;
//        List<int[]> hexPos;
//
//
//        for (int i = 0; i < halfHeight; i += 1) {
//            for (int j = i; j < width - i; j += 1) {
//                int[] pos = new int[2];
//                // top part of hex
//                pos[0] = X+j;
//                pos[1] = Y+i;
//                world[X+j][Y+i] = Tileset.WALL;
//                // lower part of hex
//
//                world[X+j][Y-1-i] = Tileset.WALL;
//            }
//        }
//
//    }
}
