package com.upa.gun.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnemyFactory {
    Map<Integer, EnemyInfo> enemies;

    public static class EnemiesWrapper {
        public ArrayList<EnemyInfo> enemies;

        public EnemiesWrapper() {
            enemies = new ArrayList<EnemyInfo>();
        }
    }

    public EnemyFactory(String path) {
        enemies = new HashMap<Integer, EnemyInfo>();

        Json json = new Json();
        EnemiesWrapper enemiesWrapper = json.fromJson(EnemiesWrapper.class, Gdx.files.internal(path));

        for (EnemyInfo info : enemiesWrapper.enemies) {
            enemies.put(info.id, info);
        }

        System.out.println("Done loading enemies");
    }

    Enemy createEnemy(int id, int x, int y) {
        return new Enemy(enemies.get(id), x, y);
    }
}
