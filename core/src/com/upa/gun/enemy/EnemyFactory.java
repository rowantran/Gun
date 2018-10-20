package com.upa.gun.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.upa.gun.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnemyFactory {
    private Enemy prototype;
    private Map<String, EnemyInfo> enemies;

    static class EnemyInfoList {
        public ArrayList<EnemyInfo> enemyInfoList;

        EnemyInfoList() {
            enemyInfoList = new ArrayList<EnemyInfo>();
        }
        EnemyInfoList(ArrayList<EnemyInfo> list) {
            enemyInfoList = list;
        }
    }

    EnemyFactory(Enemy prototype) {
        this.prototype = prototype;
        enemies = new HashMap<String, EnemyInfo>();

        Json json = new Json();
        EnemyInfoList enemiesJson = json.fromJson(EnemyInfoList.class, Gdx.files.internal("enemies.json"));

        for (EnemyInfo info : enemiesJson.enemyInfoList) {
            enemies.put(Integer.toString(info.id), info);
        }
    }

    void spawn(float x, float y) {
        World.enemies.add(prototype.create(x, y));
    }
}
