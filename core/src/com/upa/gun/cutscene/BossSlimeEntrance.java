package com.upa.gun.cutscene;

import com.upa.gun.enemy.Enemy;

public class BossSlimeEntrance extends ScriptedEventSequence {
    public BossSlimeEntrance(Enemy slime) {
        setCinematic(true);
        events.add(new BossSlimeFall(slime));
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }
}
