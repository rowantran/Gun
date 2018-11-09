package com.upa.gun.enemy;

import java.util.Map;

public class EnemyInfo {
    public int id;
    public int health;

    public String hitboxType;
    public int hitboxWidth;
    public int hitboxHeight;

    public int width;
    public int height;

    public Map<String, String> sprites;

    public AttackRotation rotation;

    EnemyInfo(int id, int health, String hitboxType, int hitboxWidth, int hitboxHeight, int width, int height,
              Map<String, String> sprites, AttackRotation rotation) {
        this.id = id;
        this.health = health;
        this.hitboxType = hitboxType;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.width = width;
        this.height = height;
        this.sprites = sprites;
        this.rotation = rotation;
    }
}
