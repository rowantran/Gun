package com.upa.gun;

public class KillEnemies implements ScriptedEvent {
    private boolean killed = false;
    private boolean finished = false;

    @Override
    public void update(float delta, GunWorld gunWorld) {
        for (Enemy enemy : gunWorld.enemies) {
            if (!(enemy instanceof BossSlime)) {
                enemy.dying = true;
            }
        }
        killed = true;
    }

    @Override
    public void onFinish(GunWorld gunWorld) {
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
