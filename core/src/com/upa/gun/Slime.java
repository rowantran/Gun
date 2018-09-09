package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Slime extends Enemy {
    int rotation;

    public static int LEFT = 0;
    public static int RIGHT = 1;

    public float attackTimeElapsed;

    public float opacity;

    float timeBetweenAttacks = 3.0f;
    float attackLength = 0.75f;
    float shotInterval = 0.15f;
    float speedMultiplier = 1/2f;

    static float hitboxSize = 10f;

    World world;

    Sound shot;

    public Slime(float x, float y, World world, GunWorld gunWorld) {
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

        rotation = Slime.LEFT;

        opacity = 1.0f;

        this.world = world;

        shot = Gdx.audio.newSound(Gdx.files.internal("sfx/gunshot.mp3"));
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

        rotation = Slime.LEFT;

        opacity = 1.0f;

        this.world = world;

        shot = Gdx.audio.newSound(Gdx.files.internal("sfx/gunshot.mp3"));
    }

    public void update(float delta) {
        if (shooting) {
            attackTimeElapsed += delta;
            timeSinceAttack += delta;
        } else {
            timeElapsed += delta;
        }

        if (dying) {
            opacity -= Settings.DEATH_FADE_SPEED * delta;
            if (opacity <= 0.0f) {
                dying = false;
                opacity = 0.0f;
                markedForDeletion = true;
            }
        } else {
            handleShooting();
            move(delta);

            if (timeSinceAttack >= shotInterval) {
                shoot();
                timeSinceAttack = 0.0f;
            }
        }
    }

    public void handleShooting() {
        if (timeElapsed >= timeBetweenAttacks) {
            shooting = true;
            if (attackTimeElapsed >= attackLength) {
                attackTimeElapsed = 0;
                timeElapsed = 0;
                shooting = false;
            }
        } else {
            shooting = false;
        }
    }

    public void move(float delta) {
        int playerX = (int) gunWorld.player.body.getTransform().getPosition().x;
        int playerY = (int) gunWorld.player.body.getTransform().getPosition().y;

        int slimeX = (int) body.getTransform().getPosition().x;
        int slimeY = (int) body.getTransform().getPosition().y;


        if(!shooting) {
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

            if(slimeX <= 113 && body.getLinearVelocity().x < 0) {
                body.setLinearVelocity(0, body.getLinearVelocity().y);
            }
            if(slimeX >= 1160 && body.getLinearVelocity().x > 0) {
                body.setLinearVelocity(0, body.getLinearVelocity().y);
            }
            if(slimeY <= 136 && body.getLinearVelocity().y < 0) {
                body.setLinearVelocity(body.getLinearVelocity().x, 0);
            }
            if(slimeY >= 674 && body.getLinearVelocity().y > 0) {
                body.setLinearVelocity(body.getLinearVelocity().x, 0);
            }

        }

        if(shooting) {
            body.setLinearVelocity(0, 0);
        }
    }

    public void shoot() {
        if (shooting) {
            Vector2 slimePos = body.getTransform().getPosition();
            Vector2 bulletAngle = gunWorld.player.body.getTransform().getPosition()
                    .sub(slimePos);
            gunWorld.bullets.add(new EnemyBullet(slimePos.x, slimePos.y, bulletAngle.angleRad(),
                    world, Assets.bulletEnemy));
            fireSound();
        }
    }

    public void fireSound() {
        shot.stop();
        shot.play();
    }
}
