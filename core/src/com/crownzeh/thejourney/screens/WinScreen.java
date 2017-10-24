package com.crownzeh.thejourney.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.crownzeh.thejourney.TheJourney;


public class WinScreen implements Screen {
    TheJourney game;
    SpriteBatch sb;
    Music theme;
    OrthographicCamera camera;
    FitViewport viewport;
    Image menuButton;
    Stage stage;
    Texture background;


    public WinScreen(final SpriteBatch sb, final TheJourney game) {
        this.game = game;
        this.sb = sb;
        theme = TheJourney.assetManager.get("soundtrack/looped/end_theme.mp3", Music.class);
        theme.setLooping(true);
        theme.play();

        camera = new OrthographicCamera();
        viewport = new FitViewport(TheJourney.VIRTUAL_WIDTH, TheJourney.VIRTUAL_HEIGHT, camera);

        background = new Texture("end_background.png");
        menuButton = new Image(new Texture("buttons/btn_main_menu.png"));
        menuButton.setSize(menuButton.getWidth(), menuButton.getHeight());
        menuButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                TheJourney.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
                TheJourney.assetManager.get("soundtrack/looped/end_theme.mp3", Music.class).stop();
                game.setScreen(new MenuScreen(sb, game));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        stage = new Stage(viewport, sb);
        Table table = new Table();
        table.center().bottom().padBottom(10);
        table.add(menuButton);


        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(this.stage);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, TheJourney.VIRTUAL_WIDTH, TheJourney.VIRTUAL_HEIGHT);
        game.batch.end();
        stage.draw();


    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        theme.dispose();

    }
}
