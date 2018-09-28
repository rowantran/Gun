package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Bullet extends Entity {
    double angle;

    boolean markedForDeletion;

    private Hitbox hitbox;

    Bullet(float x, float y, double angle, TextureRegion texture) {
        super(x, y);
        this.angle = angle;

        hitbox = new RectangularHitbox(x + texture.getRegionWidth()/2, y + texture.getRegionHeight() / 2,
                texture.getRegionWidth(), texture.getRegionHeight());

        markedForDeletion = false;
    }

    public void update(float delta) {

        int bulletX = (int)body.getTransform().getPosition().x;
        int bulletY = (int)body.getTransform().getPosition().y;

        if(bulletX < 0 || bulletX > 1280f/Settings.PPM || bulletY < 0 || bulletY > 800f/Settings.PPM) {
            markedForDeletion = true;
        }

    }
}