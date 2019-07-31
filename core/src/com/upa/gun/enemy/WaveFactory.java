package com.upa.gun.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import com.upa.gun.WaveInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WaveFactory {

    public Map<Integer, WaveInfo> waves;

    public WaveFactory(String path) {

        waves = new HashMap<Integer, WaveInfo>();

        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(Gdx.files.internal(path)).get("waves");

        for(JsonValue difficulty : root) {

            int id = difficulty.getInt("id");

            JsonValue enemyVals = difficulty.get("enemies");
            JsonValue delayVals = difficulty.get("delays");


            for(int i = 0; i < enemyVals.size; i++) {
                ArrayList<int[]> wave = new ArrayList<int[]>();

                for(int j = 0; j < enemyVals.get(i).size; j++) {

                    int[] temp = enemyVals.get(i).get(j).asIntArray();
                    wave.add(temp);

                }

                int[] delays = delayVals.get(i).asIntArray();

                WaveInfo info = new WaveInfo(id, wave, delays);
                waves.put(id, info); //WRONG -- DO NOT HAVE UNIQUE IDS

            }




        }

    }


}
