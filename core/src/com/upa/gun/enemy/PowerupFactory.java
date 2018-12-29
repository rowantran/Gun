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

    public PowerupFactory(String path) {

        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(Gdx.files.internal(path)).get("powerups");

        for(JsonValue powerup : root) {
            int id = powerup.getInt("id");
            float damageMultiplier = powerup.getFloat("damageMultiplier");
            float speedMultiplier = powerup.getFloat("speedMultiplier");
            float bulletCooldownMultiplier = powerup.getFloat("bulletCooldownMultiplier");
            String name = powerup.getString("name");
            String spriteName = powerup.getString("sprite");
            String description = powerup.getString("description");
            String effectDescription = powerup.getString("effectDescription");

            PowerupInfo info = new PowerupInfo(id, name, spriteName, description, effectDescription, damageMultiplier,
                    speedMultiplier, bulletCooldownMultiplier);

        }

    }

}
