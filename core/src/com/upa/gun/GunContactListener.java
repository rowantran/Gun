package com.upa.gun;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GunContactListener implements ContactListener {
    private Player player;

    GunContactListener(Player player) {
        this.player = player;
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void beginContact(Contact contact) {
        if ((contact.getFixtureA().getBody().getUserData() instanceof Player &&
                contact.getFixtureB().getBody().getUserData() instanceof Bullet) ||
                (contact.getFixtureA().getBody().getUserData() instanceof Bullet) &&
                        (contact.getFixtureB().getBody().getUserData() instanceof Player)) {
            player.dying = true;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
