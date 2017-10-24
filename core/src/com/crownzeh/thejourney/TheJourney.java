package com.crownzeh.thejourney;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.crownzeh.thejourney.screens.MenuScreen;
import com.crownzeh.thejourney.world.Level;

import java.util.ArrayList;
import java.util.List;


public class TheJourney extends Game {
    public static final int VIRTUAL_WIDTH = 640;
    public static final int ACTUAL_WIDTH = 1280;
    public static final int VIRTUAL_HEIGHT = 360;
    public static final int ACTUAL_HEIGHT = 720;
    public static final float PIXELS_PER_METER = 20;
    public static final short DEFAULT_BIT = 1;
    public static final short ASPECT_BIT = 2;
    public static final short COIN_BIT = 4;
    public static final short NONE_BIT = 8;
    public static final short HEART_BIT = 16;
    public static final short SPIKE_BIT = 32;
    public static final short ENEMY_BIT = 64;
    public static final short INVISIBLE_WALL_BIT = 128;
    public static final short ASPECT_FEET_BIT = 256;
    public static final short ENEMY_HEAD_BIT = 512;
    public static AssetManager assetManager;
    public SpriteBatch batch;
    private List<Level> levels = new ArrayList();
    private Level currentLevel;
    private Preferences preferences;

    @Override
    public void create() {
        batch = new SpriteBatch();
        loadAssets();
        preferences = Gdx.app.getPreferences("level.data");
        loadLevels();
        levels.get(0).setUnlocked(true);
        preferences.flush();

        //unlockAllLevels();
        setScreen(new MenuScreen(batch, this));


    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
    }

    private void loadAssets() {
        assetManager = new AssetManager();
        assetManager.load("soundtrack/looped/menu_theme.mp3", Music.class);
        assetManager.load("soundtrack/looped/city_theme.mp3", Music.class);
        assetManager.load("soundtrack/looped/desert_theme.mp3", Music.class);
        assetManager.load("soundtrack/looped/death_theme.mp3", Music.class);
        assetManager.load("soundtrack/looped/win_theme.mp3", Music.class);
        assetManager.load("soundtrack/looped/dungeon_theme.mp3", Music.class);
        assetManager.load("soundtrack/looped/end_theme.mp3", Music.class);
        assetManager.load("soundtrack/clips/pickup_coin.wav", Sound.class);
        assetManager.load("soundtrack/clips/jump.wav", Sound.class);
        assetManager.load("soundtrack/clips/got_item.mp3", Sound.class);
        assetManager.load("soundtrack/clips/button.wav", Sound.class);
        assetManager.load("soundtrack/clips/hurt.wav", Sound.class);
        assetManager.finishLoading();
    }

    private void loadLevels() {

        for (int i = 1; i <= 3; i++) {
            boolean unlocked = preferences.getBoolean("city" + i, false);
            levels.add(new Level(i, "city", "levels/testowy" + i + ".tmx",
                    assetManager.get("soundtrack/looped/city_theme.mp3", Music.class), unlocked));

        }
        for (int i = 4; i <= 6; i++) {
            boolean unlocked = preferences.getBoolean("desert" + i, false);
            levels.add(new Level(i, "desert", "levels/testowy" + i + ".tmx",
                    assetManager.get("soundtrack/looped/desert_theme.mp3", Music.class), unlocked));
        }
        for (int i = 7; i <= 9; i++) {
            boolean unlocked = preferences.getBoolean("dungeon" + i, false);
            levels.add(new Level(i, "dungeon", "levels/testowy" + i + ".tmx",
                    assetManager.get("soundtrack/looped/dungeon_theme.mp3", Music.class), unlocked));
        }

    }

    private void unlockAllLevels() {
        for (Level level : levels) {
            level.setUnlocked(true);
        }
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public List<Level> getLevels() {
        return levels;
    }


}
