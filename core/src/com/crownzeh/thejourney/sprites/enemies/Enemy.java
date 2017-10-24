package com.crownzeh.thejourney.sprites.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.crownzeh.thejourney.screens.GameScreen;

public abstract class Enemy extends Sprite {
    public Body body;
    protected World world;
    protected GameScreen screen;


    public Enemy(World world, GameScreen screen, float x, float y) {
        this.world = world;
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
    }

    protected abstract void defineEnemy();

    public abstract void die();

}
