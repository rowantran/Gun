package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class MenuScreen extends ScreenAdapter {
    GunGame game;
    OrthographicCamera camera;
    GlyphLayout layout;

    float textAlpha;
    boolean fading;

    public MenuScreen(GunGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.RESOLUTION.x, Settings.RESOLUTION.y);

        layout = new GlyphLayout();

        textAlpha = 1.0f;
        fading = true;
    }

    public void draw() {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.enableBlending();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        Assets.menuFont.setColor(1,1,1,1);
        Assets.menuFont.getData().setScale(8);
        layout.setText(Assets.menuFont, "Gun");
        Assets.menuFont.draw(game.batch, layout, (Settings.RESOLUTION.x - layout.width) / 2,
                (Settings.RESOLUTION.y*4/5 + layout.height*0.5f));
        game.batch.end();

        game.batch.begin();
        Assets.menuFont.setColor(1,1,1,textAlpha);
        Assets.menuFont.getData().setScale(4);
        layout.setText(Assets.menuFont, "Press any key to start");
        Assets.menuFont.draw(game.batch, layout, (Settings.RESOLUTION.x - layout.width) / 2,
                (Settings.RESOLUTION.x/6));

        game.batch.end();
    }

    private void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new GameScreen(game));
        }

        if (fading) {
            textAlpha -= Settings.DEATH_FADE_SPEED * delta;
            if (textAlpha <= 0.0f) {
                fading = false;
            }
        } else {
            textAlpha += Settings.DEATH_FADE_SPEED * delta;
            if (textAlpha >= 1.0f) {
                fading = true;
            }
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }
}
