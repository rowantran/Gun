package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static Texture bullets;
    public static TextureRegion bulletBasic;

    public static BitmapFont menuFont;

    public static Texture loadTexture(String filepath) {
        return new Texture(Gdx.files.internal(filepath));
    }

    public static void load() {
        bullets = loadTexture("sprites/normyBullet.png");

        bulletBasic = new TextureRegion(bullets, 0, 0, 64, 64);

        menuFont = new BitmapFont();
    }
}