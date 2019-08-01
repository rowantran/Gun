package com.upa.gun.enemy;

import java.util.ArrayList;

public class WaveInfo {

    public int id;
    public ArrayList<int[]> wave;
    public int[] delays;

    private int currentDelayIndex;
    private int currentWaveIndex;

    public WaveInfo(int id, ArrayList<int[]> wave, int[] delays) {
        this.id = id;
        this.wave = wave;
        this.delays = delays;

        currentDelayIndex = 0;
        currentWaveIndex = 0;

    }

    public int getDelayIndex() {
        return currentDelayIndex;
    }

    public int getWaveIndex() {
        return currentWaveIndex;
    }

    public void incrementDelayIndex() {
        currentDelayIndex++;
    }

    public void incrementWaveIndex() {
        currentWaveIndex++;
    }


}
