package com.upa.gun.enemy;

public class EnemyInfo {
    public int id;
    public int health;
    public String sprite;

    public EnemyInfo() {
        this.id = 0;
        this.health = 0;
        this.sprite = "";
    }

    public EnemyInfo(int id, int health, String sprite) {
        this.id = id;
        this.health = health;
        this.sprite = sprite;
    }
}
