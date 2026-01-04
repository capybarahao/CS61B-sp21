package byow.Core;

import byow.TileEngine.TETile;

import java.io.Serializable;

public class Save implements Serializable {
    private TETile[][] mapFrame;
    private Avatar me;
    private long seed;

    public Save(TETile[][] mapFrame, Avatar me, long seed) {
        this.mapFrame = mapFrame;
        this.me = me;
        this.seed = seed;
    }

    public TETile[][] getMapFrame() {
        return mapFrame;
    }

    public long getSeed() {
        return seed;
    }

    public Avatar getMe() {
        return me;
    }

}
