package com.upa.gun;

interface ScriptedEvent {
    void update(float delta, World world);
    void onFinish(World world);
    boolean isFinished();
    boolean onFinishCalled();
}