package com.boopgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.boopgame.Boop;
import com.boopgame.gamescreen.GameLogic;
import com.boopgame.gamescreen.GameRenderer;

/**
 * Created by karl on 11.05.2016.
 */
public class GameScreen implements Screen {

    private final GameRenderer gameRenderer;
    private final GameLogic gameLogic;
    private final float gameWidth;
    private final float gameHeight;

    public GameScreen(Boop boop) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        gameWidth = 180;
        gameHeight = screenHeight / (screenWidth / gameWidth);
        gameRenderer = new GameRenderer(gameWidth, gameHeight);
        gameLogic = new GameLogic();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(delta > .1f)
            delta = .1f;
        gameLogic.update(delta);
        gameRenderer.render(delta, gameLogic.getPlayerBoop());
    }

    @Override
    public void resize(int width, int height) {
        float newGameHeight = height / (width / gameWidth);
        gameRenderer.resize(gameWidth, newGameHeight);
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
