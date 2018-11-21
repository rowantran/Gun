package com.upa.gun.cutscene;

import com.upa.gun.enemy.Enemy;
import com.upa.gun.World;
import com.upa.gun.enemy.EnemyFadingState;

public class KillEnemies implements ScriptedEvent {
    private boolean killed = false;
    private boolean finished = false;

    @Override
    public void update(float delta) {
        for (Enemy enemy : World.enemies) {
            enemy.setState(new EnemyFadingState(enemy));
        }

        killed = true;
    }

    @Override
    public void onFinish() {
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return killed;
    }

    @Override
    public boolean onFinishCalled() {
        return finished;
    }
}
