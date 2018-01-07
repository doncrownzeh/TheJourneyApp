package com.crownzeh.thejourney.other;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Assets {
    public static AssetManager assetManager;

    public static void loadAssets() {
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
}
