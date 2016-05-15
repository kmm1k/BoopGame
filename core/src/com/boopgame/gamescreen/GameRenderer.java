package com.boopgame.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.boopgame.gameobjects.BoopInterface;

import java.util.ArrayList;

/**
 * Created by karl on 11.05.2016.
 */
public class GameRenderer {
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private float gameWidth;
    private float gameHeight;

    public GameRenderer(float gameWidth, float gameHeight, OrthographicCamera cam) {
        //Gdx.app.log("BoopGame", "initgamerender");
        this.cam = cam;
        this.shapeRenderer = new ShapeRenderer();
    }

    public void render(float delta, ArrayList<BoopInterface> renderQueue) {
        ClearScreenAndSetBackground();
        renderShapes(renderQueue);
    }

    private void renderShapes(ArrayList<BoopInterface> renderQueue) {
        for (BoopInterface object:
             renderQueue) {
            shapeRenderer.setProjectionMatrix(cam.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 1, 0, 1);
            shapeRenderer.circle(object.getX(), object.getY(), object.getRadius());
            shapeRenderer.end();
        }
    }

    private void ClearScreenAndSetBackground() {
        Gdx.gl.glClearColor(0 / 255.0f, 0/ 255.0f, 0/ 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
