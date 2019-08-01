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

        printWaveInfo();

        timer = 0f;
        World.waveActive = true;

    }


    private void printWaveInfo() { //temp for debug


        System.out.println();
        System.out.println();
        Gdx.app.log("Wave", "ID: " + id);
        System.out.println();
        Gdx.app.log("Wave", "Printing waves:");
        for(int i = 0; i < info.wave.size(); i++) {

            for(int j = 0; j < info.wave.get(i).length; j++) {
                System.out.print("" + info.wave.get(i)[j] + " ");
            }
            System.out.println();
        }

        Gdx.app.log("Wave", "Printing delays:");
        for(int i = 0; i < info.delays.length; i++) {
            System.out.print("" + info.delays[i] + " ");
        }
    }

    public void update(float delta) {

        timer += delta;

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

        if(info.delays[info.getDelayIndex()] == 0) {
            World.waveActive = false;
            World.doorsOpen = true; //need to kill all slimes first
            Gdx.app.log("Wave", "Finished spawning waves");
        }

    }
}
