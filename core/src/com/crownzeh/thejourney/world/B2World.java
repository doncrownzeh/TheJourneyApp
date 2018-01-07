package com.crownzeh.thejourney.world;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.crownzeh.thejourney.TheJourney;
import com.crownzeh.thejourney.other.GameConfig;
import com.crownzeh.thejourney.scenes.Hud;
import com.crownzeh.thejourney.screens.GameScreen;
import com.crownzeh.thejourney.sprites.Aspect;
import com.crownzeh.thejourney.sprites.collision.Coin;
import com.crownzeh.thejourney.sprites.collision.Heart;
import com.crownzeh.thejourney.sprites.collision.InvisibleWall;
import com.crownzeh.thejourney.sprites.collision.Spike;
import com.crownzeh.thejourney.sprites.collision.StaticObject;
import com.crownzeh.thejourney.sprites.enemies.Soldier;


public class B2World {
    private final int GROUND_LAYER = 2;
    private final int PLATFORM_LAYER = 3;
    private final int COIN_LAYER = 4;
    private final int HEART_LAYER = 5;
    private final int SPIKE_LAYER = 6;
    private final int ENEMY_LAYER = 7;
    private final int INVISIBLE_WALL_LAYER = 8;
    private TheJourney game;
    private Aspect player;
    private Hud hud;
    private Array<Soldier> soldiers;

    public B2World(World world, TiledMap map, Aspect player, Hud hud, TheJourney game, GameScreen gameScreen) {
        this.game = game;
        this.setPlayer(player);
        this.setHud(hud);
        game.getCurrentLevel().amountOfCoins = 0;

        // ground layer
        for (MapObject object : map.getLayers().get(GROUND_LAYER).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new StaticObject(world, map, rect);
        }

        // platforms layer
        for (MapObject object : map.getLayers().get(PLATFORM_LAYER).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new StaticObject(world, map, rect);
        }


        // coins layer
        for (MapObject object : map.getLayers().get(HEART_LAYER).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Heart(world, map, rect, player, hud);
        }

        //spikes layer
        for (MapObject object : map.getLayers().get(SPIKE_LAYER).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Spike(world, map, rect, player, hud);
        }

        //soldiers layer
        soldiers = new Array<Soldier>();
        for (MapObject object : map.getLayers().get(ENEMY_LAYER).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            String name = game.getCurrentLevel().getLevelName();
            int type = -1;
            if (name.equals("city")) {
                type = 1;
            }
            if (name.equals("desert")) {
                type = 2;
            }
            if (name.equals("dungeon")) {
                type = 3;
            }
            if (game.getCurrentLevel().getId() % 3 == 0) {
                type = 4;
            }

            soldiers.add(new Soldier(world, gameScreen, rect.getX() / GameConfig.PIXELS_PER_METER, rect.getY() / GameConfig.PIXELS_PER_METER, type));
        }

        // coins layer
        for (MapObject object : map.getLayers().get(COIN_LAYER).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Coin(world, map, rect, player, hud);
            game.getCurrentLevel().amountOfCoins++;
        }

        // invisible wall layer
        for (MapObject object : map.getLayers().get(INVISIBLE_WALL_LAYER).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new InvisibleWall(world, map, rect);
        }


    }

    public Array<Soldier> getSoldiers() {
        return soldiers;
    }

    private void setHud(Hud hud) {
        this.hud = hud;
    }

    private void setPlayer(Aspect player) {
        this.player = player;
    }

}
