package com.upa.gun.enemy;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.*;

import static com.upa.gun.Settings.*;

public class Slime extends Enemy {

    private float directionalUpdateCounter;
    private float directionalUpdateTimer;
    private float horizontalDifference;
    private float verticalDifference;

    public Slime(EnemyInfo info, Vector2 position) {
        super(info, position);

        RectangularHitbox center = new RectangularHitbox(getPosition(), new Vector2(info.hitboxWidth, info.hitboxHeight));
        center.setPosition(new Vector2(getPosition().x + getSize().x/2 - center.getWidth()/2, getPosition().y + getSize().y/2 - center.getHeight()/2));

        hitbox.addHitbox("center", center);
        hitbox.generateCorrectOffsets();

        RectangularHitbox left = new RectangularHitbox(new Vector2(position.x, position.y), new Vector2(12f, 24f));
        RectangularHitbox right = new RectangularHitbox(new Vector2(position.x + getSize().x - 12, position.y), new Vector2(12f, 24f));
        RectangularHitbox top = new RectangularHitbox(new Vector2(position.x, position.y + getSize().y/2 - 12), new Vector2(48f, 12f));
        RectangularHitbox bot = new RectangularHitbox(new Vector2(position.x, position.y), new Vector2(48f, 12f));

        crateCheckHitbox.addHitbox("left", left);
        crateCheckHitbox.addHitbox("right", right);
        crateCheckHitbox.addHitbox("top", top);
        crateCheckHitbox.addHitbox("bot", bot);
        crateCheckHitbox.generateCorrectOffsets();

        hitbox.setActive(true);
        crateCheckHitbox.setActive(true);

        directionalUpdateCounter = 1.0f;
        directionalUpdateTimer = 1.0f;
    }

    /**
     * Finds the distance between the slime and the player
     */
    private void updateDirection() {
        Vector2 playerPos = World.player.getPosition();
        float playerX = playerPos.x;
        float playerY = playerPos.y;
        float slimeX = getPosition().x;
        float slimeY = getPosition().y;
        horizontalDifference = introduceOffset(playerX - slimeX);
        verticalDifference = introduceOffset(playerY - slimeY);
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
        float xSquare = horizontalDifference * horizontalDifference;
        float ySquare = verticalDifference * verticalDifference;
        double currentSquare = xSquare + ySquare;
        float currentSpeed = (float)Math.sqrt(currentSquare);
        float speedRatio = Settings.SLIME_SPEED / currentSpeed;
        setVelocity(horizontalDifference * speedRatio, verticalDifference * speedRatio);
    }

    /**
     * Creates offset to add randomness to movement
     * @param value - Original value
     * @return - Returns offset value
     */
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

    /**
     * Update function; handles all movement
     * @param delta - clock
     */
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
                setVelocity(getVelocity().x/4, getVelocity().y/4);
                break;
            default:
                setVelocity(0f, 0f);
        }
    }
}
