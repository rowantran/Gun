package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player {
    public float timeElapsed;
    boolean moving;
    boolean dying;
    boolean fading;

    Vector2 spawnPoint;
    Polygon hitbox;

    float opacity;

    public static int FRONT = 0;
    public static int BACK = 1;
    public static int LEFT = 2;
    public static int RIGHT = 3;

    int rotation;

    Body body;

    public Player(float x, float y, World world) {
        spawnPoint = new Vector2(x, y);

        timeElapsed = 0.0f;
        moving = false;
        dying = false;

        opacity = 1.0f;

        rotation = FRONT;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        CircleShape hitbox = new CircleShape();
        hitbox.setRadius(10f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = hitbox;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);
    }

    public void update(float delta) {
        moving = false;

        if (dying) {
            Assets.playerIdleSprites[rotation].setX(body.getTransform().getPosition().x-
                    Assets.playerIdleSprites[rotation].getWidth()/2);
            Assets.playerIdleSprites[rotation].setY(body.getTransform().getPosition().y-
                    Assets.playerIdleSprites[rotation].getHeight()/2);
            Assets.playerIdleSprites[rotation].setOriginCenter();
            Assets.playerIdleSprites[rotation].rotate(Settings.DEATH_ROTATE_SPEED * delta);
            if (Assets.playerIdleSprites[rotation].getRotation() > 90) {
                dying = false;
                fading = true;
            }
        } if (fading) {
            opacity -= Settings.DEATH_FADE_SPEED * delta;
            if (opacity < 0.0f) {
                opacity = 1.0f;
                Assets.playerIdleSprites[rotation].setRotation(0);
                fading = false;
                this.body.setTransform(spawnPoint, 0);
            }
        }
        if (!dying && !fading) {
            int angle = (int) body.getTransform().getRotation();

            if (Gdx.input.isKeyPressed(Settings.KEY_LEFT)) {
                int currentX = (int) body.getTransform().getPosition().x;
                int currentY = (int) body.getTransform().getPosition().y;
                body.setTransform(currentX-Settings.PLAYER_SPEED*delta, currentY, angle);
                moving = true;
                rotation = LEFT;
            }

            if (Gdx.input.isKeyPressed(Settings.KEY_RIGHT)) {
                int currentX = (int) body.getTransform().getPosition().x;
                int currentY = (int) body.getTransform().getPosition().y;
                body.setTransform(currentX+Settings.PLAYER_SPEED*delta, currentY, angle);
                moving = true;
                rotation = RIGHT;
            }

            if (Gdx.input.isKeyPressed(Settings.KEY_DOWN)) {
                int currentX = (int) body.getTransform().getPosition().x;
                int currentY = (int) body.getTransform().getPosition().y;
                body.setTransform(currentX, currentY-Settings.PLAYER_SPEED*delta, angle);
                moving = true;
                rotation = FRONT;
            }

            if (Gdx.input.isKeyPressed(Settings.KEY_UP)) {
                int currentX = (int) body.getTransform().getPosition().x;
                int currentY = (int) body.getTransform().getPosition().y;
                body.setTransform(currentX, currentY+Settings.PLAYER_SPEED*delta, angle);
                moving = true;
                rotation = BACK;
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
                body.setLinearVelocity(0, 0);
                moving = false;
            }
        }

        if (moving) {
            timeElapsed += delta;
        }
    }
}
