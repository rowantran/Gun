package com.upa.gun;

import com.badlogic.gdx.physics.box2d.*;

public class GunContactListener implements ContactListener {
    private GunWorld gunWorld;
    private World world;

    GunContactListener(GunWorld gunWorld, World world) {
        this.gunWorld = gunWorld;
        this.world = world;
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void beginContact(Contact contact) {
        Object a = contact.getFixtureA().getBody().getUserData();
        Object b = contact.getFixtureB().getBody().getUserData();

        if (a instanceof Player) {
            killIfEnemy(b);
        } else if (b instanceof Player) {
            killIfEnemy(a);

        }
        
    }

    private void killIfEnemy(Object o) {
        if (isEnemy(o)) {
            gunWorld.player.dying = true;
        }
    }

    private boolean isEnemy(Object o) {
        return o instanceof EnemyBullet || o instanceof Slime;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
