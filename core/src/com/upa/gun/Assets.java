package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static Texture backgroundRoom1;

    public static TextureAtlas playerAtlas;
    public static Animation<TextureRegion> playerAnimation;

    public static Texture bullets;
    public static TextureRegion bulletBasic;

    public static BitmapFont menuFont;

    public static Texture loadTexture(String filepath) {
        return new Texture(Gdx.files.internal(filepath));
    }

    public static void load() {
        backgroundRoom1 = loadTexture("sprites/background1.png");

        playerAtlas = new TextureAtlas(Gdx.files.internal("sprites/player.atlas"));
        playerAnimation = new Animation<TextureRegion>(0.25f,
                Assets.playerAtlas.findRegions("playerFront"), Animation.PlayMode.LOOP);

        bullets = loadTexture("sprites/laserBullet.png");

        bulletBasic = new TextureRegion(bullets, 0, 0, 33, 14);

        menuFont = new BitmapFont();
    }
}