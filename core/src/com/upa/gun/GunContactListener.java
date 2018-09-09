package com.upa.gun;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GunContactListener implements ContactListener {
    @Override
    public void endContact(Contact contact) {
        System.out.println("Contact");
    }

    @Override
    public void beginContact(Contact contact) {
        System.out.println("Contact");
        if ((contact.getFixtureA().getBody().getUserData().equals("Bullet") &&
        contact.getFixtureB().getBody().getUserData().equals("Player")) ||
        (contact.getFixtureB().getBody().getUserData().equals("Bullet") &&
        (contact.getFixtureA().getBody().getUserData().equals("Player")))) {
            System.out.println("Collision");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        System.out.println("Contact");
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        System.out.println("Contact");
    }
}
