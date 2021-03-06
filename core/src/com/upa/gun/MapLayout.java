package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Vector;

public class MapLayout {

    private int id;
    public MapInfo info;

    private ArrayList<Crate> crates;
    private ArrayList<Door> doors;

    public MapLayout(MapInfo info) {
        this.info = info;
        id = info.id;

        createObjects();
    }


    private void createObjects() {
        int[][] all = info.all;

        crates = new ArrayList<Crate>();
        doors = new ArrayList<Door>();


        for(int i = 0; i < all.length; i++) {
            for(int j = 0; j < all[i].length; j++) {

                switch(all[i][j]) {
                    case 0:
                        break;
                    case 1:
                        boolean leftCrate = false;
                        boolean rightCrate = false;
                        boolean botCrate = false;
                        boolean topCrate = false;
                        if(i > 0 && all[i-1][j] == 1) {
                            topCrate = true;
                        }
                        if(i < all.length - 1 && all[i+1][j] == 1) {
                            botCrate = true;
                        }
                        if(j > 0 && all[i][j-1] == 1) {
                            leftCrate = true;
                        }
                        if(j < all[i].length - 1 && all[i][j+1] == 1) {
                            rightCrate = true;
                        }
                        crates.add(new Crate(new Vector2(j * 64 + 32, (all.length - i) * 64 - 28), leftCrate, rightCrate, botCrate, topCrate));
                        crates.get(crates.size()-1).setDisplaySide(true);
                        if(i < all.length-1 && all[i+1][j] == 1) {
                            crates.get(crates.size()-1).setDisplaySide(false);
                        }
                        break;
                    case 2:
                        if(i == 0) {
                            doors.add(new Door(new Vector2(j * 64 + 32, (all.length - i) * 64 - 28), new Vector2(64f, 16f), 1)); //1 = up, 2 = down, 3 = left, 4 = right
                        }
                        else if(i == all.length-1) {
                            doors.add(new Door(new Vector2(j * 64 + 32, (all.length - i) * 64 - 28), new Vector2(64f, 16f), 2));
                        }
                        else if(j == 0) {
                            doors.add(new Door(new Vector2(j * 64 + 32, (all.length - i) * 64 - 28), new Vector2(16f, 64f), 3));
                        }
                        else if(j == all[i].length-1) {
                            doors.add(new Door(new Vector2(j * 64 + 32, (all.length - i) * 64 - 28), new Vector2(16f, 64f), 4));
                        }
                        else {
                            Gdx.app.log("MapLayout", "Found non-edge door");
                        }
                        break;
                    default:
                        Gdx.app.log("MapLayout", "Found invalid item");
                        break;
                }
            }
        }
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    public ArrayList<Crate> getCrates() {
        return crates;
    }
}
