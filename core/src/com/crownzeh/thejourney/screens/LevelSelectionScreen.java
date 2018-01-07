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
import com.crownzeh.thejourney.other.Assets;
import com.crownzeh.thejourney.other.GameConfig;
import com.crownzeh.thejourney.world.Level;


public class LevelSelectionScreen implements Screen {

    private Music theme;
    private SpriteBatch sb;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private Stage stage;
    private Texture background;
    private Image backButton;
    private TheJourney game;
    private MenuScreen menuScreen;


    LevelSelectionScreen(TheJourney game, SpriteBatch sb, MenuScreen menuScreen, Music theme) {
        this.game = game;
        this.sb = sb;
        this.menuScreen = menuScreen;
        this.theme = theme;
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.VIRTUAL_WIDTH, GameConfig.VIRTUAL_HEIGHT, camera);
        background = new Texture("background_texture.png");
        Table levelTable = new Table();
        levelTable.center().top().padTop(15);
        backButton = new Image(new Texture("buttons/btn_back.png"));
        stage = new Stage(viewport, sb);

        for (Level level : game.getLevels()) {
            int i = level.getId();
            Image temp;
            if (level.isUnlocked()) {
                final Level tempLevel = level;
                temp = new Image(new Texture("buttons/level/" + level.getLevelName() + ".png"));
                temp.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        levelButtonAction(tempLevel);
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });

            } else {
                temp = new Image(new Texture("buttons/level/" + level.getLevelName() + "_locked.png"));
            }
            levelTable.add(temp).size(temp.getWidth(), temp.getHeight());
            if (i % 3 == 0) levelTable.row().pad(10, 10, 10, 10);
        }

        levelTable.setFillParent(true);


        backButton.setSize(backButton.getWidth() * 2, backButton.getHeight() * 2);
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backButtonAction();
                return super.touchDown(event, x, y, pointer, button);
            }
        });


        Table controlTable = new Table();
        controlTable.bottom().left().pad(5, 5, 5, 5);
        controlTable.add(backButton).size(backButton.getWidth(), backButton.getHeight());
        controlTable.setFillParent(true);

        stage.addActor(levelTable);
        stage.addActor(controlTable);

    }

    private void backButtonAction() {

        Assets.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
        game.setScreen(menuScreen);
    }

    private void levelButtonAction(Level level) {
        Assets.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
        theme.stop();
        game.setCurrentLevel(level);
        Gdx.app.log(game.getCurrentLevel().getLevelPath(), game.getCurrentLevel().getLevelName());
        game.setScreen(new GameScreen(game, game.getCurrentLevel().getLevelPath()));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, GameConfig.VIRTUAL_WIDTH, GameConfig.VIRTUAL_HEIGHT);
        game.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
