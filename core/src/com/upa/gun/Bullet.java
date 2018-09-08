package com.upa.gun;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends Entity {
    public Bullet(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public void update(float delta) {
        this.position.x += Settings.BULLET_SPEED * delta;
        this.bounds.x = this.position.x;
    }
    public void render(SpriteBatch batch) {
        batch.draw(Assets.bulletBasic, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
    }
}
