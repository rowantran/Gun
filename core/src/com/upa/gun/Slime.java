package com.upa.gun;

public class Slime extends Enemy {


    public Slime() {
        int timeSiinceAttack = 0;
    }

    public void update() {
        move();
        shoot();
    }

    public void draw() {

    }


    public void move() {

    }

    public void shoot() {
        if(timeSinceAttack <= 5) {
            //shooty shooty
        }
    }


}
