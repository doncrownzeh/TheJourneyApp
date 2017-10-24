package com.crownzeh.thejourney.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.crownzeh.thejourney.TheJourney;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = TheJourney.ACTUAL_WIDTH;
        config.height = TheJourney.ACTUAL_HEIGHT;
        config.resizable = true;

        new LwjglApplication(new TheJourney(), config);

    }
}
