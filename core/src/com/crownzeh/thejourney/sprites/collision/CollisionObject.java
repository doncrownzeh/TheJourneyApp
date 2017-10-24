package com.crownzeh.thejourney.sprites.collision;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.crownzeh.thejourney.TheJourney;

public abstract class CollisionObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public CollisionObject() {
    }

    public CollisionObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polyShape = new PolygonShape();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / TheJourney.PIXELS_PER_METER,
                (bounds.getY() + bounds.getHeight() / 2) / TheJourney.PIXELS_PER_METER);
        body = world.createBody(bodyDef);
        polyShape.setAsBox(bounds.getWidth() / 2 / TheJourney.PIXELS_PER_METER,
                bounds.getHeight() / 2 / TheJourney.PIXELS_PER_METER);
        fixtureDef.shape = polyShape;
        fixture = body.createFixture(fixtureDef);
    }

    public abstract void onHit();

    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        int xCoord = (int) (body.getPosition().x * TheJourney.PIXELS_PER_METER / 20);
        int yCoord = (int) (body.getPosition().y * TheJourney.PIXELS_PER_METER / 20);
        return layer.getCell(xCoord, yCoord);
    }

}
