package com.boopgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by karl on 15.05.2016.
 */
public abstract class AbstractScreen implements Screen {

    protected final float gameWidth;
    protected final float gameHeight;
    protected final float screenWidth;
    protected final float screenHeight;
    protected final OrthographicCamera cam;
    private boolean ORTHO_FLIPPED = false;

    public AbstractScreen() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        gameWidth = 540;
        gameHeight = screenHeight / (screenWidth / gameWidth);
        cam = new OrthographicCamera();
        cam.setToOrtho(ORTHO_FLIPPED, gameWidth, gameHeight);
    }

    @Override
    public void resize(int width, int height) {
        float gameHeight = height / (width / gameWidth);
        cam.setToOrtho(ORTHO_FLIPPED, gameWidth, gameHeight);
    }
}
