package com.upa.gun;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Settings {
    public static final boolean DEV_MODE = false;
    public static final boolean SLOW_BULLETS = false;

    public static final float STEP_TIME = 1/300f;

    public static final Vector2 RESOLUTION = new Vector2(1280, 800);

    public static final float BULLET_SPEED = 500.0f;
    public static final float ENEMY_BULLET_SPEED = 350.0f;
    public static final float PLAYER_SPEED = 250.0f;
    public static final float SLIME_SPEED = 100.0f;

    public static final float DEATH_ROTATE_SPEED = 80.0f;
    public static final float DEATH_FADE_SPEED = (DEATH_ROTATE_SPEED / 90.0f) * 1.0f;

    public static final int KEY_LEFT = Input.Keys.A;
    public static final int KEY_RIGHT = Input.Keys.D;
    public static final int KEY_UP = Input.Keys.W;
    public static final int KEY_DOWN = Input.Keys.S;

    public static int PERCENT_SPAWN_CHANCE = 1;
    public static boolean PLAYER_DEATH = true;
}