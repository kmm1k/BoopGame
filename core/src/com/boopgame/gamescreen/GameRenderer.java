package com.boopgame.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.boopgame.gameobjects.BoopInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by karl on 11.05.2016.
 */
public class GameRenderer {
    //private final Box2DDebugRenderer debugRenderer;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private float gameWidth;
    private float gameHeight;

    public GameRenderer(float gameWidth, float gameHeight, OrthographicCamera cam) {
        //Gdx.app.log("BoopGame", "initgamerender");
        this.cam = cam;
        this.shapeRenderer = new ShapeRenderer();
        //debugRenderer = new Box2DDebugRenderer();
    }

    public void render(float delta, HashMap<String, BoopInterface> renderQueue, World world, String id) {
        ClearScreenAndSetBackground();
        renderShapes(renderQueue, world, id);
    }

    private void renderShapes(HashMap<String, BoopInterface> renderQueue, World world, String id) {
        synchronized (renderQueue){
            for (Map.Entry<String, BoopInterface> object:
                    renderQueue.entrySet()) {
                shapeRenderer.setProjectionMatrix(cam.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 1, 0, 1);
                shapeRenderer.circle(object.getValue().getX(),
                        object.getValue().getY(),
                        object.getValue().getRadius());
                shapeRenderer.end();
                //Gdx.app.log("BoopGame", "IDS "+object.getId()+id);
                if (object.getKey().equals(id)){
                    cam.position.set(object.getValue().getX(), object.getValue().getY(), 0);
                    cam.zoom = 1 + (object.getValue().getRadius()/30);
                    cam.update();
                }
                //debugRenderer.render(world, cam.combined);
            }
        }

    }

    private void ClearScreenAndSetBackground() {
        Gdx.gl.glClearColor(0 / 255.0f, 0/ 255.0f, 0/ 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
