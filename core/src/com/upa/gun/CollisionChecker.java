package com.upa.gun;

public class CollisionChecker implements Updatable {
    private void checkBulletsCollision() {
        for (Bullet b : World.bullets) {
            if (b.hitbox.colliding(World.player.hitbox)) {
                World.player.state = World.player.state.dying; //will leave dying state when other condition occurs - needs fix
            }
        }
    }

    @Override
    public void update(float delta) {
        checkBulletsCollision();
    }
}
