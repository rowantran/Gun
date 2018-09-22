package com.upa.gun;

import java.util.ArrayList;
import java.util.List;

public abstract class ScriptedEventSequence {
    List<ScriptedEvent> events;
    int currentEvent;
    boolean active;
    boolean cinematic;

    ScriptedEventSequence() {
        events = new ArrayList<ScriptedEvent>();
        currentEvent = 0;
        active = false;
    }

    void update(float delta, GunWorld gunWorld) {
        if (active) {
            ScriptedEvent event = events.get(currentEvent);
            if (!event.onFinishCalled() && event.isFinished()) {
                event.onFinish(gunWorld);
                if (isNextEvent()) {
                    currentEvent++;
                } else {
                    active = false;
                }
            } else {
                event.update(delta, gunWorld);
            }
        }
    }

    void start() {
        active = true;
    }

    private boolean isNextEvent() {
        return currentEvent + 1 < events.size();
    }
}
