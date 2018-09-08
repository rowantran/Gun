package com.upa.gun;

import java.util.ArrayList;
import java.util.Iterator;
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

        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            bullet.update(delta);
            if (player.bounds.overlaps(bullet.getBoundingRectangle())) {
                Assets.playerIdleSprite.setX(player.position.x);
                Assets.playerIdleSprite.setY(player.position.y);
                player.dying = true;
                iterator.remove();
            }
        }
    }
}
