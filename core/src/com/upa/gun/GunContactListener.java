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

        if (a instanceof FriendlyBullet) {
            if (b instanceof Enemy) {
                Enemy enemy = (Enemy) b;
                enemy.dying = true;

                markForDeletion(a);
            }
        } else if (b instanceof FriendlyBullet) {
            if (a instanceof Enemy) {
                Enemy enemy = (Enemy) a;
                enemy.dying = true;

                markForDeletion(b);
            }
        }
    }

    private void killIfEnemy(Object o) {
        if (isEnemy(o)) {
            gunWorld.player.dying = true;
        }
    }

    private boolean isEnemy(Object o) {
        if (o instanceof EnemyBullet) {
            markForDeletion(o);
            return true;
        }

        return o instanceof Enemy;
    }

    private void markForDeletion(Object o) {
        ((Bullet) o).markedForDeletion = true;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
