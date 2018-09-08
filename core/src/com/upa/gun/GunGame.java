package com.upa.gun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GunGame extends Game {
	public SpriteBatch batch;
	public Player player;

	@Override
	public void create () {
	    Assets.load();

        batch = new SpriteBatch();
        player = new Player(20, 20);
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
