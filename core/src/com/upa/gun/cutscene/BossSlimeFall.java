package com.upa.gun.cutscene;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.Settings;
import com.upa.gun.enemy.Enemy;

public class BossSlimeFall implements ScriptedEvent {
    private static float FALL_SPEED = 3.0f;
    private static float TIME_AFTER_FALL = 0.8f;

    private Enemy slime;
    private float velocity;
    private boolean finished;

    private float timeElapsed;

    private KillEnemies killEnemies;

    BossSlimeFall(Enemy slime) {
        this.slime = slime;
        velocity = 0f;
        finished = false;
        timeElapsed = 0f;

        killEnemies = new KillEnemies(slime);
    }

    @Override
    public void update(float delta) {
        velocity -= FALL_SPEED * delta;
        timeElapsed += delta;

        Vector2 currentPos = slime.getPosition();
        currentPos.y += velocity;
        slime.setPosition(currentPos);

        if (isFinished()) {
            killEnemies.update(delta);

            slime.setVelocity(0, 0);
        }
    }

    @Override
    public void onFinish() {
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return slime.getPosition().y <=
                (Settings.RESOLUTION.y * 0.4f) && timeElapsed > TIME_AFTER_FALL;
    }

    @Override
    public boolean onFinishCalled() {
        return finished;
    }
}
