package com.boopgame.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.boopgame.gameobjects.BoopInterface;

import java.util.ArrayList;

/**
 * Created by karl on 11.05.2016.
 */
public class GameRenderer {
    private final Box2DDebugRenderer debugRenderer;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private float gameWidth;
    private float gameHeight;

    public GameRenderer(float gameWidth, float gameHeight, OrthographicCamera cam) {
        //Gdx.app.log("BoopGame", "initgamerender");
        this.cam = cam;
        this.shapeRenderer = new ShapeRenderer();
        debugRenderer = new Box2DDebugRenderer();
    }

    public void render(float delta, ArrayList<BoopInterface> renderQueue, World world) {
        ClearScreenAndSetBackground();
        renderShapes(renderQueue);
        debugRenderer.render(world, cam.combined);
        cam.position.set(renderQueue.get(0).getX(), renderQueue.get(0).getY(), 0);
        cam.update();

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
