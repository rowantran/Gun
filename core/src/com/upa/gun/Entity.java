package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity implements Updatable {

    private Vector2 position;
    private Vector2 size;
    private Vector2 velocity;

    public Hitboxes hitbox;

    public Entity(Vector2 position, Vector2 size) {
        this.position = position.cpy();
        this.size = size.cpy();
        velocity = new Vector2(0f, 0f);
        hitbox = new Hitboxes(position);
    }

    public Hitboxes getHitbox() {
        return hitbox;
    }

    protected void centerRectangularHitbox(RectangularHitbox hitbox) { //may delete in future cleanup
        hitbox.setPosition(new Vector2(position.x + size.x/2 - hitbox.getWidth()/2, position.y + size.y/2 - hitbox.getHeight()/2));
    }

    @Override
    public void update(float delta) {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        Hitboxes hitbox = getHitbox();
        hitbox.updateHitboxes(velocity.x * delta, velocity.y * delta);
    }

    public void specialMove(float delta) { //questionable
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
        Hitboxes hitbox = getHitbox();
        hitbox.updateHitboxes(velocity.x * delta, velocity.y * delta);
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    public Vector2 getSize() {
        return size.cpy();
    }

    public Vector2 getVelocity() { return velocity.cpy(); }

    public void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }

    void setVelocity(Vector2 velocity) {
        setVelocity(velocity.x, velocity.y);
    }
}
