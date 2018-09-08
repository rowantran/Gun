package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player extends Entity {

    public Player(float x, float y) {
        super(x, y, Assets.bulletBasic.getRegionWidth(), Assets.bulletBasic.getRegionHeight());
    }

    public void update(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.position.x -= Settings.PLAYER_SPEED * delta;
            this.bounds.x = this.position.x;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.position.x += Settings.PLAYER_SPEED * delta;
            this.bounds.x = this.position.x;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.position.y -= Settings.PLAYER_SPEED * delta;
            this.bounds.y = this.position.y;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.position.y += Settings.PLAYER_SPEED * delta;
            this.bounds.y = this.position.y;
        }

    }
}
