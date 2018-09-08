package com.upa.gun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GunGame extends Game {
	public SpriteBatch batch;

	@Override
	public void create () {
	    Assets.load();

        batch = new SpriteBatch();
        setScreen(new MenuScreen(this));
	}

	public void render() {
	    super.render();
    }

	@Override
	public void dispose () {
		batch.dispose();
	}
}
