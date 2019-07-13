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

        Hitbox leftFoot = World.player.crateCheckHitbox.getChild("leftFoot");
        Hitbox rightFoot = World.player.crateCheckHitbox.getChild("rightFoot");
        Hitbox vertFoot = World.player.crateCheckHitbox.getChild("vertFoot");

        for(Crate c : World.currentMap.getCrates()) {

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
        checkPlayerHit();
        checkEnemiesHit();
        checkCrateTouch();
        checkBulletCrash();
        //checkPowerupCollect();
    }
}
