package com.upa.gun;

public class CollisionChecker implements Updatable {
    private GunWorld world;

    CollisionChecker() {
        world = GunWorld.getInstance();
    }

    public void checkBulletsCollision() {
        for (Bullet b : world.bullets) {
            if (b.hitbox.colliding(world.player.hitbox)) {
               //  player die
            }
        }
    }

    @Override
    public void update(float delta) {

    }
}
