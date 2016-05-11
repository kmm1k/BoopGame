package com.boopgame.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.boopgame.gameobjects.PlayerBoop;

/**
 * Created by karl on 11.05.2016.
 */
public class GameRenderer {
    private OrthographicCamera cam;
    private boolean ORTHO_FLIPPED = false;
    private ShapeRenderer shapeRenderer;
    private float gameWidth;
    private float gameHeight;

    public GameRenderer(float gameWidth, float gameHeight) {
        //Gdx.app.log("BoopGame", "initgamerender");
        this.shapeRenderer = new ShapeRenderer();
        this.cam = new OrthographicCamera();
        cam.setToOrtho(ORTHO_FLIPPED, gameWidth, gameHeight);
    }

    public void render(float delta, PlayerBoop playerBoop) {
        ClearScreenAndSetBackground();
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.circle(playerBoop.getX(), playerBoop.getY(), playerBoop.getRadius());
        shapeRenderer.end();
    }

    private void ClearScreenAndSetBackground() {
        Gdx.gl.glClearColor(0 / 255.0f, 0/ 255.0f, 0/ 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void resize(float gameWidth, float gameHeight) {
        cam.setToOrtho(ORTHO_FLIPPED, gameWidth, gameHeight);
    }
}
