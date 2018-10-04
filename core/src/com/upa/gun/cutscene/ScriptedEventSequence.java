package com.upa.gun.cutscene;

import com.upa.gun.Updatable;

import java.util.ArrayList;
import java.util.List;

public abstract class ScriptedEventSequence implements Updatable {
    List<ScriptedEvent> events;
    private int currentEvent;
    private boolean active;
    private boolean cinematic;

    ScriptedEventSequence() {
        events = new ArrayList<ScriptedEvent>();
        currentEvent = 0;
        active = false;
    }

    @Override
    public void update(float delta) {
        if (active) {
            ScriptedEvent event = events.get(currentEvent);
            if (!event.onFinishCalled() && event.isFinished()) {
                event.onFinish();
                if (isNextEvent()) {
                    currentEvent++;
                } else {
                    active = false;
                }
            } else {
                event.update(delta);
            }
        }
    }

    public void start() {
        active = true;
    }

    private boolean isNextEvent() {
        return currentEvent + 1 < events.size();
    }

    public boolean isActive() {
        return active;
    }

    public boolean isCinematic() {
        return cinematic;
    }

    void setCinematic(boolean cinematic) {
        this.cinematic = cinematic;
    }
}
