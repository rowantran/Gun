package com.upa.gun;

class BossSlimeEntrance extends ScriptedEventSequence {
    BossSlimeEntrance(BossSlime slime) {
        cinematic = true;
        events.add(new BossSlimeFall(slime));
    }
}
