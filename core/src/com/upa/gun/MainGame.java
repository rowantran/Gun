package com.upa.gun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends ApplicationAdapter {
    private OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	Texture bullet;
	
	@Override
	public void create () {
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, 1280, 800);

		batch = new SpriteBatch();
		img = new Texture("sprites/normyBullet.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.begin();

		batch.draw(img, 0, 0);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		bullet.dispose();
	}
}
