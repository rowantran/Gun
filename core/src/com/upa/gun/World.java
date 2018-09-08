package com.upa.gun;

import java.util.ArrayList;
import java.util.List;

public class World {
    Player player;
    List<Bullet> bullets;

    World(Player player) {
        this.player = player;
        bullets = new ArrayList<Bullet>();
    }

    public void update(float delta) {
        player.update(delta);

        for (Bullet bullet : bullets) {
            bullet.update(delta);
        }
    }
}
