package com.boopgame.gamescreen;

import com.badlogic.gdx.Gdx;
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
    private final GameInputHandler gameInputHandler;

    public GameLogic() {
        gameOver = false;
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
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

}
