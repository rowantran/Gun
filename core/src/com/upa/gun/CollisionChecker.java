package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.upa.gun.enemy.Enemy;
import com.upa.gun.enemy.Powerup;

public class CollisionChecker implements Updatable {

    private void checkDoorEnter() {
        World.roomChange = 0;
        World.resetTimer();
        if(World.doorsOn) {
            for (Door d : World.currentMap.getDoors()) {
                if (World.player.crateCheckHitbox.colliding(d.getHitbox())) {
                    World.roomChange = d.getDirection();

                    World.moveAllEntities();

                    switch (d.getDirection()) {
                        case 1:
                            World.currentMap = World.fullMap[--World.mapY][World.mapX];
                            World.adjustNewMap();
                            break;
                        case 2:
                            World.currentMap = World.fullMap[++World.mapY][World.mapX];
                            World.adjustNewMap();
                            break;
                        case 3:
                            World.currentMap = World.fullMap[World.mapY][--World.mapX];
                            World.adjustNewMap();
                            break;
                        case 4:
                            World.currentMap = World.fullMap[World.mapY][++World.mapX];
                            World.adjustNewMap();
                            break;
                        default:
                            Gdx.app.log("CollisionChecker", "Found invalid door direction");
                            break;
                    }
                    break;
                }
            }
        }
    }

    private void checkPlayerHit() {
        for (Bullet b : World.enemyBullets) {
            if (b.getHitbox().colliding(World.player.hitbox.getChild("center"))) {
                World.player.hurt(1); //will leave dying state when other condition occurs - needs fix
            }
        }
    }

    private void checkEnemiesHit() {
        for (Bullet b : World.playerBullets) {
            for (Enemy e: World.enemies) {
                if (b.getHitbox().colliding(e.getHitbox())) {
                    //Gdx.app.debug("CollisionChecker", "Enemy hit by bullet");
                    e.damage(Settings.playerDamage);
                    b.markedForDeletion = true;
                }
            }
        }
    }

    private void checkPowerupCollect() {
        for(Powerup p : World.powerups) {
            if (World.player.hitboxes.colliding(p.hitboxes)) {
                p.markForDeletion(World.player);
            }
        }
    }

    private void checkBulletCrash() {
        for(Crate c : World.currentMap.getCrates()) {
            for (Bullet b : World.playerBullets) {
                if(c.getHitbox().colliding(b.getHitbox())) {
                    b.markedForDeletion = true;
                }
            }
            for(Bullet b : World.enemyBullets) {
                if(c.getHitbox().colliding(b.getHitbox())) {
                    b.markedForDeletion = true;
                }
            }
        }
    }

    @Override
    public void update(float delta) {
        checkDoorEnter();
        checkPlayerHit();
        checkEnemiesHit();
        checkBulletCrash();
        //checkPowerupCollect();
    }
}
