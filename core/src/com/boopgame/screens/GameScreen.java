package com.boopgame.screens;

import com.boopgame.Boop;
import com.boopgame.gamescreen.GameLogic;
import com.boopgame.gamescreen.GameRenderer;

import io.socket.client.Socket;

/**
 * Created by karl on 11.05.2016.
 */
public class GameScreen extends AbstractScreen {

    private final GameRenderer gameRenderer;
    private final GameLogic gameLogic;
    private String id;

    public GameScreen(Boop boop, Socket socket, String id) {
        super();
        this.id = id;
        gameLogic = new GameLogic((int)screenWidth, (int)screenHeight, socket, boop);
        gameRenderer = new GameRenderer(gameWidth, gameHeight, cam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (gameLogic.getConnectionMade()) {
            if (delta > .1f)
                delta = .1f;
            gameLogic.update(delta);
            gameRenderer.render(delta, gameLogic.getGameEntities(), gameLogic.getWorld(), gameLogic.getId());
            gameLogic.stepWorld();
        }
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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
