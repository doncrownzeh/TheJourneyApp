package com.crownzeh.thejourney.scenes;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crownzeh.thejourney.TheJourney;
import com.crownzeh.thejourney.other.GameConfig;
import com.crownzeh.thejourney.sprites.Aspect;

import java.util.Locale;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;
    private Aspect player;
    private TheJourney game;
    private String coinInfo;
    private Image heart;
    private Label heartsLeftLabel;
    private Image coin;
    private Label coinLabel;
    private Label levelNameLabel;
    private Label levelIndexLabel;
    private Label scoreLabel;
    private Label scoreDisplayLabel;
    private Label highscoreLabel;
    private Label highscoreDisplayLabel;

    public Hud(SpriteBatch sb, Aspect player, TheJourney game) {
        this.game = game;
        this.player = player;
        viewport = new FitViewport(GameConfig.VIRTUAL_WIDTH, GameConfig.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        coin = new Image(new Texture("coin.png"));
        coin.setSize(15, 15);
        heart = new Image(new Texture("heart.png"));
        heart.setSize(15, 15);
        coinInfo = " x " + player.getVirtualCoins() + " /" + game.getCurrentLevel().getAmountOfCoins();
        Table table = new Table();
        table.left().top().padTop(10);
        table.setFillParent(true);
        coinLabel = new Label(coinInfo, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        heartsLeftLabel = new Label(String.format(Locale.US," x " + "%01d", player.getHealth()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelIndexLabel = new Label(String.format(Locale.US,"%1d", game.getCurrentLevel().getId()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelNameLabel = new Label(game.getCurrentLevel().getLevelName(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        highscoreLabel = new Label(String.format(Locale.US,"%5d", game.getCurrentLevel().getHighscore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        highscoreDisplayLabel = new Label("Highscore", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format(Locale.US,"%5d", player.getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreDisplayLabel = new Label("Score", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(levelNameLabel);
        table.add(levelIndexLabel);
        table.row().padTop(10);
        table.add(highscoreDisplayLabel);
        table.add(highscoreLabel);
        table.row().padTop(10);
        table.add(scoreDisplayLabel);
        table.add(scoreLabel);
        table.row().padTop(10);
        table.add(coin).size(coin.getWidth(), coin.getHeight());
        table.add(coinLabel);
        table.row().padTop(10);
        table.add(heart).size(heart.getWidth(), heart.getHeight());
        table.add(heartsLeftLabel);
        stage.addActor(table);

    }

    public void update() {
        updateLabels();
    }


    private void updateLabels() {
        coinInfo = " x " + player.getVirtualCoins() + " / " + game.getCurrentLevel().getAmountOfCoins();
        coinLabel.setText(coinInfo);
        heartsLeftLabel.setText(String.format(Locale.US," x " + "%01d", player.getHealth()));
        scoreLabel.setText(String.format(Locale.US,"%5d", player.getScore()));
        highscoreLabel.setText(String.format(Locale.US,"%5d", game.getCurrentLevel().getHighscore()));
    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}
