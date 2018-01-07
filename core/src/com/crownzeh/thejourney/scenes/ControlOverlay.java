package com.crownzeh.thejourney.scenes;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crownzeh.thejourney.TheJourney;
import com.crownzeh.thejourney.other.Assets;
import com.crownzeh.thejourney.other.GameConfig;
import com.crownzeh.thejourney.other.GameInputProcessor;
import com.crownzeh.thejourney.sprites.Aspect;

public class ControlOverlay implements Disposable {

    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage mainStage;
    private Stage menuStage;
    private Stage deathStage;
    private Stage winStage;
    private Stage currentStage;
    private Aspect player;
    private SpriteBatch sb;
    private TheJourney game;
    private Music theme;
    private GameInputProcessor gameInputProcessor;
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean playerAlive;

    public ControlOverlay(SpriteBatch sb, Aspect player, TheJourney game, Music theme) {
        this.sb = sb;
        this.player = player;
        playerAlive = player.isAlive();
        this.game = game;
        this.theme = theme;
        gameInputProcessor = new GameInputProcessor(sb, player, game);
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.VIRTUAL_WIDTH, GameConfig.VIRTUAL_HEIGHT, camera);
        mainStage = new Stage(viewport, sb);
        menuStage = new Stage(mainStage.getViewport(), mainStage.getBatch());
        deathStage = new Stage(mainStage.getViewport(), mainStage.getBatch());
        winStage = new Stage(mainStage.getViewport(), mainStage.getBatch());
        currentStage = mainStage;
        drawArrows();
        drawActionButtons();
        drawMenuButton();
        drawInGameMenu();
        drawDeathStage();
        drawWinStage();

    }

    private boolean isRightPressed() {
        return rightPressed;
    }

    private boolean isLeftPressed() {
        return leftPressed;
    }

    private void drawActionButtons() {
        Table actionTable = new Table();
        actionTable.bottom().right();
        Image actionButton1 = new Image(new Texture("buttons/btn_action1.png"));
        actionButton1.setSize(70, 70);
        actionButton1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameInputProcessor.jump();
                return true;
            }
        });
        Image actionButton2 = new Image(new Texture("buttons/btn_action2.png"));
        actionButton2.setSize(70, 70);
        actionButton2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameInputProcessor.dash();
                return true;
            }
        });
        actionTable.padRight(5);
        actionTable.add();
        actionTable.add(actionButton1).size(actionButton1.getWidth(), actionButton1.getHeight());
        actionTable.row().pad(5, 5, 5, 5);
        actionTable.add(actionButton2).size(actionButton2.getWidth(), actionButton2.getHeight());
        actionTable.add();
        actionTable.setFillParent(true);
        mainStage.addActor(actionTable);
    }

    private void drawArrows() {
        Table arrowTable = new Table();
        arrowTable.bottom().left();
        Image upImage = new Image(new Texture("buttons/btn_up.png"));
        upImage.setSize(50, 50);
        upImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameInputProcessor.jump();
                return true;
            }
        });
        Image downImage = new Image(new Texture("buttons/btn_down.png"));
        downImage.setSize(50, 50);
        downImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameInputProcessor.dash();
                return true;
            }
        });
        Image rightImage = new Image(new Texture("buttons/btn_right.png"));
        rightImage.setSize(50, 50);
        rightImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });
        Image leftImage = new Image(new Texture("buttons/btn_left.png"));
        leftImage.setSize(50, 50);
        leftImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });
        arrowTable.padLeft(5);
        arrowTable.add();
        arrowTable.add(upImage).size(upImage.getWidth(), upImage.getHeight());
        arrowTable.add();
        arrowTable.row().pad(5, 5, 5, 5);
        arrowTable.add(leftImage).size(leftImage.getWidth(), leftImage.getHeight());
        arrowTable.add();
        arrowTable.add(rightImage).size(rightImage.getWidth(), rightImage.getHeight());
        arrowTable.row().padBottom(5);
        arrowTable.add();
        arrowTable.add(downImage).size(downImage.getWidth(), downImage.getHeight());
        arrowTable.add();
        arrowTable.setFillParent(true);
        mainStage.addActor(arrowTable);
    }

    private void drawMenuButton() {
        Table menuTable = new Table();
        menuTable.top().right().padRight(10);
        Image menuButton = new Image(new Texture("buttons/btn_menu.png"));
        menuButton.setSize(80, 50);
        menuButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
                currentStage = menuStage;
                return true;
            }
        });
        menuTable.add(menuButton).size(menuButton.getWidth(), menuButton.getHeight());
        menuTable.setFillParent(true);
        mainStage.addActor(menuTable);
    }

    private void drawInGameMenu() {
        Table table = new Table();
        table.padTop(30);
        Image close = new Image(new Texture("buttons/btn_resume.png"));
        close.setSize(close.getWidth(), close.getHeight());
        close.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
                currentStage = mainStage;
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        Image restart = new Image(new Texture("buttons/btn_restart.png"));
        restart.setSize(restart.getWidth(), restart.getHeight());
        restart.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
                gameInputProcessor.restartLevel();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        Image exit = new Image(new Texture("buttons/btn_main_menu.png"));
        exit.setSize(exit.getWidth(), exit.getHeight());
        exit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
                gameInputProcessor.goToMainMenu();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        table.add(close).size(close.getWidth(), close.getHeight());
        table.row().pad(10, 10, 10, 10);
        table.add(restart).size(restart.getWidth(), restart.getHeight());
        table.row().pad(10, 10, 10, 10);
        table.add(exit).size(exit.getWidth(), exit.getHeight());
        table.setFillParent(true);
        menuStage.addActor(table);
    }


    private void drawDeathStage() {
        Table deathTable = new Table();
        Image deathBackground = new Image(new Texture("death_background.png"));
        deathTable.add(deathBackground).size(deathBackground.getWidth(), deathBackground.getHeight());
        deathTable.setFillParent(true);
        Table deathButtons = new Table().bottom().padBottom(20);
        Image restartLevel = new Image(new Texture("buttons/btn_restart.png"));
        restartLevel.setSize(restartLevel.getWidth() / 2, restartLevel.getHeight() / 2);
        restartLevel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
                Assets.assetManager.get("soundtrack/looped/death_theme.mp3", Music.class).stop();
                gameInputProcessor.restartLevel();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        Image exitToMenu = new Image(new Texture("buttons/btn_main_menu.png"));
        exitToMenu.setSize(exitToMenu.getWidth() / 2, exitToMenu.getHeight() / 2);
        exitToMenu.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
                Assets.assetManager.get("soundtrack/looped/death_theme.mp3", Music.class).stop();
                gameInputProcessor.goToMainMenu();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        deathButtons.add(restartLevel).size(restartLevel.getWidth(), restartLevel.getHeight()).expandX();
        deathButtons.add(exitToMenu).size(exitToMenu.getWidth(), exitToMenu.getHeight()).expandX();
        deathButtons.setFillParent(true);

        deathStage.addActor(deathTable);
        deathStage.addActor(deathButtons);

    }

    private void drawWinStage() {
        Table winTable = new Table();
        Image winBackground = new Image(new Texture("win_background.png"));
        winTable.add(winBackground).size(winBackground.getWidth(), winBackground.getHeight());
        winTable.setFillParent(true);
        Table winButtons = new Table().bottom().padBottom(20);
        Image nextLevel = new Image(new Texture("buttons/btn_next_level.png"));
        Image exitToMenu = new Image(new Texture("buttons/btn_main_menu.png"));
        exitToMenu.setSize(exitToMenu.getWidth() / 2, exitToMenu.getHeight() / 2);
        exitToMenu.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
                Assets.assetManager.get("soundtrack/looped/win_theme.mp3", Music.class).stop();
                gameInputProcessor.goToMainMenu();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        nextLevel.setSize(nextLevel.getWidth() / 2, nextLevel.getHeight() / 2);
        nextLevel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.assetManager.get("soundtrack/clips/button.wav", Sound.class).play();
                Assets.assetManager.get("soundtrack/looped/win_theme.mp3", Music.class).stop();
                gameInputProcessor.nextLevel();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        winButtons.add(exitToMenu).size(exitToMenu.getWidth(), exitToMenu.getHeight()).expandX();
        winButtons.add(nextLevel).size(nextLevel.getWidth(), nextLevel.getHeight()).expandX();
        winButtons.setFillParent(true);
        winStage.addActor(winTable);
        winStage.addActor(winButtons);

    }

    private void handleInput(float delta) {

        if (this.isRightPressed() && player.isRightMovementPossible()) {
            gameInputProcessor.moveRight();
        } else if (this.isLeftPressed() && player.isLeftMovementPossible()) {
            gameInputProcessor.moveLeft();
        } else gameInputProcessor.sideArrowUp();

    }

    public void update(float delta) {
        playerAlive = player.isAlive();
        if (!playerAlive) {
            currentStage = deathStage;
            gameInputProcessor.stopLevelMusic();
            Assets.assetManager.get("soundtrack/looped/death_theme.mp3", Music.class).play();
        }
        if (player.isFinished()) {
            if (game.getCurrentLevel().getHighscore() < player.getScore()) {
                game.getCurrentLevel().setHighscore(player.getScore());
            }
            if (game.getCurrentLevel().getId() < 9) {
                game.getLevels().get(game.getCurrentLevel().getId()).setUnlocked(true);
            }
            currentStage = winStage;
            gameInputProcessor.stopLevelMusic();
            Assets.assetManager.get("soundtrack/looped/win_theme.mp3", Music.class).play();

        }
        handleInput(delta);
    }

    public void draw() {
        currentStage.draw();
    }

    public Stage getStage() {
        return currentStage;
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        mainStage.dispose();
        currentStage.dispose();
        menuStage.dispose();
        deathStage.dispose();
        winStage.dispose();

    }


}
