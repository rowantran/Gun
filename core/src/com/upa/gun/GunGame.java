package com.upa.gun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GunGame extends Game {
	public SpriteBatch batch;
	public World world;
	private Music music;

	@Override
	public void create () {
	    Assets.load();

	    music = Gdx.audio.newMusic(Gdx.files.internal("sfx/music.mp3"));
        music.setLooping(true);
        music.play();
        batch = new SpriteBatch();
        world = World.getInstance();
        world.setGunGame(this);

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
