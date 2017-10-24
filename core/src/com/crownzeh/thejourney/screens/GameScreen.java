package com.crownzeh.thejourney.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.crownzeh.thejourney.TheJourney;
import com.crownzeh.thejourney.scenes.ControlOverlay;
import com.crownzeh.thejourney.scenes.Hud;
import com.crownzeh.thejourney.sprites.Aspect;
import com.crownzeh.thejourney.sprites.enemies.Soldier;
import com.crownzeh.thejourney.world.B2World;
import com.crownzeh.thejourney.world.WorldContactListener;

public class GameScreen implements Screen {

    private TheJourney game;
    private TextureAtlas atlas;
    private OrthographicCamera gameCamera;
    private FitViewport viewport;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Aspect player;
    private Music theme;
    private World world;
    private Box2DDebugRenderer b2ddr;
    private ControlOverlay controlOverlay;
    private boolean rendering;
    private B2World b2world;

    public GameScreen(TheJourney game, String level) {

        atlas = new TextureAtlas("characters/sprite_sheet.txt");
        this.game = game;
        gameCamera = new OrthographicCamera();
        viewport = new FitViewport(TheJourney.VIRTUAL_WIDTH / TheJourney.PIXELS_PER_METER,
                TheJourney.VIRTUAL_HEIGHT / TheJourney.PIXELS_PER_METER, gameCamera);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(level);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / TheJourney.PIXELS_PER_METER);
        gameCamera.position.set(TheJourney.VIRTUAL_WIDTH / 2, TheJourney.VIRTUAL_HEIGHT / 2, 0);
        world = new World(new Vector2(0, -10), true);
        b2ddr = new Box2DDebugRenderer();
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            b2ddr.setDrawVelocities(false);
            b2ddr.setDrawAABBs(false);
            b2ddr.setDrawBodies(false);
            b2ddr.setDrawContacts(false);
            b2ddr.setDrawInactiveBodies(false);
            b2ddr.setDrawJoints(false);
        } else {
            b2ddr.setDrawVelocities(true);
            b2ddr.setDrawAABBs(false);
            b2ddr.setDrawBodies(true);
            b2ddr.setDrawContacts(false);
            b2ddr.setDrawInactiveBodies(false);
            b2ddr.setDrawJoints(false);
        }
        rendering = true;
        player = new Aspect(world, this);
        hud = new Hud(game.batch, player, game);
        controlOverlay = new ControlOverlay(game.batch, player, game, this.theme);
        b2world = new B2World(world, map, player, hud, game, this);
        hud.update();
        world.setContactListener(new WorldContactListener(player));
        theme = game.getCurrentLevel().getTheme();
        theme.setLooping(true);
        theme.play();

    }

    public Aspect getPlayer() {
        return player;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        for (Soldier soldier : b2world.getSoldiers()) {
            soldier.update(delta);
        }
        player.update(delta, game.getCurrentLevel().getAmountOfCoins());
        gameCamera.position.x = player.body.getPosition().x;
        gameCamera.position.y = player.body.getPosition().y + 3;
        hud.update();
        gameCamera.update();
        mapRenderer.setView(gameCamera);
        controlOverlay.update(delta);

        Gdx.input.setInputProcessor(controlOverlay.getStage());

    }

    @Override
    public void render(float delta) {

        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.render();
        b2ddr.render(world, gameCamera.combined);
        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Soldier soldier : b2world.getSoldiers()) {
            soldier.draw(game.batch);
        }
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        controlOverlay.draw();


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        controlOverlay.resize(width, height);


    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
        world.dispose();
        b2ddr.dispose();
        hud.dispose();
        controlOverlay.dispose();


    }

}
