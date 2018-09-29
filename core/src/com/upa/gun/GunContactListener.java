package com.upa.gun;

import com.badlogic.gdx.physics.box2d.*;

public class GunContactListener implements ContactListener {
    private World gunWorld;

    GunContactListener(World world) {
        this.gunWorld = world;
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void beginContact(Contact contact) {
        Object a = contact.getFixtureA().getBody().getUserData();
        Object b = contact.getFixtureB().getBody().getUserData();

        if (a instanceof Player) {
            //System.out.println("a");
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

        // do for enemies also
        if (a instanceof Player) {
            //System.out.println("player");
            if (b instanceof Crate) {
                gunWorld.player.botStop = true;
                System.out.println("botstop");
            }
        } else if (b instanceof Player) {
            if (a instanceof Crate) {
                gunWorld.player.botStop = true;
            } //must check for direction later
        } else {
            gunWorld.player.topStop = false;
            gunWorld.player.botStop = false;
            gunWorld.player.leftStop = false;
            gunWorld.player.rightStop = false;
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

                if (boss.health == 0) {
                    gunWorld.spawner.bossAlive = false;
                    gunWorld.spawner.slimesKilled += 100;
                }
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
        gunWorld.spawner.slimesKilledSinceLastBoss++;
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
