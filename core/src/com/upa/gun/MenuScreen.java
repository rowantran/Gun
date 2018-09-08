package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class MenuScreen extends ScreenAdapter {
    GunGame game;
    OrthographicCamera camera;
    GlyphLayout layout;

    public MenuScreen(GunGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.RESOLUTION.x, Settings.RESOLUTION.y);

        layout = new GlyphLayout();

        Assets.menuFont.getData().setScale(8);
    }

    public void draw() {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.enableBlending();
        game.batch.begin();
        layout.setText(Assets.menuFont, "Gun");
        Assets.menuFont.draw(game.batch, layout, (Settings.RESOLUTION.x - layout.width) / 2,
                (Settings.RESOLUTION.y/2 + layout.height*0.5f));
        game.batch.end();
    }

    private void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new RoomScreen1(game));
        }
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }
}
