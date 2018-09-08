package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
    public float timeElapsed;
    boolean moving;
    boolean dying;

    Vector2 spawnPoint;

    float opacity;

    public Player(float x, float y) {
        super(x, y, Assets.playerAtlas.findRegion("playerFront-idle").getRegionWidth(),
                Assets.playerAtlas.findRegion("playerFront-idle").getRegionHeight());

        spawnPoint = new Vector2(x, y);

        timeElapsed = 0.0f;
        moving = false;
        dying = false;

        opacity = 1.0f;
    }

    public void update(float delta) {
        moving = false;

        if (dying) {
            Assets.playerIdleSprite.rotate(Settings.DEATH_ROTATE_SPEED * delta);
            if (Assets.playerIdleSprite.getRotation() > 90) {
                dying = false;
                Assets.playerIdleSprite.setRotation(0);
                this.position.x = spawnPoint.x;
                this.position.y = spawnPoint.y;
                this.bounds.x = position.x;
                this.bounds.y = position.y;
            }
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                this.position.x -= Settings.PLAYER_SPEED * delta;
                this.bounds.x = this.position.x;
                moving = true;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                this.position.x += Settings.PLAYER_SPEED * delta;
                this.bounds.x = this.position.x;
                moving = true;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                this.position.y -= Settings.PLAYER_SPEED * delta;
                this.bounds.y = this.position.y;
                moving = true;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                this.position.y += Settings.PLAYER_SPEED * delta;
                this.bounds.y = this.position.y;
                moving = true;
            }
        }

        if (moving) {
            timeElapsed += delta;
        }
    }
}
