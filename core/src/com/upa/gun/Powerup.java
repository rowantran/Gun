package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Powerup extends Entity {

    public Sprite sprite;
    private int id;
    public PowerupInfo info;
    public boolean markedForDeletion;

    Powerup(PowerupInfo info, Vector2 position) {
        super(position, new Vector2(info.width, info.height));

        RectangularHitbox main = new RectangularHitbox(position, new Vector2(size.x/1.5f, size.y/1.5f));
        main.setPosition(new Vector2(position.x + size.x/2 - main.getWidth()/2, position.y + size.y/2 - main.getHeight()/2));
        hitbox.addHitbox("main", main);
        hitbox.setActive(true);

        sprite = new Sprite(Assets.powerups.findRegion("powerup-" + id));

        markedForDeletion = false;
        this.info = info;
        id = info.id;
    }

    @Override
    public Hitboxes getHitbox() {
        return hitbox;
    }
    public int getId() {
        return id;
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
            hitbox.setActive(false);
            player.powerupsActive.add(this);
        }
    }
}
