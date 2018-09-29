package com.upa.gun;

public class KillEnemies implements ScriptedEvent {
    private boolean killed = false;
    private boolean finished = false;

    @Override
    public void update(float delta, World world) {
        for (Enemy enemy : world.enemies) {
            if (!(enemy instanceof BossSlime)) {
                enemy.dying = true;
            }
        }
        killed = true;
    }

    @Override
    public void onFinish(World world) {
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
