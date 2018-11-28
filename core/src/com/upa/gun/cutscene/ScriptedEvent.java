package com.upa.gun.cutscene;

import com.upa.gun.Updatable;

interface ScriptedEvent extends Updatable {
    @Override
    void update(float delta);

    void onFinish();
    boolean isFinished();

    /**
     * @return Whether the onFinish() function has been called.
     */
    boolean onFinishCalled();
}