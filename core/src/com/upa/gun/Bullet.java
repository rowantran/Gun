package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

public class Bullet extends Entity {
    double angle;
    Polygon hitbox;

    Sprite bulletSprite;

    Bullet(float x, float y, double angle) {
        super(x, y, Assets.bulletBasic.getRegionWidth(), Assets.bulletBasic.getRegionHeight());
        this.angle = angle;
        bulletSprite = new Sprite(Assets.bulletBasic);
        bulletSprite.setRotation((float) (angle * 180 / Math.PI));

        hitbox = new Polygon(new float[]{bounds.x,bounds.y,bounds.x+bounds.width,bounds.y,
                bounds.x+bounds.width,bounds.y+bounds.height,bounds.x,bounds.y+bounds.height});
        hitbox.setOrigin(bounds.x, bounds.y);
        hitbox.setRotation((float) (angle * 180 / Math.PI));
    }

    public void update(float delta) {
        double dx = Math.cos(angle) * Settings.BULLET_SPEED * delta;
        double dy = Math.sin(angle) * Settings.BULLET_SPEED * delta;

        position.x += dx;
        position.y += dy;
        bounds.x = position.x;
        bounds.y = position.y;
        bulletSprite.setX(position.x);
        bulletSprite.setY(position.y);

        hitbox.translate((float)dx, (float)dy);
    }
}
