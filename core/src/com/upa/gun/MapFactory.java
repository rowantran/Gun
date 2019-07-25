package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import java.util.HashMap;
import java.util.Map;

public class MapFactory {

    public Map<Integer, MapInfo> maps;

    public MapFactory(String path) {

        maps = new HashMap<Integer, MapInfo>();

        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(Gdx.files.internal(path)).get("maps");

        for(JsonValue map : root) {

            int id = map.getInt("id");

            JsonValue crateVals = map.get("crates");
            int[][] tops = new int[crateVals.size][];

            for(int i = 0; i < crateVals.size; i++) {
                int[] vals = crateVals.get(i).asIntArray();
                tops[i] = vals;
            }

            int[][] sides = new int[tops.length][tops[0].length];

            for(int i = 0; i < tops.length - 1; i++) {
                for(int j = 0; j < tops[i].length; j++) {
                    if(tops[i][j] == 1 && tops[i+1][j] == 0) {
                        sides[i][j] = 1;
                    }
                    else {
                        sides[i][j] = 0;
                    }
                }
            }
            for(int i = 0; i < tops[tops.length-1].length; i++) {
                if(tops[tops.length-1][i] == 1) {
                    sides[tops.length-1][i] = 1;
                }
                else {
                    sides[tops.length-1][i] = 0;
                }
            }

            MapInfo info = new MapInfo(id, tops, sides);
            maps.put(id, info);

        }

        Gdx.app.log("MapFactory", "Completed loading maps");
    }

    MapLayout createMap(int id) { return new MapLayout(maps.get(id)); }

}
