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

            JsonValue mapVals = map.get("map");
            int[][] all = new int[mapVals.size][];

            for(int i = 0; i < mapVals.size; i++) {
                int[] vals = mapVals.get(i).asIntArray();
                all[i] = vals;
            }

            MapInfo info = new MapInfo(id, all);
            maps.put(id, info);

        }

        Gdx.app.log("MapFactory", "Completed loading maps");
    }

    MapLayout createMap(int id) { return new MapLayout(maps.get(id)); }

}
