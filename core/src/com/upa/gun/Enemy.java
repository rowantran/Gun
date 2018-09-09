package com.upa.gun;

public abstract class Enemy {

    public abstract void update();
    public abstract void draw();

    public void render() {
        update();
        draw();
    }



}
