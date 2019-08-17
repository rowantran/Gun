package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import java.util.HashMap;
import java.util.Map;

public class PowerupFactory {

    public static PowerupFactory powerupFactory = new PowerupFactory("powerups.json");
    public Map<Integer, PowerupInfo> powerups;

    public PowerupFactory(String path) {

        powerups = new HashMap<Integer, PowerupInfo>();

        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(Gdx.files.internal(path)).get("powerups");

        for(JsonValue powerup : root) {
            int id = powerup.getInt("id");
            int width = powerup.getInt("width");
            int height = powerup.getInt("height");
            int hitboxWidth = powerup.getInt("hitboxWidth");
            int hitboxHeight = powerup.getInt("hitboxHeight");
            float damageMultiplier = powerup.getFloat("damageMultiplier");
            float speedMultiplier = powerup.getFloat("speedMultiplier");
            float bulletCooldownMultiplier = powerup.getFloat("bulletCooldownMultiplier");
            int healthBonus = powerup.getInt("healthBonus");
            String name = powerup.getString("name");
            String spriteName = powerup.getString("sprite");
            String description = powerup.getString("description");
            String effectDescription = powerup.getString("effectDescription");

            PowerupInfo info = new PowerupInfo(id, width, height, hitboxWidth, hitboxHeight, name,
                    spriteName, description, effectDescription, damageMultiplier, speedMultiplier,
                    bulletCooldownMultiplier, healthBonus);
            powerups.put(id, info);
        }

        Gdx.app.log("PowerupFactory", "Completed loading powerups");
    }

    public static PowerupFactory getInstance() {
        return powerupFactory;
    }

    public Powerup createPowerup(int id, Vector2 position) {
        return new Powerup(powerups.get(id), position);
    }

}
