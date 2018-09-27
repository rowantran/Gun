package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputHandler implements Updatable {
    private GunWorld world;

    InputHandler() {
        world = GunWorld.getInstance();
    }

    @Override
    public void update(float delta) {
        Vector2 velocity = new Vector2(0f, 0f);

        if (Gdx.input.isKeyPressed(Settings.KEY_LEFT)) {
            if(!((world.player.getPosition().x - Settings.PLAYER_SPEED * delta)*Settings.PPM < 113)) {
                velocity.x -= Settings.PLAYER_SPEED;
                world.player.moving = true;
            }
            world.player.direction = Direction.LEFT;
        }

        if (Gdx.input.isKeyPressed(Settings.KEY_RIGHT)) {
            if(!((world.player.getPosition().x + Settings.PLAYER_SPEED * delta)*Settings.PPM > 1164)) {
                velocity.x += Settings.PLAYER_SPEED;
                world.player.moving = true;
            }
            world.player.direction = Direction.RIGHT;
        }

        if (Gdx.input.isKeyPressed(Settings.KEY_DOWN)) {
            if(!world.player.botStop){
                velocity.y -= Settings.PLAYER_SPEED;
                world.player.moving = true;
            }
            world.player.direction = Direction.DOWN;
        }

        if (Gdx.input.isKeyPressed(Settings.KEY_UP)) {
            if(!((world.player.getPosition().y + Settings.PLAYER_SPEED * delta)*Settings.PPM > 702)){
                velocity.y += Settings.PLAYER_SPEED;
                world.player.moving = true;
            }
            world.player.direction = Direction.UP;
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            world.player.moving = false;
        }

        velocity.clamp(Settings.PLAYER_SPEED, Settings.PLAYER_SPEED);
        world.player.setVelocity(velocity);

        if (Gdx.input.justTouched()) {
            OrthographicCamera camera = new OrthographicCamera();
            camera.setToOrtho(false, Settings.RESOLUTION.x/Settings.PPM, Settings.RESOLUTION.y/Settings.PPM);

            if(world.player.bulletCooldown <= 0) {

                Vector3 mousePos3 = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),
                        0));
                Vector2 mousePos = new Vector2(mousePos3.x, mousePos3.y);
                Vector2 bulletAngle = mousePos.sub(world.player.getPosition());
                world.bullets.add(new FriendlyBullet(world.player.getPosition().x,
                        world.player.getPosition().y,
                        bulletAngle.angleRad(), world.world));
                world.player.shot.stop();
                world.player.shot.play(.5f);
                world.player.bulletCooldown = 0.4;
            }
        }

    }
}
