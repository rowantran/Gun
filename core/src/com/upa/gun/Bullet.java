package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Bullet {
    double angle;
    Body body;

    Sprite bulletSprite;

    Bullet(float x, float y, double angle, World world) {
        this.angle = angle;
        bulletSprite = new Sprite(Assets.bulletBasic);
        bulletSprite.setRotation((float) (angle * 180 / Math.PI));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);
        body.setUserData(this);
        body.setTransform(x, y, (float) angle);

        PolygonShape bulletBox = new PolygonShape();
        bulletBox.setAsBox(bulletSprite.getWidth()/2, bulletSprite.getHeight()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bulletBox;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.isSensor = true;

        body.createFixture(bulletBox, 0.0f);
    }

    public void update(float delta) {
        double vx, vy;
        vx = Math.cos(angle) * Settings.BULLET_SPEED;
        vy = Math.sin(angle) * Settings.BULLET_SPEED;
        if (Settings.SLOW_BULLETS) {
            vx *= 0.1f;
            vy *= 0.1f;
        }

        body.setLinearVelocity((float) vx, (float) vy);
    }
}
