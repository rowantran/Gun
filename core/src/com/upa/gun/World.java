package com.upa.gun;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

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
            if (Intersector.overlapConvexPolygons(bullet.hitbox, player.hitbox)) {
                Assets.playerIdleSprites[player.rotation].setX(player.position.x);
                Assets.playerIdleSprites[player.rotation].setY(player.position.y);
                player.dying = true;
                iterator.remove();
            }
        }
    }
}
