package com.upa.gun;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents the group of hitboxes attached to an object.
 */
public class Hitboxes implements Iterable<Hitbox> {
    private Map<String, Hitbox> hitboxes;

    public Hitboxes() {
        this(new HashMap<String, Hitbox>());
    }

    public Hitboxes(Map<String, Hitbox> hitboxes) {
        this.hitboxes = new HashMap<String, Hitbox>();
        for (Map.Entry<String, Hitbox> entry : hitboxes.entrySet()) {
            this.hitboxes.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * @param name The name of the desired child hitbox.
     * @return The requested child hitbox, or null if no such child exists.
     */
    public Hitbox getChild(String name) {
        return hitboxes.get(name);
    }

    /**
     * Checks each child of this Hitboxes against each child of the given Hitboxes.
     * @param other The other Hitboxes to check against.
     * @return Whether any child of this Hitboxes is colliding with any child of the other Hitboxes.
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
     * Checks each child of this Hitboxes against the given hitbox.
     * @param other The hitbox to check against each child.
     * @return Whether any child of this Hitboxes is colliding with the given hitbox.
     */
    public boolean colliding(Hitbox other) {
        for (Hitbox child : hitboxes.values()) {
            if (child.colliding(other)) {
                return true;
            }
        }

        return false;
    }

    public Iterator<Hitbox> iterator() {
        return hitboxes.values().iterator();
    }
}
