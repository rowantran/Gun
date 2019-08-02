package com.upa.gun.enemy;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.*;
import com.upa.gun.enemy.attacks.NoAttack;
import com.upa.gun.enemy.attacks.TrackingBurstAttack;

import java.util.HashMap;

import static com.upa.gun.Settings.*;

public class Slime extends Enemy {

    private float directionalUpdateCounter;
    private float directionalUpdateTimer;
    private float horizontalDifference;
    private float verticalDifference;

    class SlimeAttackRotation extends AttackRotation {
        SlimeAttackRotation() {
            attacks.add(new TrackingBurstAttack(0.75f, 0.15f, true));
            attacks.add(new NoAttack(3.0f, true));
        }
    }

    public Slime(EnemyInfo info, Vector2 position) {
        super(info, position);

        RectangularHitbox center = new RectangularHitbox(getPosition(), new Vector2(info.hitboxWidth, info.hitboxHeight));
        center.setPosition(new Vector2(getPosition().x + getSize().x/2 - center.getWidth()/2, getPosition().y + getSize().y/2 - center.getHeight()/2));

        RectangularHitbox left = new RectangularHitbox(new Vector2(position.x, position.y), new Vector2(12f, 24f));
        RectangularHitbox right = new RectangularHitbox(new Vector2(position.x + getSize().x - 12, position.y), new Vector2(12f, 24f));
        RectangularHitbox top = new RectangularHitbox(new Vector2(position.x, position.y + getSize().y/2 - 12), new Vector2(48f, 12f));
        RectangularHitbox bot = new RectangularHitbox(new Vector2(position.x, position.y), new Vector2(48f, 12f));

        hitbox.addHitbox("center", center);
        crateCheckHitbox.addHitbox("left", left);
        crateCheckHitbox.addHitbox("right", right);
        crateCheckHitbox.addHitbox("top", top);
        crateCheckHitbox.addHitbox("bot", bot);

        hitbox.setActive(true);
        crateCheckHitbox.setActive(true);

        directionalUpdateCounter = 1.0f;
        directionalUpdateTimer = 1.0f;
    }


    public void update(float delta) {
        super.update(delta);

        directionalUpdateCounter += delta;

        switch(state.mobileType()) {
            case 1:
                if (directionalUpdateCounter >= directionalUpdateTimer) {
                    directionalUpdateCounter = 0.0f;
                    directionalUpdateTimer = generateNewDirectionUpdateTimer();
                    updateDirection();
                    move();
                }
                break;
            case 2:
                setVelocity(getVelocity().x/3, getVelocity().y/3);
                break;
            default:
                setVelocity(0f, 0f);
        }
    }

    private void updateDirection() {
        Vector2 playerPos = World.player.getPosition();
        float playerX = playerPos.x;
        float playerY = playerPos.y;
        float slimeX = getPosition().x;
        float slimeY = getPosition().y;
        horizontalDifference = introduceOffset(playerX - slimeX);
        verticalDifference = introduceOffset(playerY - slimeY);
    }

    private float introduceOffset(float value) {
        if(value > 50f || value < -50f) {

            float minOffset = value/5;

            int addOffset = (int)(Math.random() * (value/2));
            float offset = (float)addOffset + minOffset;
            int direction = (int)(Math.random() * 2);
            if(direction == 0) {
                value += offset;
            }
            else {
                value -= offset;
            }
        }
        return value;
    }

    private float generateNewDirectionUpdateTimer() {
        int range = MAX_SLIME_UPDATE_TIMER - MIN_SLIME_UPDATE_TIMER;
        int timer = (int)(Math.random() * (double)(range));
        return (float)timer / 100 + (float)MIN_SLIME_UPDATE_TIMER / 100;
    }

    public void move() {

        float xSquare = horizontalDifference * horizontalDifference;
        float ySquare = verticalDifference * verticalDifference;
        double currentSquare = xSquare + ySquare;
        float currentSpeed = (float)Math.sqrt(currentSquare);
        float speedRatio = Settings.SLIME_SPEED / currentSpeed;
        setVelocity(horizontalDifference * speedRatio, verticalDifference * speedRatio);

    }

}
