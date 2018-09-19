package com.upa.gun;

import com.badlogic.gdx.physics.box2d.*;

public class Slime extends Enemy {
    public static int LEFT = 0;
    public static int RIGHT = 1;

    public float attackTimeElapsed;

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

    public void move(float delta) {
        float playerX = gunWorld.player.body.getTransform().getPosition().x;
        float playerY = gunWorld.player.body.getTransform().getPosition().y;

        float slimeX = body.getTransform().getPosition().x;
        float slimeY = body.getTransform().getPosition().y;

        if (slimeX < playerX) {
            if (slimeY < playerY) {
                body.setLinearVelocity(Settings.SLIME_SPEED * speedMultiplier, Settings.SLIME_SPEED * speedMultiplier);
            }
            if (slimeY > playerY) {
                body.setLinearVelocity(Settings.SLIME_SPEED * speedMultiplier, -Settings.SLIME_SPEED * speedMultiplier);
            }
            if (slimeY == playerY) {
                body.setLinearVelocity(Settings.SLIME_SPEED, 0);
            }
        }
        if (slimeX > playerX) {
            if (slimeY < playerY) {
                body.setLinearVelocity(-Settings.SLIME_SPEED * speedMultiplier, Settings.SLIME_SPEED * speedMultiplier);
            }
            if (slimeY > playerY) {
                body.setLinearVelocity(-Settings.SLIME_SPEED * speedMultiplier, -Settings.SLIME_SPEED * speedMultiplier);
            }
            if (slimeY == playerY) {
                body.setLinearVelocity(-Settings.SLIME_SPEED, 0);
            }
        }
        if (slimeX == playerX) {
            if (slimeY < playerY) {
                body.setLinearVelocity(0, Settings.SLIME_SPEED);
            }
            if (slimeY > playerY) {
                body.setLinearVelocity(0, -Settings.SLIME_SPEED);
            }
            if(slimeY == playerY) {
                body.setLinearVelocity(0, 0);
            }
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

    public void fireSound() {
        Assets.bulletSound.stop();
        Assets.bulletSound.play(.4f);
    }
}
