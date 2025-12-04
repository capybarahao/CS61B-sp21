package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private static final long SEED = 28731;
    private static final Random RANDOM = new Random(SEED);

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
        // change tiletype in here
        TETile tileType = Tileset.WALL;

        addHexagons(3, 2, world, 50, 50);

        // draw
        ter.renderFrame(world);

    }

    /**
     * prepare tessellated hexagons in the world (n layers of ring around the center hexagon)
     * with random tile type per hexagon
     * @param s
     * @param n n is the number of rings, larger n means larger hexagons group.
     * @param world
     * @param X world position of the first character in upper row(of the center two rows)
     *          OF the center hexagon
     * @param Y ditto
     */
    private static void addHexagons(int s, int n, TETile[][] world, int X, int Y) {
        // create a list of lists, indicating the start positions of each hexagon
        List<List<Integer>> newStartPos = new ArrayList<>(List.of());

        newStartPos.add(Arrays.asList(X, Y));

        for (int i = 0; i < n; i++) {
            List<List<Integer>> temp = new ArrayList<>(List.of());
            List<List<Integer>> sum = new ArrayList<>(List.of());
            for (List<Integer> pos: newStartPos) {

                temp = getStartPosOfGivenCenter(s, pos.get(0), pos.get(1));
                sum.addAll(temp);
            }

            // This single line removes all duplicates and keeps the first occurrence order
            List<List<Integer>> unique = sum.stream()
                    .distinct()
                    .collect(Collectors.toList());

            newStartPos = unique;
        }

        for (List<Integer> pos: newStartPos){
            addHexagon(s, randomTile(), world, pos.get(0), pos.get(1));
        }



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
     * prepare a hexagon of side length s to a given position in the world
     * @param s a hexagon of side length s
     * @param tileType tile's type like WALL, WATER etc.
     * @param X Y world position of the first character in upper row(of the center two rows)
     */
    private static void addHexagon(int s, TETile tileType, TETile[][] world, int X, int Y) {

        List<int[]> hexPos = oneHexPos(s, X, Y);
        for (int[] pos: hexPos) {
            world[pos[0]][pos[1]] = tileType;
        }
        return;
    }

    /**
     * a list of arrays, indicating positions of all characters in one hexagon
     * @param s
     * @param X
     * @param Y
     * @return
     */
    private static List<int[]> oneHexPos(int s, int X, int Y) {
        int width = hexWidth(s);
        int halfHeight = s;
        List<int[]> hexPos = new java.util.ArrayList<>(List.of());

        for (int i = 0; i < halfHeight; i += 1) {
            for (int j = i; j < width - i; j += 1) {
                // top part of hex
                int[] topPos = new int[2];
                topPos[0] = X+j;
                topPos[1] = Y+i;
                hexPos.add(topPos);
                // lower part of hex
                int[] bottomPos = new int[2];
                bottomPos[0] = X+j;
                bottomPos[1] = Y-1-i;
                hexPos.add(bottomPos);
            }
        }
        return hexPos;
    }

    /**
     *
     * @return the max width of a hexagon of side length s
     */
    private static int hexWidth(int s) {
        return (s-1) * 3 +1;
    }

    /**
     * given the world position(start position) of the center hexagon
     * return a list of start positions of all tessellated hexagons around it
     * (inclusive, 7 in total)
     */
    private static List<List<Integer>> getStartPosOfGivenCenter(int s, int X, int Y) {
        int h = s * 2;
        int w = s * 2 - 1;
        List<List<Integer>> startPos = new java.util.ArrayList<>(List.of());

        startPos.add(Arrays.asList(X, Y)); // center
        startPos.add(Arrays.asList(X, Y + h)); // top
        startPos.add(Arrays.asList(X, Y - h)); // bottom
        startPos.add(Arrays.asList(X - w, Y + h/2)); // top left
        startPos.add(Arrays.asList(X + w, Y + h/2)); // top right
        startPos.add(Arrays.asList(X - w, Y - h/2)); // bottom left
        startPos.add(Arrays.asList(X + w, Y - h/2)); // bottom right

        return startPos;
    }

    /** Picks a RANDOM tile with a 33% change of being
     *  a wall, 33% chance of being a flower, and 33%
     *  chance of being empty space.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.WATER;
            default: return Tileset.NOTHING;
        }
    }
}
