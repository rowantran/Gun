package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class Assets {
    public static Texture backgroundRoom1;
    public static Texture crate;
    public static Texture shadow;

    public static TextureAtlas playerAtlas;
    public static Map<SpriteState, Map<Direction, Animation<TextureRegion>>> playerAnimations;

    public static TextureAtlas slimeAtlas;
    public static Map<SpriteState, Map<Direction, Animation<TextureRegion>>> slimeAnimations;

    public static TextureAtlas strongSlimeAtlas;
    public static Map<SpriteState, Map<Direction, Animation<TextureRegion>>> strongSlimeAnimations;

    public static TextureAtlas bossSlimeAtlas;
    public static Map<SpriteState, Map<Direction, Animation<TextureRegion>>> bossSlimeAnimations;

    public static Texture bullets;
    public static TextureRegion bulletLaser;
    public static Texture bulletsEnemies;
    public static TextureRegion bulletEnemy;
    public static Texture bulletsEnemiesBoss;
    public static TextureRegion bulletBoss;

    public static BitmapFont menuFont;

    public Pixmap pm;
    public static Texture crosshair;

    public static Sound bulletSound;
    public static Sound bossDieSound;

    public static Texture healthFullLeft;
    public static Texture healthFullRight;
    public static Texture healthFullMid;
    public static Texture healthEmptyLeft;
    public static Texture healthEmptyRight;
    public static Texture healthEmptyMid;

    public static Texture loadTexture(String filepath) {
        return new Texture(Gdx.files.internal(filepath));
    }

    public static void load() {
        backgroundRoom1 = loadTexture("sprites/background1.png");
        crate = loadTexture("sprites/crate.png");
        shadow = loadTexture("sprites/shadow.png");
        shadow.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        playerAtlas = new TextureAtlas(Gdx.files.internal("sprites/player.atlas"));
        playerAnimations = new HashMap<SpriteState, Map<Direction, Animation<TextureRegion>>>();
        loadPlayerAnimations();

        crosshair = new Texture("sprites/crosshair.png");
        crosshair.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        /*Pixmap pm = new Pixmap(Gdx.files.internal("sprites/crosshair.png"));
        int xHotSpot = pm.getWidth() / 2;
        int yHotSpot = pm.getHeight() / 2;

        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot));
        pm.dispose();
    */

        slimeAtlas = new TextureAtlas(Gdx.files.internal("sprites/slime.atlas"));
        slimeAnimations = new HashMap<SpriteState, Map<Direction, Animation<TextureRegion>>>();
        loadSlimeAnimations();

        strongSlimeAtlas = new TextureAtlas(Gdx.files.internal("sprites/strongSlime.atlas"));
        strongSlimeAnimations = new HashMap<SpriteState, Map<Direction, Animation<TextureRegion>>>();
        loadStrongSlimeAnimations();

        bossSlimeAtlas = new TextureAtlas(Gdx.files.internal("sprites/bossSlime.atlas"));
        bossSlimeAnimations = new HashMap<SpriteState, Map<Direction, Animation<TextureRegion>>>();
        loadBossSlimeAnimations();

        bullets = loadTexture("sprites/laserBullet.png");

        bulletLaser = new TextureRegion(bullets, 0, 0, 33, 14);

        bulletsEnemies = loadTexture("sprites/slimePellet.png");
        bulletEnemy = new TextureRegion(bulletsEnemies, 0, 0, 13, 16);

        bulletsEnemiesBoss = loadTexture("sprites/bossPellet.png");
        bulletBoss = new TextureRegion(bulletsEnemiesBoss, 0, 0,13,16);

        menuFont = new BitmapFont();

        healthFullLeft = loadTexture("sprites/health_full_left.png");
        healthFullLeft.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        healthFullRight = loadTexture("sprites/health_full_right.png");
        healthFullRight.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        healthFullMid = loadTexture("sprites/health_full_mid.png");
        healthFullMid.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        healthEmptyLeft = loadTexture("sprites/health_empty_left.png");
        healthEmptyLeft.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        healthEmptyRight = loadTexture("sprites/health_empty_right.png");
        healthFullRight.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        healthEmptyMid = loadTexture("sprites/health_empty_mid.png");
        healthEmptyMid.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sfx/gunshot.mp3"));
        bossDieSound = Gdx.audio.newSound(Gdx.files.internal("sfx/bossdie.wav"));
    }

    public static Vector2 getTextureSize(Map<SpriteState, Map<Direction, Animation<TextureRegion>>> map) {
        TextureRegion frame = map.get(SpriteState.IDLE).get(Direction.DOWN).getKeyFrame(0);
        return new Vector2(frame.getRegionWidth(), frame.getRegionHeight());
    }

    private static Animation<TextureRegion> loadPlayerAnimation(String direction) {
        return new Animation<TextureRegion>(0.25f,
                playerAtlas.findRegions("player" + direction), Animation.PlayMode.LOOP);
    }

    private static Animation<TextureRegion> loadPlayerIdleAnimation(String direction) {
        return loadPlayerAnimation(direction + "-idle");
    }

    private static void loadPlayerAnimations() {
        Map<Direction, Animation<TextureRegion>> playerMovingAnimations =
                new HashMap<Direction, Animation<TextureRegion>>();
        playerMovingAnimations.put(Direction.DOWN, loadPlayerAnimation("Front"));
        playerMovingAnimations.put(Direction.UP, loadPlayerAnimation("Back"));
        playerMovingAnimations.put(Direction.LEFT, loadPlayerAnimation("Left"));
        playerMovingAnimations.put(Direction.RIGHT, loadPlayerAnimation("Right"));
        playerAnimations.put(SpriteState.MOVING, playerMovingAnimations);

        Map<Direction, Animation<TextureRegion>> playerIdleAnimations =
                new HashMap<Direction, Animation<TextureRegion>>();
        playerIdleAnimations.put(Direction.DOWN, loadPlayerIdleAnimation("Front"));
        playerIdleAnimations.put(Direction.UP, loadPlayerIdleAnimation("Back"));
        playerIdleAnimations.put(Direction.LEFT, loadPlayerIdleAnimation("Left"));
        playerIdleAnimations.put(Direction.RIGHT, loadPlayerIdleAnimation("Right"));
        playerAnimations.put(SpriteState.IDLE, playerIdleAnimations);
    }

    private static Animation<TextureRegion> loadSlimeAnimation(String name) {
        return new Animation<TextureRegion>(0.25f, slimeAtlas.findRegions(name),
                Animation.PlayMode.LOOP);
    }

    private static Animation<TextureRegion> loadSlimeAnimationFlipped(String name) {
        Array<TextureAtlas.AtlasRegion> frames = slimeAtlas.findRegions(name);
        for (TextureAtlas.AtlasRegion frame : frames) {
            frame.flip(true, false);
        }

        return new Animation<TextureRegion>(0.25f, frames, Animation.PlayMode.LOOP);
    }

    private static void loadSlimeAnimations() {
        Map<Direction, Animation<TextureRegion>> slimeMovingAnimations = new HashMap<Direction, Animation<TextureRegion>>();
        slimeMovingAnimations.put(Direction.LEFT, loadSlimeAnimation("slime"));
        slimeMovingAnimations.put(Direction.RIGHT, loadSlimeAnimationFlipped("slime"));
        slimeAnimations.put(SpriteState.MOVING, slimeMovingAnimations);

        Map<Direction, Animation<TextureRegion>> slimeAttackAnimations = new HashMap<Direction, Animation<TextureRegion>>();
        slimeAttackAnimations.put(Direction.LEFT, loadSlimeAnimation("slimeAttack"));
        slimeAttackAnimations.put(Direction.RIGHT, loadSlimeAnimationFlipped("slimeAttack"));
        slimeAnimations.put(SpriteState.ATTACKING, slimeAttackAnimations);

        Map<Direction, Animation<TextureRegion>> slimeHurtAnimations = new HashMap<Direction, Animation<TextureRegion>>();
        slimeHurtAnimations.put(Direction.LEFT, loadSlimeAnimation("slime-death"));
        slimeHurtAnimations.put(Direction.RIGHT, loadSlimeAnimationFlipped("slime-death"));
        slimeAnimations.put(SpriteState.HURT, slimeHurtAnimations);
    }

    private static Animation<TextureRegion> loadStrongSlimeAnimation(String name) {
        return new Animation<TextureRegion>(0.25f, strongSlimeAtlas.findRegions(name),
                Animation.PlayMode.LOOP);
    }

    private static Animation<TextureRegion> loadStrongSlimeAnimationFlipped(String name) {
        Array<TextureAtlas.AtlasRegion> frames = strongSlimeAtlas.findRegions(name);
        for (TextureAtlas.AtlasRegion frame : frames) {
            frame.flip(true, false);
        }

        return new Animation<TextureRegion>(0.25f, frames, Animation.PlayMode.LOOP);
    }

    private static void loadStrongSlimeAnimations() {
        Map<Direction, Animation<TextureRegion>> strongSlimeMovingAnimations = new HashMap<Direction, Animation<TextureRegion>>();
        strongSlimeMovingAnimations.put(Direction.LEFT, loadStrongSlimeAnimation("strongSlime"));
        strongSlimeMovingAnimations.put(Direction.RIGHT, loadStrongSlimeAnimationFlipped("strongSlime"));
        strongSlimeAnimations.put(SpriteState.MOVING, strongSlimeMovingAnimations);

        Map<Direction, Animation<TextureRegion>> strongSlimeAttackingAnimations = new HashMap<Direction, Animation<TextureRegion>>();
        strongSlimeAttackingAnimations.put(Direction.LEFT, loadStrongSlimeAnimation("strongSlimeAttack"));
        strongSlimeAttackingAnimations.put(Direction.RIGHT, loadStrongSlimeAnimationFlipped("strongSlimeAttack"));
        strongSlimeAnimations.put(SpriteState.ATTACKING, strongSlimeAttackingAnimations);

        Map<Direction, Animation<TextureRegion>> strongSlimeHurtAnimations = new HashMap<Direction, Animation<TextureRegion>>();
        strongSlimeHurtAnimations.put(Direction.LEFT, loadStrongSlimeAnimation("strongSlime-death"));
        strongSlimeHurtAnimations.put(Direction.RIGHT, loadStrongSlimeAnimationFlipped("strongSlime-death"));
        strongSlimeAnimations.put(SpriteState.HURT, strongSlimeHurtAnimations);
    }

    private static Animation<TextureRegion> loadBossSlimeAnimation(String name) {
        return new Animation<TextureRegion>(0.25f, bossSlimeAtlas.findRegions(name),
                Animation.PlayMode.LOOP);
    }

    private static Animation<TextureRegion> loadBossSlimeAnimationFlipped(String name) {
        Array<TextureAtlas.AtlasRegion> frames = bossSlimeAtlas.findRegions(name);
        for (TextureAtlas.AtlasRegion frame : frames) {
            frame.flip(true, false);
        }

        return new Animation<TextureRegion>(0.25f, frames, Animation.PlayMode.LOOP);
    }

    private static void loadBossSlimeAnimations() {
        Map<Direction, Animation<TextureRegion>> bossSlimeMovingAnimations = new HashMap<Direction, Animation<TextureRegion>>();
        bossSlimeMovingAnimations.put(Direction.LEFT, loadBossSlimeAnimation("bossSlime"));
        bossSlimeMovingAnimations.put(Direction.RIGHT, loadBossSlimeAnimationFlipped("bossSlime"));
        bossSlimeAnimations.put(SpriteState.MOVING, bossSlimeMovingAnimations);

        Map<Direction, Animation<TextureRegion>> bossSlimeAttackingAnimations = new HashMap<Direction, Animation<TextureRegion>>();
        bossSlimeAttackingAnimations.put(Direction.LEFT, loadBossSlimeAnimation("bossSlimeAttack"));
        bossSlimeAttackingAnimations.put(Direction.RIGHT, loadBossSlimeAnimationFlipped("bossSlimeAttack"));
        bossSlimeAnimations.put(SpriteState.ATTACKING, bossSlimeAttackingAnimations);

        Map<Direction, Animation<TextureRegion>> bossSlimeHurtAnimations = new HashMap<Direction, Animation<TextureRegion>>();
        bossSlimeHurtAnimations.put(Direction.LEFT, loadBossSlimeAnimation("bossSlime-hurt"));
        bossSlimeHurtAnimations.put(Direction.RIGHT, loadBossSlimeAnimationFlipped("bossSlime-hurt"));
        bossSlimeAnimations.put(SpriteState.HURT, bossSlimeHurtAnimations);
    }
}