package com.upa.gun.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WaveFactory {

    public Map<Integer, HashMap<Integer, WaveInfo>> waves;

    public WaveFactory(String path) {

        waves = new HashMap<Integer, HashMap<Integer, WaveInfo>>();

        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(Gdx.files.internal(path)).get("waves");

        for(JsonValue difficulty : root) {

            int id = difficulty.getInt("id");

            JsonValue enemyVals = difficulty.get("enemies");
            JsonValue delayVals = difficulty.get("delays");

            HashMap type = new HashMap<Integer, WaveInfo>();

            for(int i = 0; i < enemyVals.size; i++) {

                ArrayList<int[]> wave = new ArrayList<int[]>();

                for(int j = 0; j < enemyVals.get(i).size; j++) {

                    int[] temp = enemyVals.get(i).get(j).asIntArray();
                    wave.add(temp);

                }

                int[] delays = delayVals.get(i).asIntArray();

                WaveInfo info = new WaveInfo(id, wave, delays);

                type.put(i, info);

            }
            waves.put(id, type);
        }

        Gdx.app.log("WaveFactory", "Completed loading waves");

    }

    public Wave createWave(int id) {

        int randomKey = (int)(Math.random() * waves.get(id).size());
        return new Wave(waves.get(id).get(randomKey));
    }
}
