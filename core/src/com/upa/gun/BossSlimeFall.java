package com.upa.gun;

public class BossSlimeFall implements ScriptedEvent {
    static float FALL_SPEED = 1.2f;

    private BossSlime slime;
    private boolean finished;

    BossSlimeFall(BossSlime slime) {
        this.slime = slime;
        finished = false;
    }

    @Override
    public void update(float delta, GunWorld gunWorld) {
        float currentFallSpeed = slime.body.getLinearVelocity().y;
        slime.body.setLinearVelocity(0, currentFallSpeed - FALL_SPEED*delta);
    }

    @Override
    public void onFinish(float delta, GunWorld gunWorld) {
        slime.body.setLinearVelocity(0, 0);
        gunWorld.enemies.add(slime);
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return slime.body.getPosition().y - (Settings.RESOLUTION.x/Settings.PPM*0.1f) <=
                (Settings.RESOLUTION.x / 2f / Settings.PPM);
    }

    @Override
    public boolean onFinishCalled() {
        return finished;
    }
}
