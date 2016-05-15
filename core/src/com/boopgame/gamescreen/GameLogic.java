package com.boopgame.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.boopgame.gameobjects.BoopInterface;
import com.boopgame.gameobjects.EntityBoop;
import com.boopgame.gameobjects.PlayerBoop;
import com.boopgame.helpers.GameContactListener;
import com.boopgame.helpers.GameInputHandler;

import java.util.ArrayList;

/**
 * Created by karl on 11.05.2016.
 */
public class GameLogic {

    private final PlayerBoop playerBoop;

    private boolean gameOver;
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean downPressed;
    private boolean upPressed;
    private boolean touchDown;
    private Vector2 touchPosition;
    private int screenWidth;
    private int screenHeight;
    private final EntityBoop entityBoop;
    private final World world;
    private final ArrayList<Body> bodyDeleteList;

    public GameLogic(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        touchPosition = new Vector2(0, 0);
        gameOver = false;
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        touchDown = false;
        GameInputHandler gameInputHandler = new GameInputHandler(this);
        Gdx.input.setInputProcessor(gameInputHandler);
        world = new World(new Vector2(0, 0), true);
        playerBoop = new PlayerBoop(world);
        entityBoop = new EntityBoop(10, 50, 50, world);
        GameContactListener contactListener = new GameContactListener(this);
        world.setContactListener(contactListener);
        bodyDeleteList = new ArrayList<Body>();
    }

    public void update(float delta) {
        deleteBodys();
        //TODO: tell gameRenderer that I moved, cam.translate(x,y,z)
        boolean actionInitiated = false;
        if (upPressed){
            actionInitiated = true;
            playerBoop.moveUp();
        }
        if (downPressed) {
            actionInitiated = true;
            playerBoop.moveDown();
        }
        if (leftPressed) {
            actionInitiated = true;
            playerBoop.moveLeft();
        }
        if (rightPressed) {
            actionInitiated = true;
            playerBoop.moveRight();
        }
        if (touchDown) {
            actionInitiated = true;
            calculatePlayerMovementFromTouch();
        }
        if (!actionInitiated) {
            playerBoop.stopMoving();
        }

    }

    private void calculatePlayerMovementFromTouch() {
        float speedX = ((screenWidth/2 - touchPosition.x)*-1)/(screenWidth/2);
        float speedY = ((screenHeight/2 - touchPosition.y))/(screenHeight/2);
        float speedSum = Math.abs(speedX) + Math.abs(speedY);
        speedX = speedX / speedSum;
        speedY = speedY / speedSum;
        Gdx.app.log("BoopGame", " "+speedX+" y "+speedY);
        playerBoop.movePlayerWithTouch(speedX, speedY);
    }

    public PlayerBoop getPlayerBoop() {
        return playerBoop;
    }

    public ArrayList<BoopInterface> getGameEntities() {
        ArrayList<BoopInterface> renderQueue = new ArrayList<BoopInterface>();
        renderQueue.add(playerBoop);
        renderQueue.add(entityBoop);
        return renderQueue;
    }

    public World getWorld() {
        return world;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setTouchDown(boolean touchDown) {
        this.touchDown = touchDown;
    }

    public void setTouchPosition(int screenX, int screenY) {
        this.touchPosition.set(screenX, screenY);
    }

    public void resize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    public void stepWorld() {
        world.step(1/60f, 6, 2);
    }

    public void addItemToDelete(Body body) {
        bodyDeleteList.add(body);
    }

    private void deleteBodys() {
        for (Body body :
                bodyDeleteList) {
            world.destroyBody(body);
        }
        bodyDeleteList.clear();
    }
}
