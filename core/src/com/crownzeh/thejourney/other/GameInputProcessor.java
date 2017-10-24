package com.crownzeh.thejourney.other;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.crownzeh.thejourney.TheJourney;
import com.crownzeh.thejourney.screens.GameScreen;
import com.crownzeh.thejourney.screens.MenuScreen;
import com.crownzeh.thejourney.screens.WinScreen;
import com.crownzeh.thejourney.sprites.Aspect;

public class GameInputProcessor implements InputProcessor {
    private Aspect player;
    private TheJourney game;
    private SpriteBatch sb;

    public GameInputProcessor(SpriteBatch sb, Aspect player, TheJourney game) {
        this.game = game;
        this.player = player;
        this.sb = sb;

    }

    public void jump() {
        if (player.isNotInTheAir()) {
            //player.body.applyLinearImpulse(new Vector2(0, 9f), player.body.getWorldCenter(), true);
            player.body.setLinearVelocity(new Vector2(player.body.getLinearVelocity().x, 9.5f));
            TheJourney.assetManager.get("soundtrack/clips/jump.wav", Sound.class).play();
        } else if (!player.isNotInTheAir() && player.doubleJump == true) {
            player.doubleJump = false;
            //player.body.applyLinearImpulse(new Vector2(0, 5f), player.body.getWorldCenter(), true);
            player.body.setLinearVelocity(new Vector2(player.body.getLinearVelocity().x, 9.5f));
            TheJourney.assetManager.get("soundtrack/clips/jump.wav", Sound.class).play();

        }
    }

    public void nextLevel() {
        stopLevelMusic();
        if (game.getCurrentLevel().getId() < 9) {
            game.setCurrentLevel(game.getLevels().get(game.getCurrentLevel().getId()));
            game.setScreen(new GameScreen(game, game.getCurrentLevel().getLevelPath()));
        } else {
            game.setScreen(new WinScreen(sb, game));
        }
    }

    public void dash() {
        if (!player.isNotInTheAir() && player.isCastPossible()) {
            if (player.isTurnedRight())
                player.body.applyLinearImpulse(new Vector2(12f, 0f), player.body.getWorldCenter(), true);
            else
                player.body.applyLinearImpulse(new Vector2(-12f, 0f), player.body.getWorldCenter(), true);
            player.setSpellCooldown(2);
        }

    }

    public void muteOrUnmute() {
        if (TheJourney.assetManager.get("soundtrack/looped/city_theme.mp3", Music.class).isPlaying()) {
            TheJourney.assetManager.get("soundtrack/looped/city_theme.mp3", Music.class).pause();
        } else TheJourney.assetManager.get("soundtrack/looped/city_theme.mp3", Music.class).play();
    }

    public void sideArrowUp() {
        if (player.isNotInTheAir()) {
            player.body.setLinearVelocity(new Vector2(0, player.body.getLinearVelocity().y));
        }
    }

    public void moveLeft() {
        player.body.setLinearVelocity(new Vector2(-6, player.body.getLinearVelocity().y));
    }

    public void moveRight() {
        player.body.setLinearVelocity(new Vector2(6, player.body.getLinearVelocity().y));

    }

    public void restartLevel() {
        game.setScreen(new GameScreen(game, game.getCurrentLevel().getLevelPath()));
    }

    public void goToMainMenu() {
        stopLevelMusic();
        game.setScreen(new MenuScreen(sb, game));
    }

    public void stopLevelMusic() {
        TheJourney.assetManager.get("soundtrack/looped/city_theme.mp3", Music.class).stop();
        TheJourney.assetManager.get("soundtrack/looped/desert_theme.mp3", Music.class).stop();
        TheJourney.assetManager.get("soundtrack/looped/dungeon_theme.mp3", Music.class).stop();
    }

    @Override
    public boolean keyDown(int keycode) {
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


}
