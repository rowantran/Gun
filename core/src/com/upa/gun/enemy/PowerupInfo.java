package com.upa.gun.enemy;

import java.util.Map;

public class PowerupInfo {

    public int id;
    public int width;
    public int height;
    public String hitboxType;
    public int hitboxWidth;
    public int hitboxHeight;
    public String name;
    public String sprite;
    public String description;
    public String effectDescription;
    public float damageMultiplier;
    public float speedMultiplier;
    public float bulletCooldownMultiplier;

    PowerupInfo(int id, int width, int height, String hitboxType, int hitboxWidth, int hitboxHeight, String name,
                String sprite, String description, String effectDescription, float damageMultiplier,
                float speedMultiplier, float bulletCooldownMultiplier) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.hitboxType = hitboxType;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.name = name;
        this.sprite = sprite;
        this.description = description;
        this.effectDescription = effectDescription;
        this.damageMultiplier = damageMultiplier;
        this.speedMultiplier = speedMultiplier;
        this.bulletCooldownMultiplier = bulletCooldownMultiplier;
    }


}
