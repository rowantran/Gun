package com.upa.gun;

import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GunWorld {
    Player player;
    List<Bullet> bullets;
    List<Enemy> enemies;
    World world;

    GunWorld(Player player, World world) {
        this.player = player;
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();

        this.world = world;
    }

    public void update(float delta) {
        player.update(delta);

        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            bullet.update(delta);
        }

        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update(delta);
        }
    }

    public void updatePostPhysics(float delta) {
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            if (bullet.markedForDeletion) {
                world.destroyBody(bullet.body);
                iterator.remove();
            }
        }
    }
}
