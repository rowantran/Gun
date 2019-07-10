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

        boolean oldLeftStop = World.player.leftStop;
        boolean oldRightStop = World.player.rightStop;
        boolean oldBotStop = World.player.botStop;
        boolean oldTopStop = World.player.topStop;

        World.player.resetStops();
        boolean collision = false;

        Hitbox leftFoot = World.player.hitbox.getChild("leftFoot");
        Hitbox rightFoot = World.player.hitbox.getChild("rightFoot");

        for(CrateTop c : World.currentMap.getCrateTops()) {
            if(c.getHitbox().colliding(leftFoot)) { //check for left collision
                collision = true;
                float playerX = leftFoot.getPosition().x;
                float crateX = c.getHitbox().getChild("box").getPosition().x + c.getSize().x;
                if(playerX < crateX) {
                    World.player.leftStop = true;
                }
            }
            if(c.getHitbox().colliding(rightFoot)) { //check for right collision
                collision = true;
                float playerX = rightFoot.getPosition().x + rightFoot.getWidth();
                float crateX = c.getHitbox().getChild("box").getPosition().x;
                if(playerX > crateX) {
                    World.player.rightStop = true;
                }
            }
            if(collision) {
                float playerY = leftFoot.getPosition().y; //both foot hitboxes should have equal height
                float crateY = c.getHitbox().getChild("box").getY();
                if(playerY < crateY + c.getHitbox().getChild("box").getHeight()) { //check for bottom collision
                    World.player.botStop = true;
                }
                if(playerY + leftFoot.getHeight() > crateY) { //check for top collision
                    World.player.topStop = true;
                }
            }
        }
        if(World.player.leftStop && World.player.rightStop) {
            World.player.leftStop = oldLeftStop;
            World.player.rightStop = oldRightStop;
        }
        if(World.player.botStop && World.player.topStop) {
            World.player.botStop = oldBotStop;
            World.player.topStop = oldTopStop;
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
