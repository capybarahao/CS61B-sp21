package byow.Core;

import byow.TileEngine.TETile;

public class Save {
    private TETile[][] finalMap;
    private Avatar me;
    private long seed;

    public Save(TETile[][] finalMap, Avatar me, long seed) {
        this.finalMap = finalMap;
        this.me = me;
        this.seed = seed;
    }
}
