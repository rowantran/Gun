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

    public void checkFutureCollisions2(float delta, Entity e, Hitboxes hitboxes) {

        hitboxes.setPosition(new Vector2(e.position.x + e.velocity.x * delta, e.position.y + e.position.y * delta));

        for(Crate c : World.currentMap.getCrates()) {

            Hitbox rightEdge = c.getHitbox().getChild("rightEdge");
            Hitbox leftEdge = c.getHitbox().getChild("leftEdge");
            Hitbox topEdge = c.getHitbox().getChild("topEdge");
            Hitbox botEdge = c.getHitbox().getChild("botEdge");

            if(hitboxes.colliding(rightEdge) && e.velocity.x < 0 && e.position.x < c.position.x + c.size.x) {
                e.setVelocity(((c.position.x + c.size.x) - (e.position.x)) / delta, e.velocity.y);
            }
            else if(hitboxes.colliding(leftEdge) && e.velocity.x > 0 && e.position.x + e.size.x > c.position.x) {
                e.setVelocity(((c.position.x) - (e.position.x + e.size.x)) / delta, e.velocity.y);
            }
            if(hitboxes.colliding(topEdge) && e.velocity.y < 0 && e.position.y < c.position.y + c.size.y) {
                e.setVelocity(e.velocity.x, ((c.position.y + c.size.y) - (e.position.y)) / delta);
            }
            else if(hitboxes.colliding(botEdge) && e.velocity.y > 0 && e.position.y + e.size.y > c.position.y) {
                e.setVelocity(e.velocity.x, ((c.position.y) - (e.position.y + hitboxes.getChild("botFoot").getHeight() + hitboxes.getChild("topFoot").getHeight())) / delta);
            }

        }

    }


    public void checkFutureCollisions(float delta, Entity e, Hitboxes hitboxes) {

        hitboxes.setPosition(new Vector2(e.position.x + e.velocity.x * delta, e.position.y + e.velocity.y * delta));

        Hitbox leftFoot = hitboxes.getChild("leftFoot");
        Hitbox rightFoot = hitboxes.getChild("rightFoot");
        Hitbox topFoot = hitboxes.getChild("topFoot");
        Hitbox botFoot = hitboxes.getChild("botFoot");

        float left = e.position.x;
        float right = e.position.x + e.size.x;
        float top = e.position.y + botFoot.getHeight() + topFoot.getHeight();
        float bot = e.position.y;

        for(Crate c : World.currentMap.getCrates()) {

            Hitbox rightEdge = c.getHitbox().getChild("rightEdge");
            Hitbox leftEdge = c.getHitbox().getChild("leftEdge");
            Hitbox topEdge = c.getHitbox().getChild("topEdge");
            Hitbox botEdge = c.getHitbox().getChild("botEdge");

            float cLeft = c.position.x;
            float cRight = c.position.x + c.size.x;
            float cTop = c.position.y + c.size.y;
            float cBot = c.position.y;

            if(rightEdge.colliding(leftFoot) && e.velocity.x < 0 && leftFoot.getX() < rightEdge.getX() + rightEdge.getWidth()) {
                e.setPosition(cRight, e.position.y);
                e.setVelocity(0, e.velocity.y);
            }
            else if(leftEdge.colliding(rightFoot) && e.velocity.x > 0 && rightFoot.getX() + rightFoot.getWidth() > leftEdge.getX()) {
                e.setPosition(cLeft - e.size.x, e.position.y);
                e.setVelocity(0, e.velocity.y);
            }
            else if(topEdge.colliding(botFoot) && e.velocity.y < 0 && botFoot.getY() < topEdge.getY() + topEdge.getHeight()) {
                e.setPosition(e.position.x, cTop);
                e.setVelocity(e.velocity.x, 0);
            }
            else if(botEdge.colliding(topFoot) && e.velocity.y > 0 && topFoot.getY() + topFoot.getHeight() > botEdge.getY()) {
                e.setPosition(e.position.x, cBot - (botFoot.getHeight() + topFoot.getHeight()));
                e.setVelocity(e.velocity.x, 0);
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

    /*
    public void checkFutureCollisions(float delta, Entity e, Hitboxes hitboxes) {

        hitboxes.setPosition(new Vector2(e.position.x + e.velocity.x * delta, e.position.y + e.velocity.y * delta));

        Hitbox leftFoot = hitboxes.getChild("leftFoot");
        Hitbox rightFoot = hitboxes.getChild("rightFoot");
        Hitbox topFoot = hitboxes.getChild("topFoot");
        Hitbox botFoot = hitboxes.getChild("botFoot");

        float left = e.position.x;
        float right = e.position.x + e.size.x;
        float top = e.position.y + botFoot.getHeight() + topFoot.getHeight();
        float bot = e.position.y;

        for(Crate c : World.currentMap.getCrates()) {

            Hitbox rightEdge = c.getHitbox().getChild("rightEdge");
            Hitbox leftEdge = c.getHitbox().getChild("leftEdge");
            Hitbox topEdge = c.getHitbox().getChild("topEdge");
            Hitbox botEdge = c.getHitbox().getChild("botEdge");

            float cLeft = c.position.x;
            float cRight = c.position.x + c.size.x;
            float cTop = c.position.y + c.size.y;
            float cBot = c.position.y;

            if(rightEdge.colliding(leftFoot) && e.velocity.x < 0 && leftFoot.getX() < rightEdge.getX() + rightEdge.getWidth()) {
                System.out.println("RIGHT EDGE PRE-HIT " + e.velocity.x + ", " + e.velocity.y);
                e.setVelocity((cRight - left) / delta, e.velocity.y);
                System.out.println(cRight + " " + left + " / " + delta + " --> " + ((cRight-left)/delta));
                System.out.println("RIGHT EDGE HIT " + e.velocity.x + ", " + e.velocity.y);
                System.out.println();
            }
            else if(leftEdge.colliding(rightFoot) && e.velocity.x > 0 && rightFoot.getX() + rightFoot.getWidth() > leftEdge.getX()) {
                System.out.println("LEFT EDGE PRE-HIT " + e.velocity.x + ", " + e.velocity.y);
                e.setVelocity((cLeft - right) / delta, e.velocity.y);
                System.out.println("TOP VALUE: " + top);
                System.out.println("RIGHT VALIE: " + right);
                System.out.println(cLeft + " " + right + " / " + delta + " --> " + ((cLeft-right)/delta));
                System.out.println("LEFT EDGE HIT " + e.velocity.x + ", " + e.velocity.y);
                System.out.println();
            }
            if(topEdge.colliding(botFoot) && e.velocity.y < 0 && botFoot.getY() < topEdge.getY() + topEdge.getHeight()) {
                System.out.println("TOP EDGE PRE-HIT " + e.velocity.x + ", " + e.velocity.y);
                e.setVelocity(e.velocity.x, (cTop - bot) / delta);
                System.out.println(cTop + " - " + bot + " / " + delta + " --> " + ((cTop-bot)/delta));
                System.out.println("TOP EDGE HIT " + e.velocity.x + ", " + e.velocity.y);
                System.out.println();
            }
            else if(botEdge.colliding(topFoot) && e.velocity.y > 0 && topFoot.getY() + topFoot.getHeight() > botEdge.getY()) {
                System.out.println("BOT EDGE PRE-HIT " + e.velocity.x + ", " + e.velocity.y);
                e.setVelocity(e.velocity.x, (cBot - top) / delta);
                System.out.println("TOP VALUE: " + top);
                System.out.println("RIGHT VALIE: " + right);
                System.out.println(cBot + " " + top + " / " + delta + " --> " + ((cBot-top)/delta));
                System.out.println("BOT EDGE HIT " + e.velocity.x + ", " + e.velocity.y);
                System.out.println();
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
    */

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
