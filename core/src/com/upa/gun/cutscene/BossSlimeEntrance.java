package com.upa.gun.cutscene;

import com.upa.gun.BossSlime;

public class BossSlimeEntrance extends ScriptedEventSequence {
    public BossSlimeEntrance(BossSlime slime) {
        setCinematic(true);
        events.add(new BossSlimeFall(slime));
    }
}
