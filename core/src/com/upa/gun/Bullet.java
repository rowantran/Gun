package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet extends Sprite {
    Bullet(float x, float y) {
        super(Assets.bulletBasic);
        setX(x);
        setY(y);
    }

    public void update(float delta) {
        setX(getX() + Settings.BULLET_SPEED * delta);
    }
}
