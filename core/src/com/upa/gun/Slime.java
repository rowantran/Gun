package com.upa.gun;

import com.badlogic.gdx.physics.box2d.*;

public class Slime extends Enemy {
    public static int LEFT = 0;
    public static int RIGHT = 1;

    public float attackTimeElapsed;
    public float timeSinceRandomMove; //time since last random move has been done
    public float timeUntilRandomMove; //time between random moves, randomly generated
    public float randomMoveLength; //length of time slime will travel in a random direction
    public float timeOfRandomMove; //length of time slime has been travelling in a random direction

    public boolean movingRandom = false;

    public float opacity;

    float speedMultiplier = 1/2f;

    static float hitboxSize = 10f/Settings.PPM;

    World world;

    class SlimeAttackRotation extends AttackRotation {
        SlimeAttackRotation() {
            attacks.add(new TrackingBurstAttack(0.75f, 0.15f, true));
            attacks.add(new NoAttack(3.0f, true));
        }
    }

    Slime(float x, float y, World world, GunWorld gunWorld) {
        super(gunWorld);
        attackTimeElapsed = 0.0f;
        timeSinceAttack = 0.0f;

        timeSinceRandomMove = 0.0f;
        timeUntilRandomMove = 0.0f;
        randomMoveLength = 0.0f;
        timeOfRandomMove = 0.0f;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        CircleShape hitbox = new CircleShape();
        hitbox.setRadius(hitboxSize);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = hitbox;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);

        opacity = 1.0f;

        this.world = world;

        rotation = new SlimeAttackRotation();
    }

    public Slime(float x, float y, World world, GunWorld gunWorld, Shape hitbox) {
        super(gunWorld);
        attackTimeElapsed = 0.0f;
        timeSinceAttack = 0.0f;

        timeSinceRandomMove = 0f;
        timeUntilRandomMove = 0f;
        randomMoveLength = 0.0f;
        timeOfRandomMove = 0.0f;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = hitbox;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);

        opacity = 1.0f;

        this.world = world;

        rotation = new SlimeAttackRotation();
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
                rotation.attack(gunWorld, body.getPosition());
                fireSound();
            } else {
                if (rotation.currentAttack().isMobile()) {
                    move(delta);
                } else {
                    body.setLinearVelocity(0, 0);
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
    public void defaultMove(float delta) {
        float playerX = gunWorld.player.body.getTransform().getPosition().x;
        float playerY = gunWorld.player.body.getTransform().getPosition().y;

        float slimeX = body.getTransform().getPosition().x;
        float slimeY = body.getTransform().getPosition().y;

        float pythagMultiplier = 0.7071f;

        if(slimeX < playerX) {
            body.setLinearVelocity(Settings.SLIME_SPEED, 0);
        } else if(slimeX > playerX) {
            body.setLinearVelocity(-Settings.SLIME_SPEED, 0);
        } else {
            body.setLinearVelocity(0, 0);
        }
        if(slimeY < playerY) {
            body.setLinearVelocity(body.getLinearVelocity().x * pythagMultiplier,
                    Settings.SLIME_SPEED * pythagMultiplier);
        } else if(slimeY > playerY) {
            body.setLinearVelocity(body.getLinearVelocity().x * pythagMultiplier,
                    -Settings.SLIME_SPEED * pythagMultiplier);
        } else {
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
        }
        if(body.getLinearVelocity().x == 0 && body.getLinearVelocity().y != 0) {
            body.setLinearVelocity(0, body.getLinearVelocity().y / pythagMultiplier);
        }


        if(slimeX <= 113f/Settings.PPM && body.getLinearVelocity().x < 0) {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }
        if(slimeX >= 1160f/Settings.PPM && body.getLinearVelocity().x > 0) {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }
        if(slimeY <= 136f/Settings.PPM && body.getLinearVelocity().y < 0) {
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
        }
        if(slimeY >= 674f/Settings.PPM && body.getLinearVelocity().y > 0) {
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
        }
    }


    //check if the slime is on the edge of boundaries
    public boolean checkBounds() {
        float x = body.getTransform().getPosition().x;
        float y = body.getTransform().getPosition().y;
        return(x <= 113f/Settings.PPM || x >= 1160f/Settings.PPM || y <= 136f/Settings.PPM || y >= 674/Settings.PPM);
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
        body.setLinearVelocity(x, y);
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
