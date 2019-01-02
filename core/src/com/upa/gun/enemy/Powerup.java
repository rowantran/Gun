package com.upa.gun.enemy;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.*;
import java.util.Map;

public class Powerup extends Entity {

    public Map<String, String> sprites;
    public String sprite;
    private Hitbox hitbox;
    private int id;
    private PowerupInfo info;
    public boolean markedForDeletion;

    Powerup(PowerupInfo info, float x, float y) {
        super(x, y, info.width, info.height, 0 ,0);

        try {
            createHitbox(info.hitboxType, info.hitboxWidth, info.hitboxHeight);
        } catch(UnrecognizedHitboxTypeException e) {
            //do nothing I guess
        }

        markedForDeletion = false;
        this.info = info;
        id = info.id;
    }

    public int getId() {
        return id;
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }

    /**
     * Delete this powerup from the map and apply it to the player.
     */
    public void markForDeletion(Player player) {
        markedForDeletion = true;

        if (!player.hasPowerup(getId())) {
            Settings.playerSpeed *= info.speedMultiplier;
            Settings.playerDamage *= info.damageMultiplier;
            Settings.playerBulletCooldown *= info.bulletCooldownMultiplier;
            Settings.playerHealth += info.healthBonus;

            float startX = 80 + (Assets.healthFullLeft.getRegionWidth() * Settings.playerHealth);
            startX += (getSize().x * player.powerupsActive.size);

            setPosition(startX, 72);

            player.powerupsActive.add(this);
        }
    }

    private void createHitbox(String hitboxType, int width, int height) throws UnrecognizedHitboxTypeException {
        if (hitboxType.equals("rectangular")) {
            hitbox = new RectangularHitbox(getPosition().x, getPosition().y, width, height);
        } else {
            throw new UnrecognizedHitboxTypeException(hitboxType);
        }

        centerHitbox();
    }


}
