package com.upa.gun;

public class Bullet extends Entity {
    public Bullet(float x, float y) {
        super(x, y, Assets.playerBasic.getRegionWidth(), Assets.playerBasic.getRegionHeight());
    }

    public void update(float delta) {
        this.position.x += Settings.BULLET_SPEED * delta;   
        this.bounds.x = this.position.x;
    }
}
