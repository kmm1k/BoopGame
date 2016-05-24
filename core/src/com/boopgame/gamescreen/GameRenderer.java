package com.boopgame.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.boopgame.gameobjects.BoopInterface;
import com.boopgame.gameobjects.PlayerBoop;
import com.boopgame.gameobjects.WallBoop;
import com.boopgame.helpers.AssetLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by karl on 11.05.2016.
 */
public class GameRenderer {
    private final Box2DDebugRenderer debugRenderer;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private float gameWidth;
    private float gameHeight;
    private SpriteBatch batcher;

    public GameRenderer(float gameWidth, float gameHeight, OrthographicCamera cam) {
        //Gdx.app.log("BoopGame", "initgamerender");
        this.cam = cam;
        this.shapeRenderer = new ShapeRenderer();
        debugRenderer = new Box2DDebugRenderer();
        batcher = new SpriteBatch();
    }

    public void render(float delta, HashMap<String, BoopInterface> renderQueue, World world,
                       String id, boolean gameStarted) {
        ClearScreenAndSetBackground();
        renderShapes(renderQueue, id);
        debugRenderer.render(world, cam.combined);
        if (!gameStarted)
        renderMessage();
    }

    private void renderMessage() {
        batcher.begin();
        batcher.enableBlending();
        AssetLoader.scoreFont.draw(batcher, "waiting for another player", 50, 200);
        batcher.end();
    }

    private void renderShapes(HashMap<String, BoopInterface> renderQueue, String id) {
        synchronized (renderQueue){
            for (Map.Entry<String, BoopInterface> object:
                    renderQueue.entrySet()) {
                if (object.getValue() instanceof PlayerBoop){
                    updateCamera(id, object);
                    drawCircle(object.getValue());
                } if (object.getValue() instanceof WallBoop) {
                    drawBox((WallBoop) object.getValue());
                }
            }
        }

    }

    private void updateCamera(String id, Map.Entry<String, BoopInterface> object) {
        if (object.getKey().equals(id)) {
            cam.position.set(object.getValue().getX(), object.getValue().getY(), 0);
            cam.zoom = 1 + (object.getValue().getRadius()/30);
            cam.update();
        }
    }

    private void drawBox(WallBoop box) {
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.rect(box.getX(), box.getY(), 0, 0, box.getWidth(), box.getHeight(), 1, 1,
                box.getEntityAngle());
        shapeRenderer.end();
    }

    private void drawCircle(BoopInterface object) {
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.circle(object.getX(),
                object.getY(),
                object.getRadius());
        shapeRenderer.end();
        //Gdx.app.log("BoopGame", "IDS "+object.getId()+id);
    }

    private void ClearScreenAndSetBackground() {
        Gdx.gl.glClearColor(0 / 255.0f, 0/ 255.0f, 0/ 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
