package com.upa.gun.enemy;

import com.badlogic.gdx.math.Vector2;
import com.upa.gun.*;
import java.util.Map;

public class Powerup extends Entity {

    public float opacity;
    public Map<String, String> sprites;
    public String sprite;
    private Hitbox hitbox;
    private int id;

    Powerup(PowerupInfo info, float x, float y) {
        super(x, y, info.width, info.height, 0 ,0);

        try {
            createHitbox(info.hitboxType, info.hitboxWidth, info.hitboxHeight);
        } catch(UnrecognizedHitboxTypeException e) {
            //do nothing I guess
        }

        opacity = 1f;

    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
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
