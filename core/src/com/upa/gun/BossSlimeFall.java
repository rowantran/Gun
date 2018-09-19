package com.upa.gun;

public class BossSlimeFall implements ScriptedEvent {
    static float FALL_SPEED = 1.2f;
    static float TIME_AFTER_FALL = 0.8f;

    private BossSlime slime;
    private boolean finished;

    private float timeElapsed;

    BossSlimeFall(BossSlime slime) {
        this.slime = slime;
        finished = false;
        timeElapsed = 0f;
    }

    @Override
    public void update(float delta, GunWorld gunWorld) {
        float currentFallSpeed = slime.body.getLinearVelocity().y;
        slime.body.setLinearVelocity(0, currentFallSpeed - FALL_SPEED*delta);

        if (slime.body.getPosition().y <= (Settings.RESOLUTION.x * 0.4f / Settings.PPM)) {
            slime.body.setLinearVelocity(0, 0);
            timeElapsed += delta;
        }
    }

    @Override
    public void onFinish(float delta, GunWorld gunWorld) {
        gunWorld.enemies.add(slime);
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return slime.body.getPosition().y <=
                (Settings.RESOLUTION.x * 0.4f / Settings.PPM) && timeElapsed > TIME_AFTER_FALL;
    }

    @Override
    public boolean onFinishCalled() {
        return finished;
    }
}
