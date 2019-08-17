package com.upa.gun;

import java.util.Map;

public class PowerupInfo {

    public int id;
    public int width;
    public int height;
    public int hitboxWidth;
    public int hitboxHeight;
    public String name;
    public String sprite;
    public String description;
    public String effectDescription;
    public float damageMultiplier;
    public float speedMultiplier;
    public float bulletCooldownMultiplier;
    public int healthBonus;

    PowerupInfo(int id, int width, int height, int hitboxWidth, int hitboxHeight, String name,
                String sprite, String description, String effectDescription, float damageMultiplier,
                float speedMultiplier, float bulletCooldownMultiplier, int healthBonus) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.name = name;
        this.sprite = sprite;
        this.description = description;
        this.effectDescription = effectDescription;
        this.damageMultiplier = damageMultiplier;
        this.speedMultiplier = speedMultiplier;
        this.bulletCooldownMultiplier = bulletCooldownMultiplier;
        this.healthBonus = healthBonus;
    }


}
