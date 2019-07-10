package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.upa.gun.enemy.Enemy;
import com.upa.gun.enemy.Powerup;

public class CollisionChecker implements Updatable {
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

    private void checkCrateTouch() {


        World.player.resetStops();

        Hitbox leftFoot = World.player.hitbox.getChild("leftFoot");
        Hitbox rightFoot = World.player.hitbox.getChild("rightFoot");
        Hitbox vertFoot = World.player.hitbox.getChild("vertFoot");

        for(CrateTop c : World.currentMap.getCrateTops()) {

            if(c.getHitbox().getChild("rightEdge").colliding(leftFoot)) {
                World.player.leftStop = true;
            }
            if(c.getHitbox().getChild("leftEdge").colliding(rightFoot)) {
                World.player.rightStop = true;
            }
            if(c.getHitbox().getChild("topEdge").colliding(vertFoot)) {
                World.player.botStop = true;
            }
            if(c.getHitbox().getChild("botEdge").colliding(vertFoot)) {
                World.player.topStop = true;
            }
        }
    }

    @Override
    public void update(float delta) {
        checkPlayerHit();
        checkEnemiesHit();
        checkCrateTouch();
        //checkPowerupCollect();
    }
}
