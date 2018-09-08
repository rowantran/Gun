package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public class Settings {
    public static final Vector2 RESOLUTION = new Vector2(1280, 800);

    public static final float BULLET_SPEED = 200.0f;
    public static final float PLAYER_SPEED = 250.0f;

    public static final float DEATH_ROTATE_SPEED = 80.0f;
    public static final float DEATH_FADE_SPEED = (DEATH_ROTATE_SPEED / 90.0f) * 1.0f;

    public static final boolean SHOW_HITBOXES = true;
}