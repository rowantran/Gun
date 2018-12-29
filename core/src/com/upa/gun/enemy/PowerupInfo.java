package com.upa.gun.enemy;

import java.util.Map;

public class PowerupInfo {

    public int id;
    public String name;
    public String sprite;
    public String description;
    public String effectDescription;
    public float damageMultiplier;
    public float speedMultiplier;
    public float bulletCooldownMultiplier;

    PowerupInfo(int id, String name, String sprite, String description, String effectDescription,
                float damageMultiplier, float speedMultiplier, float bulletCooldownMultiplier) {
        this.id = id;
        this.name = name;
        this.sprite = sprite;
        this.description = description;
        this.effectDescription = effectDescription;
        this.damageMultiplier = damageMultiplier;
        this.speedMultiplier = speedMultiplier;
        this.bulletCooldownMultiplier = bulletCooldownMultiplier;
    }


}
