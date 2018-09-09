package com.upa.gun;

import com.badlogic.gdx.physics.box2d.*;

import java.util.List;

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

        if (a instanceof Player && b instanceof Bullet) {
            gunWorld.player.dying = true;
            ((Bullet) b).markedForDeletion = true;
        } else if (b instanceof Player && a instanceof Bullet) {
            gunWorld.player.dying = true;
            ((Bullet) a).markedForDeletion = true;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
