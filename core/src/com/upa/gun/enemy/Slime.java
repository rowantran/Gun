package com.upa.gun.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.upa.gun.*;

import java.util.Map;

public class Slime extends Enemy {
    private float timeSinceRandomMove; //time since last random move has been done
    private float timeUntilRandomMove; //time between random moves, randomly generated
    private float randomMoveLength; //length of time slime will travel in a random direction
    private float timeOfRandomMove; //length of time slime has been travelling in a random direction

    private boolean movingRandom = false;

    float speedMultiplier = 1/2f;

    private static float HITBOX_SIZE = 10f;

    class SlimeAttackRotation extends AttackRotation {
        SlimeAttackRotation() {
            attacks.add(new TrackingBurstAttack(0.75f, 0.15f, true));
            attacks.add(new NoAttack(3.0f, true));
        }
    }

    Slime(float x, float y) {
        super(x, y, 36, 36);
        attackTimeElapsed = 0.0f;
        timeSinceAttack = 0.0f;

        timeSinceRandomMove = 0.0f;
        timeUntilRandomMove = 0.0f;
        randomMoveLength = 0.0f;
        timeOfRandomMove = 0.0f;

        rotation = new SlimeAttackRotation();
    }

    @Override
    Map<SpriteState, Map<Direction, Animation<TextureRegion>>> loadSprite() {
        return Assets.slimeAnimations;
    }

    @Override
    Enemy create(float x, float y) {
        return new Slime(x, y);
    }

    @Override
    public void createHitbox() {
        Vector2 position = getPosition();
        hitbox = new RectangularHitbox(position.x, position.y, HITBOX_SIZE, HITBOX_SIZE);
        centerHitbox();
    }

    public void update(float delta) {
        super.update(delta);

        if (dying) {
            opacity -= Settings.DEATH_FADE_SPEED * delta;
            if (opacity <= 0.0f) {
                dying = false;
                opacity = 0.0f;
                markedForDeletion = true;
            }
        } else {
            if (rotation.isAttacking()) {
                rotation.attack(getPosition());
                fireSound();
            } else {
                if (rotation.currentAttack().isMobile()) {
                    move(delta);
                } else {
                    setVelocity(0f, 0f);
                }
            }
        }
    }


    public SpriteState getState() {
        if (dying) {
            return SpriteState.HURT;
        } else if (!(rotation.currentAttack() instanceof NoAttack)) {
            return SpriteState.ATTACKING;
        } else {
            return SpriteState.MOVING;
        }
    }

    //default move toward player; horizontal or diagonal depending on position
    private void defaultMove(float delta) {
        Vector2 playerPos = World.player.getPosition();
        float playerX = playerPos.x;
        float playerY = playerPos.y;

        float slimeX = getPosition().x;
        float slimeY = getPosition().y;

        float pythagMultiplier = 0.7071f;

        if(slimeX < playerX) {
            setVelocity(Settings.SLIME_SPEED, 0);
        } else if(slimeX > playerX) {
            setVelocity(-Settings.SLIME_SPEED, 0);
        } else {
            setVelocity(0, 0);
        }
        if(slimeY < playerY) {
            setVelocity(getVelocity().x * pythagMultiplier,
                    Settings.SLIME_SPEED * pythagMultiplier);
        } else if(slimeY > playerY) {
            setVelocity(getVelocity().x * pythagMultiplier,
                    -Settings.SLIME_SPEED * pythagMultiplier);
        } else {
            setVelocity(getVelocity().x, 0);
        }
        if(getVelocity().x == 0 && getVelocity().y != 0) {
            setVelocity(0, getVelocity().y / pythagMultiplier);
        }


        if(slimeX <= 113f && getVelocity().x < 0) {
            setVelocity(0, getVelocity().y);
        }
        if(slimeX >= 1160f && getVelocity().x > 0) {
            setVelocity(0, getVelocity().y);
        }
        if(slimeY <= 136f && getVelocity().y < 0) {
            setVelocity(getVelocity().x, 0);
        }
        if(slimeY >= 674f && getVelocity().y > 0) {
            setVelocity(getVelocity().x, 0);
        }
    }


    //check if the slime is on the edge of boundaries
    public boolean checkBounds() {
        float x = getPosition().x;
        float y = getPosition().y;
        return(x <= 113f || x >= 1160f || y <= 136f || y >= 674);
    }


    //move in a random direction, occurs on a random interval
    public void randomMove() {
        float x = (float)(Math.random() * Settings.SLIME_SPEED);
        float y = getPythagY(x);
        int switchX = (int)(Math.random() * 2); //indicates whether x and y direction should be positive or negative
        int switchY = (int)(Math.random() * 2);

        if(switchX == 1) {
            x = -x;
        }
        if(switchY == 1) {
            y = -y;
        }
        setVelocity(x, y);
    }

    //uses pythagorean theorem to find corresponding y value for an x value to maintain speed
    public float getPythagY(float x) {
        return (float)(Math.sqrt(Math.pow(Settings.SLIME_SPEED,2) - Math.pow(x,2)));
    }

    //resets timers for random move and generates a new time until random move
    public void resetRandomMove() {
        int newRand1 = (int)(Math.random() * 2) + 1;
        timeUntilRandomMove = (float)(newRand1);
        timeSinceRandomMove = 0.0f;
        randomMoveLength = (float)((Math.random() * 2) + 1);
        timeOfRandomMove = 0.0f;
        movingRandom = false;
    }

    public void move(float delta) {
        if(timeSinceRandomMove < timeUntilRandomMove) { //normal moving time
            defaultMove(delta);
            timeSinceRandomMove += delta;
        } else if(timeSinceRandomMove >= timeUntilRandomMove) { //random moving time
            if(!movingRandom) { //just reached time
                randomMove();
                movingRandom = true;
            } else { //random move already set
                if(checkBounds()) { //slime is on a path to go out of bounds
                    resetRandomMove();
                }
                if(timeOfRandomMove >= randomMoveLength) { //time limit reached
                    resetRandomMove();
                } else {
                    timeOfRandomMove += delta; //time limit not reached
                }
            }
        }
    }

    public void fireSound() {
        Assets.bulletSound.stop();
        Assets.bulletSound.play(.4f);
    }
}
