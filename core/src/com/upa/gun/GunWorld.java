package com.upa.gun;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GunWorld {
    Player player;
    Enemy slime;
    List<Bullet> bullets;

    GunWorld(Player player) {
        this.player = player;
        bullets = new ArrayList<Bullet>();
    }

    public void update(float delta) {
        player.update(delta);

        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            bullet.update(delta);
        }
    }
}
