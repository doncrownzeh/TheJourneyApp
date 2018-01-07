package com.crownzeh.thejourney.sprites.collision;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.crownzeh.thejourney.TheJourney;
import com.crownzeh.thejourney.other.GameConfig;

public class InvisibleWall extends com.crownzeh.thejourney.sprites.collision.CollisionObject {

    public InvisibleWall(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        setCategoryFilter(GameConfig.INVISIBLE_WALL_BIT);
    }

    @Override
    public void onHit() {
    }
}
