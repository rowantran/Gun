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
            if (Settings.PLAYER_DEATH) {
                if (o instanceof BossBullet) {
                    gunWorld.player.hurt(2);
                } else {
                    gunWorld.player.hurt(1);
                }
            }
        }
    }

    private boolean destroyIfHostile(Object o) {
        boolean hostile = o instanceof Enemy;
        if (hostile) {
            if (o instanceof BossSlime) {
                BossSlime boss = (BossSlime) o;
                boss.hurt = true;
                boss.health--;
            } else {
                markEnemyForDeletion(o);
            }
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
        gunWorld.spawner.slimesKilled++;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
