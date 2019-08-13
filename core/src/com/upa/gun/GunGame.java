package com.upa.gun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GunGame extends Game {
	SpriteBatch batch;

	public World world;
	private Music music;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Settings.LOG_LEVEL);

	    Assets.load();

        batch = new SpriteBatch();

        world = World.getInstance();
        world.setGunGame(this);

        if(!Settings.MUTE) {
			music = Gdx.audio.newMusic(Gdx.files.internal("sfx/music.mp3"));
			music.setLooping(true);
			music.play();
		}

        setScreen(new MenuScreen(this));
        
	}

	@Override
	public void dispose() {
	    super.dispose();
		batch.dispose();
	}
}
