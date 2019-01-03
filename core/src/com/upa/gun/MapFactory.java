package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;
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

            JsonValue topsVals = map.get("tops");
            int[][] tops = new int[topsVals.size][];
            for(int i = 0; i < topsVals.size; i++) {
                int[] vals = topsVals.get(i).asIntArray();
                tops[i] = vals;
            }

            JsonValue sidesVals = map.get("sides");
            int[][] sides = new int[sidesVals.size][];
            for(int i = 0; i < sidesVals.size; i++) {
                int[] vals = sidesVals.get(i).asIntArray();
                sides[i] = vals;
            }

            MapInfo info = new MapInfo(id, tops, sides);
            maps.put(id, info);

        }

        Gdx.app.log("MapFactory", "Completed loading maps");
    }

    MapLayout createMap(int id) { return new MapLayout(maps.get(id)); }

}
