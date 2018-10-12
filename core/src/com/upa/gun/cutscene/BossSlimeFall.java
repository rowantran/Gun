package com.upa.gun.cutscene;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.BossSlime;
import com.upa.gun.Settings;
import com.upa.gun.World;

public class BossSlimeFall implements ScriptedEvent {
    private static float FALL_SPEED = 1.4f;
    private static float TIME_AFTER_FALL = 0.8f;

    private BossSlime slime;
    private float velocity;
    private boolean finished;

    private float timeElapsed;

    private KillEnemies killEnemies;

    BossSlimeFall(BossSlime slime) {
        this.slime = slime;
        velocity = 0f;
        finished = false;
        timeElapsed = 0f;

        killEnemies = new KillEnemies();
    }

    @Override
    public void update(float delta) {
        velocity -= FALL_SPEED * delta;

        Vector2 currentPos = slime.getPosition();
        currentPos.y += velocity;
        slime.setPosition(currentPos);

        if (slime.getPosition().y <= (Settings.RESOLUTION.x * 0.4f)) {
            killEnemies.update(delta);

            slime.setVelocity(0, 0);
            timeElapsed += delta;
        }
    }

    @Override
    public void onFinish() {
        World.enemies.add(slime);
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return slime.getPosition().y <=
                (Settings.RESOLUTION.x * 0.4f) && timeElapsed > TIME_AFTER_FALL;
    }

    @Override
    public boolean onFinishCalled() {
        return finished;
    }
}
