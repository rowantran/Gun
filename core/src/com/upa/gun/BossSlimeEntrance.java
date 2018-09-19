package com.upa.gun;

public class BossSlimeEntrance extends ScriptedEventSequence {
    BossSlimeEntrance(BossSlime slime) {
        events.add(new BossSlimeFall(slime));
    }
}
