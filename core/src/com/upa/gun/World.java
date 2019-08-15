package com.upa.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.upa.gun.cutscene.ScriptedEventSequence;
import com.upa.gun.enemy.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class World implements Updatable {
    private static World world = new World();

    public static Player player;

    public static List<Enemy> enemies;
    public static List<Powerup> powerups;
    public static List<Bullet> enemyBullets;
    static List<Bullet> playerBullets;
    public static List<SpawnIndicator> indicators;


    public static ArrayList<Entity> oldEntities;

    public static List<ScriptedEventSequence> sequences;

    public static Spawner spawner;

    public static CollisionChecker collisionChecker;

    boolean cinematicHappening;
    public static int activity;

    public static boolean waveActive;

    public static boolean doorsOpen;
    public static int roomChange;
    private static float timer;
    private static float distanceMoved;
    private static float distanceMoved2;

    private static EnemyFactory enemyFactory;
    private static PowerupFactory powerupFactory;
    private static MapFactory mapFactory;
    private static WaveFactory waveFactory;

    public static MapLayout currentMap;
    public static MapLayout[][] fullMap;
    public static Wave currentWave;

    public static int mapX;
    public static int mapY;

    private World() {
        enemies = new ArrayList<Enemy>();
        powerups = new ArrayList<Powerup>();

        enemyBullets = new ArrayList<Bullet>();
        playerBullets = new ArrayList<Bullet>();

        indicators = new ArrayList<SpawnIndicator>();

        sequences = new ArrayList<ScriptedEventSequence>();
        activity = 0;
        roomChange = 0;

        spawner = new Spawner(this);

        collisionChecker = new CollisionChecker();

        enemyFactory = new EnemyFactory("enemies.json");
        powerupFactory = new PowerupFactory("powerups.json");
        mapFactory = new MapFactory("maps.json");
        waveFactory = new WaveFactory("waves.json");

        fullMap = new MapLayout[3][3];

        int m = 0;
        for(int i = 0; i < fullMap.length; i++) {
            for(int j = 0; j < fullMap[i].length; j++) {
                fullMap[i][j] = mapFactory.createMap(m);
                m++;
            }
        }

        currentMap = fullMap[0][0];
        mapX = 0;
        mapY = 0;

        currentWave = waveFactory.createWave(0);

        doorsOpen = false; //temp

    }

    public void reset() {
        player.reset();

        enemies.clear();
        powerups.clear();

        enemyBullets.clear();
        playerBullets.clear();

        indicators.clear();

        sequences.clear();

        spawner.reset();
    }

    public static World getInstance() {
        return world;
    }

    void setGunGame(GunGame game) {
        player = new Player(new Vector2(200, 200), game);
    }

    @Override
    public void update(float delta) {
        cinematicHappening = false;
        for (ScriptedEventSequence sequence : sequences) {
            sequence.update(delta);
            if (sequence.isCinematic() && sequence.isActive()) {
                cinematicHappening = true;
            }
        }

        if (!cinematicHappening) {

            switch(activity) {

                case 0:
                    switch (roomChange) {
                        case 1:
                            if (distanceMoved < (float) Assets.floor.getHeight()) {
                                player.setVelocity(0f, Assets.floor.getHeight() / -Settings.ROOM_CHANGE_TIME);
                                if (distanceMoved - player.getVelocity().y * delta > (float) Assets.floor.getHeight()) {
                                    player.setVelocity(0f, (Assets.floor.getHeight() - distanceMoved) / -delta);
                                }
                                basicMoveEntities(delta, player.velocity);
                                distanceMoved -= player.getVelocity().y * delta;
                            } else if (distanceMoved2 < Settings.ROOM_CHANGE_STEP) {
                                player.setVelocity(0f, Settings.ROOM_CHANGE_STEP_SPEED);
                                if (distanceMoved2 + player.getVelocity().y * delta > Settings.ROOM_CHANGE_STEP) {
                                    player.setVelocity(0f, (Settings.ROOM_CHANGE_STEP - distanceMoved2) / delta);
                                }
                                player.basicUpdate(delta);
                                freezeObjects(delta);
                                distanceMoved2 += player.getVelocity().y * delta;
                            } else {
                                basicMoveEntities(delta, new Vector2(0f, 0f));
                            }

                            timer += delta;
                            if (timer >= Settings.ROOM_CHANGE_TIME + Settings.ROOM_CHANGE_TIME_BUFFER) {
                                cleanRoom();
                            }
                            break;
                        case 2:
                            if (distanceMoved < (float) Assets.floor.getHeight()) {
                                player.setVelocity(0f, Assets.floor.getHeight() / Settings.ROOM_CHANGE_TIME);
                                if (distanceMoved + player.getVelocity().y * delta > (float) Assets.floor.getHeight()) {
                                    player.setVelocity(0f, (Assets.floor.getHeight() - distanceMoved) / delta);
                                }
                                basicMoveEntities(delta, player.velocity);
                                distanceMoved += player.getVelocity().y * delta;
                            } else if (distanceMoved2 < Settings.ROOM_CHANGE_STEP) {
                                player.setVelocity(0f, -Settings.ROOM_CHANGE_STEP_SPEED);
                                if (distanceMoved2 - player.getVelocity().y * delta > Settings.ROOM_CHANGE_STEP) {
                                    player.setVelocity(0f, (Settings.ROOM_CHANGE_STEP - distanceMoved2) / -delta);
                                }
                                player.basicUpdate(delta);
                                freezeObjects(delta);
                                distanceMoved2 -= player.getVelocity().y * delta;
                            } else {
                                basicMoveEntities(delta, new Vector2(0f, 0f));
                            }

                            timer += delta;
                            if (timer >= Settings.ROOM_CHANGE_TIME + Settings.ROOM_CHANGE_TIME_BUFFER) {
                                cleanRoom();
                            }
                            break;
                        case 3:
                            if (distanceMoved < (float) Assets.floor.getWidth()) {
                                player.setVelocity(Assets.floor.getWidth() / Settings.ROOM_CHANGE_TIME, 0f);
                                if (distanceMoved + player.getVelocity().x * delta > (float) Assets.floor.getWidth()) {
                                    player.setVelocity((Assets.floor.getWidth() - distanceMoved) / delta, 0f);
                                }
                                basicMoveEntities(delta, player.velocity);
                                distanceMoved += player.getVelocity().x * delta;
                            } else if (distanceMoved2 < Settings.ROOM_CHANGE_STEP) {
                                player.setVelocity(-Settings.ROOM_CHANGE_STEP_SPEED, 0f);
                                if (distanceMoved2 - player.getVelocity().x * delta > Settings.ROOM_CHANGE_STEP) {
                                    player.setVelocity((Settings.ROOM_CHANGE_STEP - distanceMoved2) / -delta, 0f);
                                }
                                player.basicUpdate(delta);
                                freezeObjects(delta);
                                distanceMoved2 -= player.getVelocity().y * delta;
                            } else {
                                basicMoveEntities(delta, new Vector2(0f, 0f));
                            }

                            timer += delta;
                            if (timer >= Settings.ROOM_CHANGE_TIME + Settings.ROOM_CHANGE_TIME_BUFFER) {
                                cleanRoom();
                            }
                            break;
                        case 4:
                            if (distanceMoved < (float) Assets.floor.getWidth()) {
                                player.setVelocity(Assets.floor.getWidth() / -Settings.ROOM_CHANGE_TIME, 0f);
                                if (distanceMoved - player.getVelocity().x * delta > (float) Assets.floor.getWidth()) {
                                    player.setVelocity((Assets.floor.getWidth() - distanceMoved) / -delta, 0f);
                                }
                                basicMoveEntities(delta, player.velocity);
                                distanceMoved -= player.getVelocity().x * delta;
                            } else if (distanceMoved2 < Settings.ROOM_CHANGE_STEP) {
                                player.setVelocity(Settings.ROOM_CHANGE_STEP_SPEED, 0f);
                                if (distanceMoved2 + player.getVelocity().x * delta > Settings.ROOM_CHANGE_STEP) {
                                    player.setVelocity((Settings.ROOM_CHANGE_STEP - distanceMoved2) / delta, 0f);
                                }
                                player.basicUpdate(delta);
                                freezeObjects(delta);
                                distanceMoved2 += player.getVelocity().y * delta;
                            } else {
                                basicMoveEntities(delta, new Vector2(0f, 0f));
                            }

                            timer += delta;
                            if (timer >= Settings.ROOM_CHANGE_TIME + Settings.ROOM_CHANGE_TIME_BUFFER) {
                                cleanRoom();
                            }
                            break;
                        default:
                            collisionChecker.update(delta);
                            player.update(delta);

                            for (Bullet bullet : playerBullets) {
                                bullet.update(delta);
                            }
                            for (Bullet bullet : enemyBullets) {
                                bullet.update(delta);
                            }
                            for (Enemy enemy : enemies) {
                                enemy.update(delta);
                            }

                            if (waveActive) {
                                currentWave.update(delta);
                            } else if (enemies.size() == 0 && indicators.size() == 0) {
                                doorsOpen = true;
                            }

                            for (SpawnIndicator spawn : indicators) {
                                spawn.update(delta);
                                if (spawn.shouldSpawn()) {
                                    enemies.add(spawn.createSpawn());
                                    spawn.markedForDeletion = true;
                                }
                            }
                            player.inputHandler.pausedUpdate(delta);
                            break;
                    }
                    break;
                case 1:
                    player.inputHandler.pausedUpdate(delta);
                    break;
                default:
                    Gdx.app.log("World", "Found invalid activity identifier");
                    break;
            }
        }
    }

    private void freezeObjects(float delta) {
        for(Entity e : oldEntities) {
            e.setVelocity(0f, 0f);
            e.update(delta);
        }
        for(Entity e : currentMap.getCrates()) {
            e.setVelocity(0f, 0f);
            e.update(delta);
        }
        for(Entity e : currentMap.getDoors()) {
            e.setVelocity(0f, 0f);
            e.update(delta);
        }
    }

    private void basicMoveEntities(float delta, Vector2 velocity) {
        for(Entity e : oldEntities) {
            e.setVelocity(velocity);
            e.basicUpdate(delta);
        }
        for(Entity e : currentMap.getDoors()) {
            e.setVelocity(velocity);
            e.basicUpdate(delta);
        }
        for(Entity e : currentMap.getCrates()) {
            e.setVelocity(velocity);
            e.basicUpdate(delta);
        }
        player.setVelocity(velocity);
        player.basicUpdate(delta);
    }

    public static void resetTimer() {
        timer = 0f;
    }

    public static void moveAllEntities() {
        oldEntities = new ArrayList<Entity>();
        oldEntities.addAll(enemyBullets);
        oldEntities.addAll(playerBullets);
        oldEntities.addAll(enemies);
        oldEntities.addAll(powerups);
        oldEntities.addAll(currentMap.getCrates());
        oldEntities.addAll(currentMap.getDoors());


        enemyBullets.clear();
        playerBullets.clear();
        enemies.clear();
        powerups.clear();
    }

    private static void cleanRoom() {
        roomChange = 0;
        oldEntities.clear();
        doorsOpen = false;
        currentWave = waveFactory.createWave(0);
        waveActive = true;
        for(Crate c : currentMap.getCrates()) {
            c.resetPosition();
        }
        for(Door d : currentMap.getDoors()) {
            d.resetPosition();
        }
    }

    public static void adjustNewMap() {

        distanceMoved = 0f;
        distanceMoved2 = 0f;

        float adjustX = 0f;
        float adjustY = 0f;

        switch(roomChange) {
            case 1:
                adjustY = (float)Assets.floor.getHeight();
                break;
            case 2:
                adjustY = -(float)Assets.floor.getHeight();
                break;
            case 3:
                adjustX = -(float)Assets.floor.getWidth();
                break;
            case 4:
                adjustX = (float)Assets.floor.getWidth();
                break;
            default:
                Gdx.app.log("World", "Error attempting to adjust new map location");
                break;
        }
        for(Crate c : currentMap.getCrates()) {
            c.resetPosition();
            c.setPosition(c.getPosition().x + adjustX, c.getPosition().y + adjustY);
            c.getHitbox().setPosition(c.getPosition());
        }
        for(Door d : currentMap.getDoors()) {
            d.resetPosition();
            d.setPosition(d.getPosition().x + adjustX, d.getPosition().y + adjustY);
            d.getHitbox().setPosition(d.getPosition());
        }
    }

    void deleteMarkedForDeletion() {
        for (Iterator<Bullet> iterator = playerBullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            if (bullet.markedForDeletion) {
                iterator.remove();
            }
        }

        for (Iterator<Bullet> iterator = enemyBullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            if (bullet.markedForDeletion) {
                iterator.remove();
            }
        }

        for (Iterator<Powerup> iterator = powerups.iterator(); iterator.hasNext();) {
            Powerup powerup = iterator.next();
            if (powerup.markedForDeletion) {
                iterator.remove();
            }
        }

        ListIterator<Enemy> enemyIterator = enemies.listIterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            enemy.getState().iterationUpdate(enemyIterator);
        }

        for (Iterator<SpawnIndicator> iterator = indicators.iterator(); iterator.hasNext();) {
            SpawnIndicator spawn = iterator.next();
            if (spawn.markedForDeletion) {
                iterator.remove();
            }
        }
    }

    public EnemyFactory getEnemyFactory() {
        return enemyFactory;
    }
    public PowerupFactory getPowerupFactory() { return powerupFactory;}
}
