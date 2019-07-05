package com.upa.gun.enemy;

import com.badlogic.gdx.Gdx;
import com.upa.gun.Settings;

import java.util.ListIterator;

public class EnemyFadingState extends EnemyState {
    private Enemy parent;

    public EnemyFadingState(Enemy parent) {
        this.parent = parent;
    }

    @Override
    public void update(float delta) {
        parent.opacity -= Settings.DEATH_FADE_SPEED * delta;

        if (parent.opacity <= 0f) {
            parent.opacity = 0f;
            //Gdx.app.debug("EnemyFadingState", "Switching to dying state");
            parent.setState(new EnemyDyingState());
        }
    }

    public int mobileType() { return 2; }

    @Override
    public void iterationUpdate(ListIterator<Enemy> iterator) {}
}
