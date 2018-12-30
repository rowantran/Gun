package com.upa.gun.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.upa.gun.Direction;
import com.upa.gun.SpriteState;
import com.upa.gun.enemy.attacks.CircularAttack;
import com.upa.gun.enemy.attacks.NoAttack;
import com.upa.gun.enemy.attacks.TrackingBurstAttack;
import java.util.HashMap;
import java.util.Map;

public class PowerupFactory {

    Map<Integer, PowerupInfo> powerups;

    public PowerupFactory(String path) {

        powerups = new HashMap<Integer, PowerupInfo>();

        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(Gdx.files.internal(path)).get("powerups");

        for(JsonValue powerup : root) {
            int id = powerup.getInt("id");
            int width = powerup.getInt("width");
            int height = powerup.getInt("height");
            String hitboxType = powerup.getString("hitboxType");
            int hitboxWidth = powerup.getInt("hitboxWidth");
            int hitboxHeight = powerup.getInt("hitboxHeight");
            float damageMultiplier = powerup.getFloat("damageMultiplier");
            float speedMultiplier = powerup.getFloat("speedMultiplier");
            float bulletCooldownMultiplier = powerup.getFloat("bulletCooldownMultiplier");
            String name = powerup.getString("name");
            String spriteName = powerup.getString("sprite");
            String description = powerup.getString("description");
            String effectDescription = powerup.getString("effectDescription");

            PowerupInfo info = new PowerupInfo(id, width, height, hitboxType, hitboxWidth, hitboxHeight, name,
                    spriteName, description, effectDescription, damageMultiplier, speedMultiplier,
                    bulletCooldownMultiplier);
            powerups.put(id, info);
        }

    }

    Powerup createPowerup(int id, float x, float y) {
        return new Powerup(powerups.get(id), x, y);
    }

}
