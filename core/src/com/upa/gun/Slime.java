package com.upa.gun;

public class Slime extends Enemy {

    int timeSinceAttack;

    public Slime() {
        int timeSinceAttack = 0;
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
