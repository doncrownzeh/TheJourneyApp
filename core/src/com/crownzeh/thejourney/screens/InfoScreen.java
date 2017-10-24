package com.crownzeh.thejourney.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.crownzeh.thejourney.TheJourney;


public class InfoScreen implements Screen {

    private Image backButton;
    private Label deviceLabel;
    private Label deviceVersionLabel;
    private Label resolutionLabel;
    private Label currentResolutionLabel;
    private Label authorLabel;
    private Label authorTextLabel;
    private Label versionLabel;
    private Label currentVersionLabel;
    private FitViewport viewport;
    private Stage stage;
    private String systemVersion;
    private SpriteBatch sb;
    private TheJourney game;
    private OrthographicCamera camera;
    private MenuScreen menuScreen;


    public InfoScreen(SpriteBatch sb, TheJourney game, MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
        this.sb = sb;
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(TheJourney.VIRTUAL_WIDTH, TheJourney.VIRTUAL_HEIGHT, camera);
        stage = new Stage(viewport, sb);


        Table table = new Table();
        table.center().top();


        switch (Gdx.app.getType()) {
            case Android:
                systemVersion = "Android";
                break;
            case Desktop:
                systemVersion = "Desktop";
                break;
        }

        String resolution = Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight();

        deviceLabel = new Label("Device", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        deviceVersionLabel = new Label(systemVersion, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        resolutionLabel = new Label("Resolution", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        currentResolutionLabel = new Label(resolution, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        versionLabel = new Label("Version", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        currentVersionLabel = new Label(String.format("%01d", Gdx.app.getVersion()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        authorLabel = new Label("Author", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        authorTextLabel = new Label("Jakub Krzetowski", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(deviceLabel).expandX().padTop(10);
        table.add(deviceVersionLabel).expandX().padTop(10);
        table.row();
        table.add(resolutionLabel).expandX();
        table.add(currentResolutionLabel).expandX();
        table.row();
        table.add(versionLabel).expandX();
        table.add(currentVersionLabel).expandX();
        table.row();
        table.add(authorLabel).expandX();
        table.add(authorTextLabel).expandX();
        table.setFillParent(true);
        stage.addActor(table);

        Table buttonTable = new Table();
        buttonTable.bottom().left();
        backButton = new Image(new Texture("buttons/btn_back.png"));
        backButton.setSize(backButton.getWidth() * 2, backButton.getHeight() * 2);
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backButtonAction();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        buttonTable.add(backButton).size(backButton.getWidth(), backButton.getHeight()).pad(5, 5, 5, 5);
        buttonTable.setFillParent(true);
        stage.addActor(buttonTable);


    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.end();
        game.batch.setProjectionMatrix(this.stage.getCamera().combined);
        this.stage.draw();

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

    }

    private void backButtonAction() {

        TheJourney.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
        game.setScreen(menuScreen);
    }
}
