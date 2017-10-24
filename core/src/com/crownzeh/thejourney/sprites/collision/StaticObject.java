package com.crownzeh.thejourney.sprites.collision;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class StaticObject extends com.crownzeh.thejourney.sprites.collision.CollisionObject {

    public StaticObject(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData("static");
    }

    @Override
    public void onHit() {
    }

}
