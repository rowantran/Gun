package com.upa.gun;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class RoomScreen1 {
    GunGame game;
    OrthographicCamera camera;

    public RoomScreen1(GunGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.RESOLUTION.x, Settings.RESOLUTION.y);
    }
}
