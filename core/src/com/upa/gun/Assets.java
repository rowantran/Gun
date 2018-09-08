package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static Texture backgroundRoom1;

    public static Texture playerTexture;
    public static TextureRegion playerBasic;

    public static Texture bullets;
    public static TextureRegion bulletBasic;

    public static BitmapFont menuFont;

    public static Texture loadTexture(String filepath) {
        return new Texture(Gdx.files.internal(filepath));
    }

    public static void load() {
        backgroundRoom1 = loadTexture("sprites/background1.png");

        playerTexture = loadTexture("sprites/tempPlayer.png");

        playerBasic = new TextureRegion(playerTexture, 0, 0, 128,128);

        bullets = loadTexture("sprites/laserBullet.png");

        bulletBasic = new TextureRegion(bullets, 0, 0, 63, 50);

        menuFont = new BitmapFont();
    }
}