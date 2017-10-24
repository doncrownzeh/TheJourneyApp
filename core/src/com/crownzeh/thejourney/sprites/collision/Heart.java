package com.crownzeh.thejourney.sprites.collision;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.crownzeh.thejourney.TheJourney;
import com.crownzeh.thejourney.scenes.Hud;
import com.crownzeh.thejourney.sprites.Aspect;

public class Heart extends CollisionObject {

    private Aspect player;
    private Hud hud;

    public Heart(World world, TiledMap map, Rectangle bounds, Aspect player, Hud hud) {
        super(world, map, bounds);
        this.player = player;
        this.hud = hud;
        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(TheJourney.HEART_BIT);

    }

    @Override
    public void onHit() {
        setCategoryFilter(TheJourney.NONE_BIT);
        getCell().setTile(null);
        TheJourney.assetManager.get("soundtrack/clips/got_item.mp3", Sound.class).play();
        player.setHealth((short) (player.getHealth() + 1));
        hud.update();
    }

}
