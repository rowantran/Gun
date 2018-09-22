package com.upa.gun;

interface ScriptedEvent {
    void update(float delta, GunWorld gunWorld);
    void onFinish(GunWorld gunWorld);
    boolean isFinished();
    boolean onFinishCalled();
}