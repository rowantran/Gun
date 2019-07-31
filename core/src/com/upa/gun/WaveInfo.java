package com.upa.gun;

import java.util.ArrayList;

public class WaveInfo {

    public int id;
    public ArrayList<int[]> wave;
    public int[] delays;

    public WaveInfo(int id, ArrayList<int[]> wave, int[] delays) {
        this.id = id;
        this.wave = wave;
        this.delays = delays;
    }


}
