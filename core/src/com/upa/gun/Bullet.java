package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends Entity {
    public Bullet(float x, float y) {
        super(x, y, Assets.bulletBasic.getRegionWidth(), Assets.bulletBasic.getRegionHeight());
    }

    public void update(float delta) {
        this.position.x += Settings.BULLET_SPEED * delta;
        this.bounds.x = this.position.x;
    }
}
