package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MapLayout {

    private int id;
    public MapInfo info;
    private ArrayList<CrateTop> crateTops;
    private ArrayList<CrateSide> crateSides;

    public MapLayout(MapInfo info) {
        this.info = info;
        id = info.id;

        createCrates();


    }


    private void createCrates() {
        int[][] tops = info.tops;
        int[][] sides = info.sides;
        crateTops = new ArrayList<CrateTop>();
        crateSides = new ArrayList<CrateSide>();

        for(int i = 0; i < tops.length; i++) {
            for(int j = 0; j < tops[i].length; j++) {
                if(tops[i][j] == 1) {
                    crateTops.add(new CrateTop(new Vector2(j * 64 + 32, (tops.length-i) * 64 - 8)));
                }
            }
        }

        for(int i = 0;  i < sides.length; i++) {
            for(int j = 0; j < sides[i].length; j++) {
                if(sides[i][j] == 1) {
                    crateSides.add(new CrateSide(j * 64 + 32,(sides.length-i) * 64 - 35));
                }
            }
        }

    }

    public ArrayList<CrateTop> getCrateTops() { return crateTops; }
    public ArrayList<CrateSide> getCrateSides() { return crateSides; }

}
