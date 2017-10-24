package com.crownzeh.thejourney.world;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;

public class Level {
    Preferences prefs;
    String levelName;
    String levelPath;
    int highscore;
    boolean unlocked;
    int amountOfCoins;
    Music theme;
    int id;

    public Level(int id, String name, String path, Music theme, boolean unlocked) {
        amountOfCoins = 0;
        this.unlocked = unlocked;
        this.id = id;
        this.theme = theme;
        this.levelName = name;
        this.levelPath = path;
        prefs = Gdx.app.getPreferences("level.data");
        this.highscore = prefs.getInteger(levelName + id + "hs", 0);
    }

    public Music getTheme() {
        return theme;
    }

    public void setTheme(Music theme) {
        this.theme = theme;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
        prefs = Gdx.app.getPreferences("level.data");
        prefs.putInteger(levelName + id + "hs", highscore);
        prefs.flush();

    }

    public String getLevelPath() {
        return levelPath;
    }

    public void setLevelPath(String levelPath) {
        this.levelPath = levelPath;
    }

    public int getAmountOfCoins() {
        return amountOfCoins;
    }

    public void setAmountOfCoins(int amountOfCoins) {
        this.amountOfCoins = amountOfCoins;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        prefs = Gdx.app.getPreferences("level.data");
        this.unlocked = unlocked;
        prefs.putBoolean(this.levelName + this.id, unlocked);
        prefs.flush();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
