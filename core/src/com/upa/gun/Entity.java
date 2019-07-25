package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity implements Updatable {
    private Vector2 position;
    private Vector2 size;
    private Vector2 velocity;

    public Hitboxes hitboxes;

    public Entity(Vector2 position, Vector2 size) {
        this.position = position.cpy();
        this.size = size.cpy();
        velocity = new Vector2(0f, 0f);

        hitboxes = new Hitboxes();
    }

    public Entity(float x, float y, float width, float height, float hitboxOffsetX, float hitboxOffsetY) {
        this(new Vector2(x, y), new Vector2(width, height));
    }

    /**
     * @return The hitbox collection of this entity.
     */
    public abstract Hitboxes getHitbox();

    /*
    protected void centerHitbox(Hitbox hitbox) {
        setHitboxOffset((getSize().x - hitbox.getWidth()) / 2, (getSize().y - hitbox.getHeight()) / 2);
    }
    */

    protected void centerRectangularHitbox(RectangularHitbox hitbox) {
        hitbox.setOffset(new Vector2((getSize().x/2) - (hitbox.getWidth()/2), (getSize().y/2) - (hitbox.getHeight())));
        hitbox.fixPosition(position); //definitely kind of wonky
    }

    @Override
    public void update(float delta) {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        // Update hitbox to match new position
        Hitboxes hitbox = getHitbox();
        hitbox.updateHitboxes(velocity.x * delta, velocity.y * delta);
    }

    public void specialMove(float delta) {
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

    public Vector2 getVelocity() {
        return velocity.cpy();
    }

    public void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }

    void setVelocity(Vector2 velocity) {
        setVelocity(velocity.x, velocity.y);
    }
}
