package com.upa.gun;

import com.badlogic.gdx.Gdx;

public class WaveLayout {

    private int id;
    public WaveInfo info;

    public WaveLayout(WaveInfo info) {
        this.info = info;
        id = info.id;
        printWaveInfo();
    }

    private void printWaveInfo() { //temp for debug

        System.out.println();
        System.out.println();
        Gdx.app.log("WaveLayout", "ID: " + id);
        System.out.println();
        Gdx.app.log("WaveLayout", "Printing waves:");
        for(int i = 0; i < info.wave.size(); i++) {

            for(int j = 0; j < info.wave.get(i).length; j++) {
                System.out.print("" + info.wave.get(i)[j] + " ");
            }
            System.out.println();
        }

        Gdx.app.log("WaveLayout", "Printing delays:");
        for(int i = 0; i < info.delays.length; i++) {
            System.out.print("" + info.delays[i] + " ");
        }

    }


}
