package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.upa.gun.enemy.Enemy;
import com.upa.gun.enemy.Powerup;

public class CollisionChecker implements Updatable {
    private void checkPlayerHit() {
        for (Bullet b : World.enemyBullets) {
            if (b.hitbox.colliding(World.player.hitbox)) {
                World.player.hurt(1); //will leave dying state when other condition occurs - needs fix
            }
        }
    }

    private void checkEnemiesHit() {
        for (Bullet b : World.playerBullets) {
            for (Enemy e: World.enemies) {
                if (b.hitbox.colliding(e.getHitbox())) {
                    Gdx.app.debug("CollisionChecker", "Enemy hit by bullet");
                    e.damage(Settings.playerDamage);
                    System.out.println("Enemy has " + e.getHealth() + " health left");
                    b.markedForDeletion = true;
                }
            }
        }
    }

    private void checkPowerupCollect() {
        for(Powerup p : World.powerups) {
            if (p.getHitbox().colliding(World.player.hitbox)) {
                p.markForDeletion(World.player);
            }
        }
    }

    /**
     * @param delta
     * Checks if the player is touching a crate. May be adjusted after new hitboxes are implemented
     */
    /*
    private void checkCrateTouch() {
        World.player.resetStops();
        for(CrateTop c : World.currentMap.getCrateTops()) {
            if (c.getHitbox().colliding(World.player.footHixbox)) {
                float playerX = World.player.footHixbox.getX();
                float playerY = World.player.footHixbox.getY();
                float crateX = c.getHitbox().getX();
                float crateY = c.getHitbox().getY();

                if(playerX < crateX) { World.player.rightStop = true; } //player on left
                if(crateX < playerX) { World.player.leftStop = true; } //player on right
                if(playerY < crateY) { World.player.topStop = true; } //player on bottom
                if(crateY < playerY) { World.player.botStop = true; } //player on top

            }
        }
    }
    */

    @Override
    public void update(float delta) {
        checkPlayerHit();
        checkEnemiesHit();
        checkPowerupCollect();
        //checkCrateTouch(); currently disabled for hitbox change
    }
}
