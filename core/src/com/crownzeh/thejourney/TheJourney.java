package com.crownzeh.thejourney;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.crownzeh.thejourney.other.Assets;
import com.crownzeh.thejourney.screens.MenuScreen;
import com.crownzeh.thejourney.world.Level;

import java.util.ArrayList;
import java.util.List;


public class TheJourney extends Game {
    public SpriteBatch batch;
    private List<Level> levels = new ArrayList();
    private Level currentLevel;
    private Preferences preferences;

    @Override
    public void create() {
        batch = new SpriteBatch();
        Assets.loadAssets();
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
    }



    private void loadLevels() {

        for (int i = 1; i <= 3; i++) {
            boolean unlocked = preferences.getBoolean("city" + i, false);
            levels.add(new Level(i, "city", "levels/testowy" + i + ".tmx",
                    Assets.assetManager.get("soundtrack/looped/city_theme.mp3", Music.class), unlocked));

        }
        for (int i = 4; i <= 6; i++) {
            boolean unlocked = preferences.getBoolean("desert" + i, false);
            levels.add(new Level(i, "desert", "levels/testowy" + i + ".tmx",
                    Assets.assetManager.get("soundtrack/looped/desert_theme.mp3", Music.class), unlocked));
        }
        for (int i = 7; i <= 9; i++) {
            boolean unlocked = preferences.getBoolean("dungeon" + i, false);
            levels.add(new Level(i, "dungeon", "levels/testowy" + i + ".tmx",
                    Assets.assetManager.get("soundtrack/looped/dungeon_theme.mp3", Music.class), unlocked));
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
