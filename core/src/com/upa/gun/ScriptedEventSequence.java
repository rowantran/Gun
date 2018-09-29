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

    void update(float delta, World world) {
        if (active) {
            ScriptedEvent event = events.get(currentEvent);
            if (!event.onFinishCalled() && event.isFinished()) {
                event.onFinish(world);
                if (isNextEvent()) {
                    currentEvent++;
                } else {
                    active = false;
                }
            } else {
                event.update(delta, world);
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
