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


public class MenuScreen implements Screen {

    //background colour
    private final float RED = 0.0f;
    private final float GREEN = 0.0f;
    private final float BLUE = 0f;
    private TheJourney game;
    private Image playButton;
    private Image infoButton;
    private Image exitButton;
    private Texture background;
    private Image logo;
    private Music theme;
    private SpriteBatch sb;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private Stage stage;

    public MenuScreen(SpriteBatch sb, TheJourney game) {
        this.game = game;
        this.sb = sb;

        theme = TheJourney.assetManager.get("soundtrack/looped/menu_theme.mp3", Music.class);
        theme.setLooping(true);
        theme.play();

        camera = new OrthographicCamera();
        viewport = new FitViewport(TheJourney.VIRTUAL_WIDTH, TheJourney.VIRTUAL_HEIGHT, camera);

        background = new Texture("background_texture.png");
        logo = new Image(new Texture("logo.png"));
        playButton = new Image(new Texture("buttons/btn_play.png"));
        infoButton = new Image(new Texture("buttons/btn_settings.png"));
        exitButton = new Image(new Texture("buttons/btn_exit.png"));

        stage = new Stage(viewport, sb);


        Table table = new Table();
        table.center().top().padTop(10);
        logo.setSize(logo.getWidth() * 2, logo.getHeight() * 2);
        playButton.setSize(playButton.getWidth() * 2, playButton.getHeight() * 2);
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                playButtonAction();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        infoButton.setSize(infoButton.getWidth() * 2, infoButton.getHeight() * 2);
        infoButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                infoButtonAction();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        exitButton.setSize(exitButton.getWidth() * 2, exitButton.getHeight() * 2);
        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                exitButtonAction();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        table.add(logo).size(logo.getWidth(), logo.getHeight());
        table.row().pad(10, 10, 10, 10);
        table.add(playButton).size(playButton.getWidth(), playButton.getHeight());
        table.row().pad(10, 10, 10, 10);
        table.add(infoButton).size(infoButton.getWidth(), infoButton.getHeight());
        table.row().pad(10, 10, 10, 10);
        table.add(exitButton).size(exitButton.getWidth(), exitButton.getHeight());
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

        Gdx.gl.glClearColor(RED, GREEN, BLUE, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, TheJourney.VIRTUAL_WIDTH, TheJourney.VIRTUAL_HEIGHT);
        game.batch.end();
        stage.draw();


    }

    private void playButtonAction() {
        TheJourney.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
        game.setScreen(new LevelSelectionScreen(game, sb, this, theme));
    }

    private void infoButtonAction() {
        TheJourney.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
        game.setScreen(new InfoScreen(sb, game, this));
    }

    private void exitButtonAction() {
        TheJourney.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
        Gdx.app.exit();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

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
        theme.dispose();
        background.dispose();
        stage.dispose();

    }

}
