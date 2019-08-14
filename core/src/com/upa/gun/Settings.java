package com.upa.gun;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Settings {
    public static final int LOG_LEVEL = Application.LOG_DEBUG;

    public static final boolean DEV_MODE = true;
    public static final boolean INVINCIBLE = true;
    public static final boolean SPAWN_CAP = false;
    public static final int SPAWN_CAP_LIMIT = 1;
    public static final boolean MUTE = true;

    public static final float ROOM_CHANGE_TIME = 0.8f;
    public static final float ROOM_CHANGE_TIME_BUFFER = 0.3f;
    public static final float ROOM_CHANGE_STEP = 100f;
    public static final float ROOM_CHANGE_STEP_SPEED = 400f;

    public static final float STEP_TIME = 1/300f;

    public static final Vector2 RESOLUTION = new Vector2(1280, 800);

    public static final float BULLET_SPEED = 500.0f;

    public static final float ENEMY_BULLET_SPEED_MULTIPLIER = 0.8f;

    public static final float SLIME_SPEED = 100.0f;
    public static final float ROLL_DELAY = 1.5f;
    public static final float ROLL_SPEED = 500.0f;
    public static final float ROLL_LENGTH = 0.25f;

    public static final float DEATH_ROTATE_SPEED = 80.0f;
    public static final float DEATH_FADE_SPEED = (DEATH_ROTATE_SPEED / 40.0f) * 1.0f;

    public static final int MAX_SLIME_UPDATE_TIMER = 80; //divide by 100
    public static final int MIN_SLIME_UPDATE_TIMER = 30;

    public static final float DAMAGE_FLASH_LENGTH = 1/20f;
    public static final float SLOW_FADE_SPEED = 0.4f;

    public static final int KEY_LEFT = Input.Keys.A;
    public static final int KEY_RIGHT = Input.Keys.D;
    public static final int KEY_UP = Input.Keys.W;
    public static final int KEY_DOWN = Input.Keys.S;
    public static final int KEY_ROLL = Input.Keys.SHIFT_LEFT;

    public static int PERCENT_SPAWN_CHANCE = 1;
    public static boolean PLAYER_DEATH = true;
    public static final float I_FRAME_LENGTH = 1.0f;


    //player variables
    public static float playerBulletCooldown = 0.1f;
    public static float playerSpeed = 250.0f;
    public static int playerDamage = 2;
    public static int playerHealth = 10;
}