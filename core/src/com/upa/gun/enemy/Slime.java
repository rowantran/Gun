package com.upa.gun.enemy;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.*;

import static com.upa.gun.Settings.*;

/**
 * Class for all slime-type enemies
 */
public class Slime extends Enemy {

    private float directionalUpdateCounter;
    private float directionalUpdateTimer;
    private float horizontalDifference;
    private float verticalDifference;
    private float xVelocity;
    private float yVelocity;

    public Slime(EnemyInfo info, Vector2 position) {
        super(info, position);

        RectangularHitbox center = new RectangularHitbox(getPosition(), new Vector2(info.hitboxWidth, info.hitboxHeight));
        center.setPosition(new Vector2(position.x + size.x/2 - center.getWidth()/2, position.y + size.y/2 - center.getHeight()/2));

        hitbox.addHitbox("center", center);
        hitbox.generateCorrectOffsets();

        RectangularHitbox cCheck = new RectangularHitbox(position, new Vector2(size.x, 24));
        cCheckHitbox.addHitbox("cCheck", cCheck);

        hitbox.setActive(true);
        cCheckHitbox.setActive(true);

        directionalUpdateCounter = 1.0f;
        directionalUpdateTimer = 1.0f;
    }

    /**
     * Generates random time until direction of movement is updated
     * @return - Returns new time
     */
    private float generateNewDirectionUpdateTimer() {
        int range = MAX_SLIME_UPDATE_TIMER - MIN_SLIME_UPDATE_TIMER;
        int timer = (int)(Math.random() * (double)(range));
        return (float)timer / 100 + (float)MIN_SLIME_UPDATE_TIMER / 100;
    }

    /**
     * Converts x and y distances into scaled diagonal movement
     */
    public void move() {

        horizontalDifference = introduceOffset(World.player.getPosition().x - position.x);
        verticalDifference = introduceOffset(World.player.getPosition().y - position.y);

        boolean xNegative = horizontalDifference < 0;
        boolean yNegative = verticalDifference < 0;

        float xyRatio = horizontalDifference / verticalDifference;
        float cSquare = Settings.SLIME_SPEED * Settings.SLIME_SPEED;
        float ySquare = cSquare / (xyRatio * xyRatio + 1f);
        yVelocity = Math.abs((float)Math.sqrt(ySquare));
        xVelocity = Math.abs(yVelocity * xyRatio);

        if(verticalDifference == 0) {
            xVelocity = Settings.SLIME_SPEED;
            yVelocity = 0;
        }

        if(xNegative) { xVelocity *= -1f; }
        if(yNegative) { yVelocity *= -1f; }

        setVelocity(xVelocity, yVelocity);
    }

    /**
     * Creates offset to add randomness to movement
     * @param value - Original value
     * @return - Returns offset value
     */
    private float introduceOffset(float value) {
        if(value > 50f || value < -50f) {
            float minOffset = value/5;
            float addOffset = (float)(Math.random() * (value/2));
            float offset = minOffset + addOffset;
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

    /**
     * Update function; handles all movement
     * @param delta - Clock
     */
    public void update(float delta) {

        directionalUpdateCounter += delta;
        switch(state.mobileType()) {
            case 1:
                if (directionalUpdateCounter >= directionalUpdateTimer) {
                    directionalUpdateCounter = 0.0f;
                    directionalUpdateTimer = generateNewDirectionUpdateTimer();
                    move();
                }
                break;
            case 2:
                setVelocity(getVelocity().x/4, getVelocity().y/4);
                break;
            default:
                setVelocity(0f, 0f);
        }
        super.update(delta);
    }
}
