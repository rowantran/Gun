package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MapLayout {

    private int id;
    public MapInfo info;

    private ArrayList<Crate> crates;


    public MapLayout(MapInfo info) {
        this.info = info;
        id = info.id;

        createCrates();
    }


    private void createCrates() {
        int[][] tops = info.tops;
        int[][] sides = info.sides;

        crates = new ArrayList<Crate>();

        for(int i = 0; i < tops.length; i++) {
            for(int j = 0; j < tops[i].length; j++) {
                if(tops[i][j] == 1 || sides[i][j] == 1) {
                    crates.add(new Crate(new Vector2(j * 64 + 32, (tops.length - i) * 64-35)));
                    if(sides[i][j] == 1) {
                        crates.get(crates.size()-1).displaySide();
                    }
                    else {
                        crates.get(crates.size()-1).hideSide();
                    }
                }
            }
        }
    }


    public ArrayList<Crate> getCrates() {
        return crates;
    }
}
