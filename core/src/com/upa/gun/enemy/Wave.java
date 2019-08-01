package com.upa.gun.enemy;

import com.badlogic.gdx.Gdx;
import com.upa.gun.Updatable;
import com.upa.gun.World;

public class Wave implements Updatable {

    private int id;
    public WaveInfo info;
    private float timer;

    public Wave(WaveInfo info) {
        this.info = info;
        id = info.id;

        timer = 0f;
        World.waveActive = true;
    }


    public void update(float delta) {

        timer += delta;
        if(info.delays[info.getDelayIndex()] == -1) {
            World.waveActive = false;
            return;
        }

        if(timer >= info.delays[info.getDelayIndex()]) {

            for(int e : info.wave.get(info.getWaveIndex())) {
                if(e == 2) {
                    World.spawner.spawnBossSlime();
                }
                else {
                    World.spawner.spawnSlime(e);
                }
            }
            info.incrementDelayIndex();
            info.incrementWaveIndex();
            timer = 0f;
        }



    }
}
