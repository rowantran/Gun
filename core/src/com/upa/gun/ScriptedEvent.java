package com.upa.gun;

interface ScriptedEvent {
    void update(float delta, GunWorld gunWorld);
    void onFinish(float delta, GunWorld gunWorld);
    boolean isFinished();
    boolean onFinishCalled();
}