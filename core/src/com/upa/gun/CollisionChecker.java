package com.upa.gun;

public class CollisionChecker implements Updatable {
    private void checkBulletsCollision() {
        for (Bullet b : World.bullets) {
            if (b.hitbox.colliding(World.player.hitbox)) {
                System.out.println("PLAYER DIE");
            }
        }
    }

    @Override
    public void update(float delta) {
        checkBulletsCollision();
    }
}
