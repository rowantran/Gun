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

	Bullet bullet;
	
	@Override
	public void create () {
	    Assets.load();

	    setScreen(new MenuScreen(this));
		batch = new SpriteBatch();
<<<<<<< HEAD:core/src/com/upa/gun/GunGame.java
		bullet = new Bullet(10, 10, 64, 64);
=======
		img = new Texture("sprites/normyBullet.png");
>>>>>>> d33637afda4771fae22782b5420e77ec131309ef:core/src/com/upa/gun/MainGame.java
	}

	public void update(float delta) {
	    bullet.update(delta);
    }

	public void render (float delta) {
	     update(delta);

		Gdx.gl.glClearColor(0.2f, 0, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		bullet.render(batch);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
