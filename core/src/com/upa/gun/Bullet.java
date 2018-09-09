package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Bullet {
    double angle;
    Body body;

    Sprite bulletSprite;

    Bullet(float x, float y, double angle, World world) {
        this.angle = angle;
        bulletSprite = new Sprite(Assets.bulletBasic);
        bulletSprite.setOrigin(0, 0);
        bulletSprite.setRotation((float) (angle * 180 / Math.PI));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);
        body.setUserData("Bullet");

        PolygonShape bulletBox = new PolygonShape();
        bulletBox.setAsBox(bulletSprite.getWidth(), bulletSprite.getHeight());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bulletBox;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.isSensor = true;

        body.createFixture(bulletBox, 0.0f);
    }

    public void update(float delta) {
        double vx = Math.cos(angle) * Settings.BULLET_SPEED;
        double vy = Math.sin(angle) * Settings.BULLET_SPEED;

        body.setLinearVelocity((float) vx, (float) vy);
    }
}
