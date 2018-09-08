package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player extends Entity {
    public float timeElapsed;

    public Player(float x, float y) {
        super(x, y, Assets.playerAtlas.findRegion("playerFrontIdle").getRegionWidth(),
                Assets.playerAtlas.findRegion("playerFrontIdle").getRegionHeight());
        timeElapsed = 0.0f;
    }

    public void update(float delta) {
        boolean moving = false;

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

        if (moving) {
            timeElapsed += delta;
        }
    }
}
