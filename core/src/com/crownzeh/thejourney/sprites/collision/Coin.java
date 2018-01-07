package com.crownzeh.thejourney.sprites.collision;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.crownzeh.thejourney.TheJourney;
import com.crownzeh.thejourney.other.Assets;
import com.crownzeh.thejourney.other.GameConfig;
import com.crownzeh.thejourney.scenes.Hud;
import com.crownzeh.thejourney.sprites.Aspect;

public class Coin extends CollisionObject {

    private Aspect player;
    private Hud hud;

    public Coin(World world, TiledMap map, Rectangle bounds, Aspect player, Hud hud) {
        super(world, map, bounds);
        this.player = player;
        this.hud = hud;
        fixture.setUserData(this);
        setCategoryFilter(GameConfig.COIN_BIT);
        fixture.setSensor(true);

    }

    @Override
    public void onHit() {
        setCategoryFilter(GameConfig.NONE_BIT);
        getCell().setTile(null);
        Assets.assetManager.get("soundtrack/clips/pickup_coin.wav", Sound.class).play();
        player.addCoin();
        hud.update();

    }

}
