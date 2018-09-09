package com.upa.gun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GunGame extends Game {
	public SpriteBatch batch;
	public Player player;
	public World world;
	private float elapsed;

	@Override
	public void create () {
	    Assets.load();

        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), true);
        player = new Player(20, 20, world);
        elapsed = 0.0f;
        setScreen(new MenuScreen(this));
	}

	public void render() {
	    super.render();
    }

    public void doPhysicsStep(float delta) {
	    float frameTime = Math.min(delta, 0.25f);
	    elapsed += frameTime;
	    while (elapsed >= Settings.STEP_TIME) {
	        world.step(Settings.STEP_TIME, 6, 2);
	        elapsed -= Settings.STEP_TIME;
        }
    }

	@Override
	public void dispose () {
		batch.dispose();
	}
}
