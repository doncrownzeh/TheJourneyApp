package com.crownzeh.thejourney.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.crownzeh.thejourney.TheJourney;
import com.crownzeh.thejourney.other.GameConfig;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = GameConfig.ACTUAL_WIDTH;
        config.height = GameConfig.ACTUAL_HEIGHT;
        config.resizable = true;

        new LwjglApplication(new TheJourney(), config);

    }
}
