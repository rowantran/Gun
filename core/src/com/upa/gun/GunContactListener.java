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
            dieIfEnemy(b);
        } else if (b instanceof Player) {
            dieIfEnemy(a);
        }

        if (a instanceof FriendlyBullet) {
            if (destroyIfHostile(b)) {
                markBulletForDeletion(a);
            }
        } else if (b instanceof FriendlyBullet) {
            if (destroyIfHostile(a)) {
                markBulletForDeletion(b);
            }
        }
    }

    private void dieIfEnemy(Object o) {
        if (isHostile(o)) {
            gunWorld.player.dying = true;
        }
    }

    private boolean destroyIfHostile(Object o) {
        boolean hostile = o instanceof Enemy;
        if (hostile) {
            markEnemyForDeletion(o);
        }

        return hostile;
    }

    private boolean isHostile(Object o) {
        return o instanceof Enemy || o instanceof EnemyBullet;
    }

    private void markBulletForDeletion(Object o) {
        ((Bullet) o).markedForDeletion = true;
    }

    private void markEnemyForDeletion(Object o) {
        // Tushar's bug fix
        ((Enemy) o).dying = true;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
