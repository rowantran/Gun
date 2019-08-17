package com.upa.gun.enemy;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.*;
import java.util.Map;

public class Powerup extends Entity {

    public Map<String, String> sprites;
    public String sprite;
    private int id;
    public PowerupInfo info;
    public boolean markedForDeletion;

    Powerup(PowerupInfo info, Vector2 position) {
        super(position, new Vector2(info.width, info.height));

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
    public Hitboxes getHitbox() {
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

            float startX = 80 + (Assets.healthBars.findRegion("player-left-full").getRegionWidth() * Settings.playerHealth);
            startX += (getSize().x * player.powerupsActive.size);

            setPosition(startX, 72f);
            //getHitbox().setActive(false);
            /*
            for (Hitbox hitbox : hitboxes) {
                hitbox.setActive(false);
            }
            */

            player.powerupsActive.add(this);
        }
    }

    private void createHitbox(String hitboxType, int width, int height) throws UnrecognizedHitboxTypeException {
        if (hitboxType.equals("rectangular")) {
            hitbox = new Hitboxes();
        } else {
            throw new UnrecognizedHitboxTypeException(hitboxType);
        }

        //centerHitbox();
        /*
            RectangularHitbox hitbox = new RectangularHitbox(getPosition(), new Vector2(width, height));
            centerRectangularHitbox(hitbox);
            hitboxes.addHitbox("hitbox", hitbox);
        } else {
            throw new UnrecognizedHitboxTypeException(hitboxType);
        }
        */
    }


}
