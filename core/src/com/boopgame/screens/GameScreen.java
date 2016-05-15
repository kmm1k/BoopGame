package com.boopgame.screens;

import com.boopgame.Boop;
import com.boopgame.gamescreen.GameLogic;
import com.boopgame.gamescreen.GameRenderer;

/**
 * Created by karl on 11.05.2016.
 */
public class GameScreen extends AbstractScreen {

    private final GameRenderer gameRenderer;
    private final GameLogic gameLogic;

    public GameScreen(Boop boop) {
        super();
        gameRenderer = new GameRenderer(gameWidth, gameHeight, cam);
        gameLogic = new GameLogic((int)screenWidth, (int)screenHeight);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(delta > .1f)
            delta = .1f;
        gameLogic.update(delta);
        gameRenderer.render(delta, gameLogic.getGameEntities());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        gameLogic.resize(width, height);
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
