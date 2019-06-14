package com.upa.gun;

import com.badlogic.gdx.Gdx;
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
                    System.out.println("HIT");
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
            if (World.player.hitboxes.colliding(p.hitboxes)) {
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
        Hitbox footHitbox = World.player.hitboxes.getChild("foot");
        for(CrateTop c : World.currentMap.getCrateTops()) {
            if (c.hitboxes.colliding(footHitbox)) {
                float playerX = footHitbox.getPosition().x;
                float playerY = footHitbox.getPosition().y;
                float crateX = c.hitboxes.getChild("hitbox").getPosition().x;
                float crateY = c.hitboxes.getChild("hitbox").getPosition().y;

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
        //checkPowerupCollect();
        //checkCrateTouch(); currently disabled for hitbox change
    }
}
