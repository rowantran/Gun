package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents the group of hitboxes attached to an object.
 */
public class Hitboxes implements Iterable<Hitbox> {

    private boolean active;
    private Map<String, Hitbox> hitboxes;
    private Vector2 position;

    public Hitboxes() {
        this(new HashMap<String, Hitbox>());
    }

    public Hitboxes(Vector2 origin) {
        this(new HashMap<String, Hitbox>());
        position = origin;
    }

    public Hitboxes(Map<String, Hitbox> hitboxes) {
        this.hitboxes = new HashMap<String, Hitbox>();
        for (Map.Entry<String, Hitbox> entry : hitboxes.entrySet()) {
            this.hitboxes.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Retrieves a single hitbox from the group of hitboxes
     * @param name - The name of the desired child hitbox
     * @return - The requested child hitbox, or null if no such child exists
     */
    public Hitbox getChild(String name) {
        return hitboxes.get(name);
    }

    /**
     * Adds a hitbox to this group of hitboxes
     * @param name - The name to identify the new hitbox
     * @param hitbox - The hitbox to add
     */
    public void addHitbox(String name, Hitbox hitbox) {
        hitboxes.put(name, hitbox);
    }

    /**
     * Checks each child of this Hitboxes against each child of the given Hitboxes
     * @param other - The other Hitboxes to check against
     * @return - Whether any child of this Hitboxes is colliding with any child of the other Hitboxes
     */
    public boolean colliding(Hitboxes other) {
        for (Hitbox child : hitboxes.values()) {
            if (other.colliding(child)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks each child of this Hitboxes against the given hitbox
     * @param other The hitbox to check against each child
     * @return Whether any child of this Hitboxes is colliding with the given hitbox
     */
    public boolean colliding(Hitbox other) {
        for (Hitbox child : hitboxes.values()) {
            if (child.colliding(other) && child.isActive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the offset of each child hitbox to be relative to the position of this Hitboxes
     */
    public void generateCorrectOffsets() {
        for(Hitbox hitbox : hitboxes.values()) {
            hitbox.setOffset(hitbox.getX() - position.x, hitbox.getY() - position.y);
        }
    }

    /**
     * Calls for an update of each child hitbox's position
     * @param adjustX - The amount to increment the x value of each child hitbox
     * @param adjustY - The amount to increment the y value of each child hitbox
     */
    public void updateHitboxes(float adjustX, float adjustY) {
        for (Hitbox child : hitboxes.values()) {
            child.adjustPosition(adjustX, adjustY);
        }
    }

    public Iterator<Hitbox> iterator() {
        return hitboxes.values().iterator();
    }
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        for(Hitbox child : hitboxes.values()) {
            child.setActive(active);
        }
    }

    public void setPosition(Vector2 position) {
        this.position = position.cpy();
        for(Hitbox hitbox : hitboxes.values()) {
            hitbox.setPosition(new Vector2(position.x + hitbox.getOffset().x, position.y + hitbox.getOffset().y));
        }
    }

}
