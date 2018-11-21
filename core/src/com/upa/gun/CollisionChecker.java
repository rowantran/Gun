package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.upa.gun.enemy.Enemy;

public class CollisionChecker implements Updatable {
    private void checkPlayerHit() {
        for (Bullet b : World.enemyBullets) {
            if (b.hitbox.colliding(World.player.hitbox)) {
                World.player.hurt(1); //will leave dying state when other condition occurs - needs fix
            }
        }
    }

    private void checkEnemiesHit() {
        for (Bullet b : World.playerBullets) {
            for (Enemy e: World.enemies) {
                if (b.hitbox.colliding(e.getHitbox())) {
                    Gdx.app.debug("CollisionChecker", "Enemy hit by bullet");
                    e.damage(1);
                    b.markedForDeletion = true;
                }
            }
        }
    }

    @Override
    public void update(float delta) {
        checkPlayerHit();
        checkEnemiesHit();
    }
}
