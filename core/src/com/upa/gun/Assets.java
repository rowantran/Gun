package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

import java.util.ArrayList;
import java.util.List;

public class Assets {
    public static Texture backgroundRoom1;

    public static TextureAtlas playerAtlas;
    public static List<Animation<TextureRegion>> playerAnimations;
    public static Sprite[] playerIdleSprites;

    public static TextureAtlas slimeAtlas;
    public static List<Animation<TextureRegion>> slimeMovementAnimations;
    public static List<Animation<TextureRegion>> slimeAttackAnimations;
    public static Sprite slimeDeathSprite;

    public static TextureAtlas strongSlimeAtlas;
    public static List<Animation<TextureRegion>> strongSlimeMovementAnimations;
    public static List<Animation<TextureRegion>> strongSlimeAttackAnimations;
    public static Sprite strongSlimeDeathSprite;

    public static Texture bullets;
    public static TextureRegion bulletLaser;
    public static Texture bulletsEnemies;
    public static TextureRegion bulletEnemy;

    public static BitmapFont menuFont;

    public Pixmap pm;
    public static Texture crosshair;

    public static Texture loadTexture(String filepath) {
        return new Texture(Gdx.files.internal(filepath));
    }

    public static void load() {
        backgroundRoom1 = loadTexture("sprites/background1.png");

        playerAtlas = new TextureAtlas(Gdx.files.internal("sprites/player.atlas"));
        playerAnimations = new ArrayList<Animation<TextureRegion>>();
        loadPlayerAnimations("Front");
        loadPlayerAnimations("Back");
        loadPlayerAnimations("Left");
        loadPlayerAnimations("Right");

    /*
        crosshair = new Texture("sprites/crosshair.png");
        Pixmap pm = new Pixmap(Gdx.files.internal("sprites/crosshair.png"));
        int xHotSpot = pm.getWidth() / 2;
        int yHotSpot = pm.getHeight() / 2;

        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot));
        pm.dispose();
    */

        playerIdleSprites = new Sprite[4];
        loadPlayerIdleSprite(Player.FRONT, "Front");
        loadPlayerIdleSprite(Player.BACK, "Back");
        loadPlayerIdleSprite(Player.LEFT, "Left");
        loadPlayerIdleSprite(Player.RIGHT, "Right");

        slimeAtlas = new TextureAtlas(Gdx.files.internal("sprites/slime.atlas"));
        slimeMovementAnimations = new ArrayList<Animation<TextureRegion>>();
        slimeAttackAnimations = new ArrayList<Animation<TextureRegion>>();
        loadSlimeMovementAnimations();
        loadSlimeAttackAnimations();

        slimeDeathSprite = new Sprite(Assets.slimeAtlas.findRegion("slime-death"));

        strongSlimeAtlas = new TextureAtlas(Gdx.files.internal("sprites/strongSlime.atlas"));
        strongSlimeMovementAnimations = new ArrayList<Animation<TextureRegion>>();
        strongSlimeAttackAnimations = new ArrayList<Animation<TextureRegion>>();
        loadStrongSlimeMovementAnimations();
        loadStrongSlimeAttackAnimations();

        strongSlimeDeathSprite = new Sprite(strongSlimeAtlas.findRegion("strongSlime-death"));

        bullets = loadTexture("sprites/laserBullet.png");

        bulletLaser = new TextureRegion(bullets, 0, 0, 33, 14);

        bulletsEnemies = loadTexture("sprites/slimePellet.png");
        bulletEnemy = new TextureRegion(bulletsEnemies, 0, 0, 13, 16);

        menuFont = new BitmapFont();
    }

    private static void loadPlayerAnimations(String direction) {
        playerAnimations.add(new Animation<TextureRegion>(0.25f,
                Assets.playerAtlas.findRegions("player" + direction), Animation.PlayMode.LOOP));
    }

    private static void loadPlayerIdleSprite(int index, String direction) {
        playerIdleSprites[index] = new Sprite(Assets.playerAtlas.findRegion("player" + direction + "-idle"));
    }

    private static void loadSlimeMovementAnimations() {
        slimeMovementAnimations.add(new Animation<TextureRegion>(0.25f,
                Assets.slimeAtlas.findRegions("slime"), Animation.PlayMode.LOOP));
    }
    private static void loadSlimeAttackAnimations() {
        slimeAttackAnimations.add(new Animation<TextureRegion>(0.25f,
                Assets.slimeAtlas.findRegions("slimeAttack"), Animation.PlayMode.LOOP));
    }
    private static void loadStrongSlimeMovementAnimations() {
        strongSlimeMovementAnimations.add(new Animation<TextureRegion>(0.25f,
                Assets.strongSlimeAtlas.findRegions("strongSlime"), Animation.PlayMode.LOOP));
    }
    private static void loadStrongSlimeAttackAnimations() {
        strongSlimeAttackAnimations.add(new Animation<TextureRegion>(0.25f,
                Assets.strongSlimeAtlas.findRegions("strongSlimeAttack"), Animation.PlayMode.LOOP));
    }
}