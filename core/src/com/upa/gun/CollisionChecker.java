package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.upa.gun.enemy.Enemy;

/**
 * Checks and handles collisions of hitboxes
 */
public class CollisionChecker implements Updatable {

    /**
     * Checks if the player has entered a door
     */
    private void checkDoorEnter() {
        World.roomChange = 0;
        World.resetTimer();
        for (Door d : World.currentMap.getDoors()) {
            if (World.player.cCheckHitbox.colliding(d.getHitbox().getChild("open"))) {
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

    /**
     * Checks if the player has been hit by an enemy bullet
     */
    private void checkPlayerHit() {
        for (Bullet b : World.enemyBullets) {
            if (b.getHitbox().colliding(World.player.hitbox.getChild("center"))) {
                World.player.hurt(1); //will leave dying state when other condition occurs - needs fix
            }
        }
    }

    /**
     * Checks if an enemy has been hit by a player bullet
     */
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

    /**
     * Checks if the player is touching a powerup
     */
    private void checkPowerupCollect() {
        for(Powerup p : World.powerups) {
            if (World.player.hitbox.colliding(p.hitbox)) {
                p.markForDeletion(World.player);
            }
        }
    }

    /**
     * Checks if a bullet has hit a terrain element
     */
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


    /**
     * Determines if, when hitting the corner of a terrain element, an entity should slide horizontally or vertically
     * @param crateCorner - The coordinate of the corner of the object that has been collided with
     * @param entityCorner - The corner of the entity that has collided with the object
     * @return - Returns 0 if the entity should slide vertically, 1 if the entity should slide horizontally
     */
    private int compareCollisions(Vector2 crateCorner, Vector2 entityCorner) {

        float horizontal = Math.abs(crateCorner.x - entityCorner.x);
        float vertical = Math.abs(crateCorner.y - entityCorner.y);

        if(horizontal < vertical) {
            return 0;
        }
        else {
            return 1;
        }
    }

    /**
     * Checks if an entity will be inside a terrain element in the next frame. If so, the position is set to the edge
     * of the object and the velocity in that direction is set to 0
     * @param delta - Clock
     * @param e - Entity being checked
     * @param hitboxes - The entity's hitbox to be used for collision detection with terrain elements
     */
    public void checkFutureCollisions(float delta, Entity e, Hitboxes hitboxes) {

        hitboxes.setPosition(new Vector2(e.position.x + e.velocity.x * delta, e.position.y + e.velocity.y * delta));
        Hitbox foot = hitboxes.getChild("cCheck");

        Vector2 velocityCopy = e.velocity.cpy();
        Vector2 positionCopy = e.position.cpy();

        float fLeft = foot.getX();
        float fRight = foot.getX() + foot.getWidth();
        float fTop = foot.getY() + foot.getHeight();
        float fBot = foot.getY();

        for(Crate c : World.currentMap.getCrates()) {

            Hitbox leftEdge = c.getHitbox().getChild("leftEdge");
            Hitbox rightEdge = c.getHitbox().getChild("rightEdge");
            Hitbox topEdge = c.getHitbox().getChild("topEdge");
            Hitbox botEdge = c.getHitbox().getChild("botEdge");

            float cLeft = c.position.x;
            float cRight = c.position.x + c.size.x;
            float cTop = c.position.y + c.size.y;
            float cBot = c.position.y;

            boolean leftEdgeCollide = false;
            boolean rightEdgeCollide = false;

            if(rightEdge.colliding(foot) && e.velocity.x < 0 && fLeft < cRight) {
                e.setPosition(cRight, e.position.y);
                e.setVelocity(0, e.velocity.y);
                rightEdgeCollide = true;
            } else if(leftEdge.colliding(foot) && e.velocity.x > 0 && fRight > cLeft) {
                e.setPosition(cLeft - e.size.x, e.position.y);
                e.setVelocity(0, e.velocity.y);
                leftEdgeCollide = true;
            }

            if(topEdge.colliding(foot) && e.velocity.y < 0 && fBot < cTop) {
                if(rightEdgeCollide) {
                    int compare = compareCollisions(new Vector2(cRight, cTop), new Vector2(fLeft, fBot));
                    if(compare == 1) {
                        e.setPosition(positionCopy.x, cTop);
                        e.setVelocity(velocityCopy.x, 0);
                    }
                } else if(leftEdgeCollide) {
                    int compare = compareCollisions(new Vector2(cLeft, cTop), new Vector2(fRight, fBot));
                    if(compare == 1) {
                        e.setPosition(positionCopy.x, cTop);
                        e.setVelocity(velocityCopy.x, 0);
                    }
                } else {
                    e.setPosition(e.position.x, cTop);
                    e.setVelocity(e.velocity.x, 0);
                }
            } else if(botEdge.colliding(foot) && e.velocity.y > 0 && fTop > cBot) {
                if (rightEdgeCollide) {
                    int compare = compareCollisions(new Vector2(cRight, cBot), new Vector2(fLeft, fTop));
                    if(compare == 1) {
                        e.setPosition(positionCopy.x, cBot - foot.getHeight());
                        e.setVelocity(velocityCopy.x, 0);
                    }
                } else if (leftEdgeCollide) {
                    int compare = compareCollisions(new Vector2(cLeft, cBot), new Vector2(fRight, fTop));
                    if(compare == 1) {
                        e.setPosition(positionCopy.x, cBot - foot.getHeight());
                        e.setVelocity(velocityCopy.x, 0);
                    }
                } else {
                    e.setPosition(e.position.x, cBot - foot.getHeight());
                    e.setVelocity(e.velocity.x, 0);
                }
            }
        }

        if(!World.doorsOpen) {

            for(Door d : World.currentMap.getDoors()) {

                Hitbox edge = d.getHitbox().getChild("closed");

                float dLeft = edge.getX();
                float dRight = edge.getX() + edge.getWidth();
                float dTop = edge.getY() + edge.getHeight();
                float dBot = edge.getY();

                switch(d.getDirection()) {
                    case 1:
                        if(edge.colliding(foot) && e.velocity.y > 0 && fTop > dBot) {
                            e.setPosition(e.position.x, dBot - foot.getHeight());
                            e.setVelocity(e.velocity.x, 0);
                        }
                        break;
                    case 2:
                        if (edge.colliding(foot) && e.velocity.y < 0 && fBot < dTop) {
                            e.setPosition(e.position.x, dTop);
                            e.setVelocity(e.velocity.x, 0);
                        }
                        break;
                    case 3:
                        if(edge.colliding(foot) && e.velocity.x < 0 && fLeft < dRight) {
                            e.setPosition(dRight, e.position.y);
                            e.setVelocity(0, e.velocity.y);
                        }
                        break;
                    case 4:
                        if(edge.colliding(foot) && e.velocity.x > 0 && fRight > dLeft) {
                            e.setPosition(dLeft - foot.getWidth(), e.position.y);
                            e.setVelocity(0, e.velocity.y);
                        }
                        break;
                    default:
                        Gdx.app.log("CollisionChecker", "Found invalid door");
                }
            }
        }
        hitboxes.setPosition(e.position);
    }

    /**
     * Update function; calls the necessary collision checks
     * @param delta - Clock
     */
    @Override
    public void update(float delta) {
        if(World.doorsOpen) {
            checkDoorEnter();
        }

        checkPlayerHit();
        checkEnemiesHit();
        checkBulletCrash();
        checkPowerupCollect();
    }
}
