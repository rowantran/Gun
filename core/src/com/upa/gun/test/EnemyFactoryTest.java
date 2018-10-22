package com.upa.gun.test;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.upa.gun.enemy.EnemyFactory;
import com.upa.gun.enemy.EnemyInfo;

public class EnemyFactoryTest {
    public static void main(String[] args) {
        EnemyFactory.EnemiesWrapper factory = new EnemyFactory.EnemiesWrapper();

        EnemyInfo info1 = new EnemyInfo();
        info1.id = 0;
        info1.health = 10;
        info1.hitboxType = "rectangular";
        info1.hitboxWidth = 10;
        info1.hitboxHeight = 10;
        info1.width = 36;
        info1.height = 36;
        info1.sprite = "enemy1";
        factory.enemies.add(info1);

        EnemyInfo info2 = new EnemyInfo();
        info2.id = 1;
        info2.health = 100;
        info2.hitboxType = "rectangular";
        info2.hitboxWidth = 10;
        info2.hitboxHeight = 10;
        info2.width = 36;
        info2.height = 36;
        info2.sprite = "enemy2";
        factory.enemies.add(info2);

        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        System.out.println(json.prettyPrint(factory));
    }
}
