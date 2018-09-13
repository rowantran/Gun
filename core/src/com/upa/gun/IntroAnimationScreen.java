package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.naming.NamingEnumeration;

public class IntroAnimationScreen extends ScreenAdapter {
    GunGame game;
    OrthographicCamera camera;
    GlyphLayout layout;

    float textAlpha;

    static float PLAYER_RUN_SPEED = 300.0f;
    static float BOSS_RUN_SPEED = 450.0f;

    float timeElapsed;

    float playerX, playerY, bossX, bossY;

    boolean[] slimesStrong;

    public IntroAnimationScreen(GunGame game, float textAlpha) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.RESOLUTION.x, Settings.RESOLUTION.y);

        layout = new GlyphLayout();

        this.textAlpha = textAlpha;
        timeElapsed = 0f;

        TextureRegion playerIdleRightFrame = Assets.playerAnimations.get(ActionState.MOVING).get(Direction.RIGHT)
                .getKeyFrame(0);
        playerX = (Settings.RESOLUTION.x - playerIdleRightFrame.getRegionWidth()) / 2;
        playerY = (Settings.RESOLUTION.y - playerIdleRightFrame.getRegionHeight()) / 2;

        TextureRegion bossMoveFrame = Assets.bossSlimeAnimations.get(ActionState.MOVING).get(Direction.LEFT)
                .getKeyFrame(0);
        bossX = -200;
        bossY = (Settings.RESOLUTION.y - bossMoveFrame.getRegionHeight()) / 2 - 100;

        slimesStrong = new boolean[5];
        for (int i = 0; i < slimesStrong.length; i++) {
            int rand = (int) (Math.random() * 2);
            slimesStrong[i] = rand == 0;
        }
    }

    void draw() {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.enableBlending();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        Assets.menuFont.setColor(1,1,1, textAlpha);
        Assets.menuFont.getData().setScale(8);
        layout.setText(Assets.menuFont, "There Is No Gun.");
        Assets.menuFont.draw(game.batch, layout, (Settings.RESOLUTION.x - layout.width) / 2,
                (Settings.RESOLUTION.y*4/5 + layout.height*0.5f));
        Assets.menuFont.setColor(1,1,1,textAlpha);
        Assets.menuFont.getData().setScale(4);
        layout.setText(Assets.menuFont, "Press any key to start");
        Assets.menuFont.draw(game.batch, layout, (Settings.RESOLUTION.x - layout.width) / 2,
                (Settings.RESOLUTION.x/6));

        game.batch.end();

        TextureRegion playerCurrentFrame = Assets.playerAnimations.get(ActionState.MOVING).get(Direction.RIGHT).getKeyFrame(timeElapsed);
        TextureRegion bossCurrentFrame = Assets.bossSlimeAnimations.get(ActionState.MOVING).get(Direction.RIGHT).getKeyFrame(timeElapsed);
        bossCurrentFrame.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        TextureRegion slimeCurrentFrame;

        game.batch.begin();
        game.batch.draw(playerCurrentFrame, (int) playerX, (int) playerY,
                playerCurrentFrame.getRegionWidth(), playerCurrentFrame.getRegionHeight());
        game.batch.draw(bossCurrentFrame, (int) bossX, (int) bossY,
                bossCurrentFrame.getRegionWidth()*8, bossCurrentFrame.getRegionHeight()*8);
        float tempX = bossX - 100;
        for (int i = 0; i < 5; i++) {
            tempX -= 50;
            if (slimesStrong[i]) {
                slimeCurrentFrame = Assets.slimeAnimations.get(ActionState.MOVING).get(Direction.RIGHT).getKeyFrame(timeElapsed);
            } else {
                slimeCurrentFrame = Assets.strongSlimeAnimations.get(ActionState.MOVING).get(Direction.RIGHT).getKeyFrame(timeElapsed);
            }
            game.batch.draw(slimeCurrentFrame, (int) tempX, (int) bossY,
                    slimeCurrentFrame.getRegionWidth(), slimeCurrentFrame.getRegionHeight());
        }
        game.batch.end();
    }

    private void update(float delta) {
        textAlpha -= Settings.SLOW_FADE_SPEED * delta;

        timeElapsed += delta;

        playerX += PLAYER_RUN_SPEED * delta;
        bossX += BOSS_RUN_SPEED * delta;

        if (bossX > Settings.RESOLUTION.x + 400) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }
}
