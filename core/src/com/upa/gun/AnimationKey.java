package com.upa.gun;

/**
 * Represents the identifier for a specific animation asset. Consists of the name of the atlas file the animation is
 * stored in and the name of the animation within the atlas (enemy_00, enemy_01, etc.) would have a name of "enemy."
 */
public class AnimationKey {
    private String atlas;
    private String animationName;

    public AnimationKey(String atlas, String animationName) {
       this.atlas = atlas;
       this.animationName = animationName;
    }

    public String getAtlas() {
        return atlas;
    }

    public String getAnimationName() {
        return animationName;
    }

    @Override
    public int hashCode() {
        return atlas.hashCode() * animationName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimationKey that = (AnimationKey) o;
        return atlas.equals(that.atlas) && animationName.equals(that.animationName);
    }
}