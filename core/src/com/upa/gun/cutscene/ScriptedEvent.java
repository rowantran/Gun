package com.upa.gun.cutscene;

import com.upa.gun.Updatable;

interface ScriptedEvent extends Updatable {
    @Override
    void update(float delta);

    void onFinish();
    boolean isFinished();
    boolean onFinishCalled();
}