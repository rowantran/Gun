package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.upa.gun.enemy.Enemy;
import com.upa.gun.enemy.Powerup;

public class CollisionChecker implements Updatable {

    private void checkDoorEnter() {
        World.roomChange = 0;
        World.resetTimer();
        for (Door d : World.currentMap.getDoors()) {
            if (World.player.crateCheckHitbox.colliding(d.getHitbox().getChild("open"))) {
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
            if (World.player.hitbox.colliding(p.hitbox)) {
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
        if(!World.doorsOpen) {
            for(Door d : World.currentMap.getDoors()) {
                for(Bullet b : World.playerBullets) {
                    if(b.getHitbox().colliding(d.getHitbox().getChild("closed"))) {
                        b.markedForDeletion = true;
                    }
                }
                for(Bullet b : World.enemyBullets) {
                    if(b.getHitbox().colliding(d.getHitbox().getChild("closed"))) {
                        b.markedForDeletion = true;
                    }
                }
            }
        }
    }

    public void checkFutureCollisions(float delta, Entity e, Hitboxes hitboxes) {

        hitboxes.setPosition(new Vector2(e.position.x + e.velocity.x * delta, e.position.y + e.velocity.y * delta));

        Hitbox leftFoot = hitboxes.getChild("leftFoot");
        Hitbox rightFoot = hitboxes.getChild("rightFoot");
        Hitbox topFoot = hitboxes.getChild("topFoot");
        Hitbox botFoot = hitboxes.getChild("botFoot");

        for(Crate c : World.currentMap.getCrates()) {

            Hitbox rightEdge = c.getHitbox().getChild("rightEdge");
            Hitbox leftEdge = c.getHitbox().getChild("leftEdge");
            Hitbox topEdge = c.getHitbox().getChild("topEdge");
            Hitbox botEdge = c.getHitbox().getChild("botEdge");

            if(rightEdge.isActive() && rightEdge.colliding(leftFoot) && e.velocity.x < 0 && leftFoot.getX() < rightEdge.getX() + rightEdge.getWidth()) {
                e.setVelocity(((rightEdge.getX() + rightEdge.getWidth()) - (e.position.x)) / delta, e.velocity.y);
            }
            else if(leftEdge.isActive() && leftEdge.colliding(rightFoot) && e.velocity.x > 0 && rightFoot.getX() + rightFoot.getWidth() > leftEdge.getX()) {
                e.setVelocity(((leftEdge.getX()) - (e.position.x + e.size.x)) / delta, e.velocity.y);
            }
            if(topEdge.isActive() && topEdge.colliding(botFoot) && e.velocity.y < 0 && botFoot.getY() < topEdge.getY() + topEdge.getHeight()) {
                e.setVelocity(e.velocity.x, ((topEdge.getY() + topEdge.getHeight()) - (e.position.y)) / delta);
            }
            else if(botEdge.isActive() && botEdge.colliding(topFoot) && e.velocity.y > 0 && topFoot.getY() + topFoot.getHeight() > botEdge.getY()) {
                e.setVelocity(e.velocity.x, ((botEdge.getY()) - (e.position.y + botFoot.getHeight() + topFoot.getHeight())) / delta);
            }
        }

        if(!World.doorsOpen) {

            for(Door d : World.currentMap.getDoors()) {

                Hitbox edge = d.getHitbox().getChild("closed");

                switch(d.getDirection()) {
                    case 1:
                        if(edge.colliding(topFoot) && e.velocity.y > 0 && topFoot.getY() + topFoot.getHeight() > edge.getY()) {
                            e.setVelocity(e.velocity.x, ((edge.getY()) - (e.position.y + botFoot.getHeight() + topFoot.getHeight())) / delta);
                        }
                        break;
                    case 2:
                        if(edge.colliding(botFoot) && e.velocity.y < 0 && botFoot.getY() < edge.getY() + edge.getHeight()) {
                            e.setVelocity(e.velocity.x, ((edge.getY() + edge.getHeight()) - (e.position.y)) / delta);
                        }
                        break;
                    case 3:
                        if(edge.colliding(leftFoot) && e.velocity.x < 0 && leftFoot.getX() < edge.getX() + edge.getWidth()) {
                            e.setVelocity(((edge.getX() + edge.getWidth()) - (e.position.x)) / delta, e.velocity.y);
                        }
                        break;
                    case 4:
                        if(edge.colliding(rightFoot) && e.velocity.x > 0 && rightFoot.getX() + rightFoot.getWidth() > edge.getX()) {
                            e.setVelocity(((edge.getX()) - (e.position.x + e.size.x)) / delta, e.velocity.y);
                        }
                        break;
                    default:
                        Gdx.app.log("CollisionChecker", "Found invalid door");
                        break;
                }
            }

        }
        hitboxes.setPosition(e.position);
    }

    @Override
    public void update(float delta) {
        if(World.doorsOpen) {
            checkDoorEnter();
        }

        checkPlayerHit();
        checkEnemiesHit();
        checkBulletCrash();
        //checkPowerupCollect();
    }
}
