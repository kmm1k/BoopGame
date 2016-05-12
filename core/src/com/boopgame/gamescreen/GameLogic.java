package com.boopgame.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.boopgame.gameobjects.PlayerBoop;
import com.boopgame.helpers.GameInputHandler;

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
    private final GameInputHandler gameInputHandler;
    private int screenWidth;
    private int screenHeight;

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
        gameInputHandler = new GameInputHandler(this);
        Gdx.input.setInputProcessor(gameInputHandler);
        playerBoop = new PlayerBoop();
    }

    public void update(float delta) {
        //TODO: tell gameRenderer that I moved, cam.translate(x,y,z)
        if (upPressed){
            playerBoop.moveUp();
        }
        if (downPressed) {
            playerBoop.moveDown();
        }
        if (leftPressed) {
            playerBoop.moveLeft();
        }
        if (rightPressed) {
            playerBoop.moveRight();
        }
        if (touchDown) {
            calculatePlayerMovementFromTouch();
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
}
