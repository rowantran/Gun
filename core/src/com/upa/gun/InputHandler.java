package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputHandler implements Updatable {
    private Vector2 checkKeys(float delta) {
        Vector2 velocity = new Vector2(0f, 0f);

        if (Gdx.input.isKeyPressed(Settings.KEY_LEFT)) {
            if(!((World.player.getPosition().x - Settings.playerSpeed * delta) < 113)) {
                velocity.x -= Settings.playerSpeed;
                World.player.state = PlayerState.moving;
            }
        }

        if (Gdx.input.isKeyPressed(Settings.KEY_RIGHT)) {
            if(!((World.player.getPosition().x + Settings.playerSpeed * delta) > 1164)) {
                velocity.x += Settings.playerSpeed;
                World.player.state = PlayerState.moving;
            }
        }

        if (Gdx.input.isKeyPressed(Settings.KEY_DOWN)) {
            if(!World.player.botStop){
                velocity.y -= Settings.playerSpeed;
                World.player.state = PlayerState.moving;
            }
        }

        if (Gdx.input.isKeyPressed(Settings.KEY_UP)) {
            if (!((World.player.getPosition().y + Settings.playerSpeed * delta) > 702)) {
                velocity.y += Settings.playerSpeed;
                World.player.state = PlayerState.moving;
            }
        }

        if (Gdx.input.isKeyJustPressed(Settings.KEY_ROLL)) {
            //System.out.println(World.player.state.timeElapsed);
            if(World.player.timeSinceRoll >= Settings.ROLL_DELAY) {
                World.player.timeSinceRoll = 0f;
                World.player.state = new PlayerRollingState(World.player.direction);
            }
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            World.player.state = PlayerState.idle;
        }

        return velocity.setLength(Settings.playerSpeed);
    }

    /**
     * Handle all player input
     * @param delta Frame time for current tick
     */
    @Override
    public void update(float delta) {
        Vector2 velocity = new Vector2(0f, 0f);
        if (World.player.state.controllable) {
            velocity = checkKeys(delta);
            if (velocity.x != 0f || velocity.y != 0f) {
                World.player.direction = Direction.getDirection(velocity);
            }
        }

        World.player.setVelocity(velocity);

        if (Gdx.input.justTouched()) {
            OrthographicCamera camera = new OrthographicCamera();
            camera.setToOrtho(false, Settings.RESOLUTION.x, Settings.RESOLUTION.y);

            if(World.player.bulletCooldown <= 0) {

                Vector3 mousePos3 = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),
                        0));
                Vector2 mousePos = new Vector2(mousePos3.x, mousePos3.y);
                Vector2 bulletAngle = mousePos.sub(World.player.getPosition());
                World.playerBullets.add(new FriendlyBullet(World.player.getPosition().x,
                        World.player.getPosition().y,
                        bulletAngle.angleRad()));
                World.player.shot.stop();
                World.player.shot.play(.5f);
                World.player.bulletCooldown = Settings.playerBulletCooldown;
            }
        }
    }

}
