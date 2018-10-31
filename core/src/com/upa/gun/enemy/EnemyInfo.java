package com.upa.gun.enemy;

public class EnemyInfo {
    public int id;
    public int health;

    public String hitboxType;
    public int hitboxWidth;
    public int hitboxHeight;

    public int width;
    public int height;

    public String sprite;

    public AttackRotation rotation;

    EnemyInfo(int id, int health, String hitboxType, int hitboxWidth, int hitboxHeight, int width, int height,
              String sprite, AttackRotation rotation) {
        this.id = id;
        this.health = health;
        this.hitboxType = hitboxType;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.rotation = rotation;
    }
}
