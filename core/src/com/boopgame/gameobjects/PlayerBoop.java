package com.boopgame.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by karl on 11.05.2016.
 */
public class PlayerBoop implements BoopInterface {
    private final float INITIAL_PLAYER_SIZE = 20f;
    private final float INITIAL_PLAYER_SPEED = 3f;
    private final float playerSize;
    private final float playerSpeed;
    private final Vector2 playerPosition;

    public PlayerBoop() {
        playerSize = INITIAL_PLAYER_SIZE;
        playerSpeed = INITIAL_PLAYER_SPEED;
        playerPosition = new Vector2(100, 100);
    }

    public void eatBoop() {

    }

    @Override
    public float getX() {
        return playerPosition.x;
    }

    @Override
    public float getY() {
        return playerPosition.y;
    }

    @Override
    public float getRadius() {
        return playerSize/2;
    }

    public void moveUp() {
        playerPosition.set(playerPosition.x, playerPosition.y + playerSpeed);
    }

    public void moveDown() {
        playerPosition.set(playerPosition.x, playerPosition.y - playerSpeed);
    }

    public void moveLeft() {
        playerPosition.set(playerPosition.x - playerSpeed, playerPosition.y);
    }

    public void moveRight() {
        playerPosition.set(playerPosition.x + playerSpeed, playerPosition.y);
    }

    public void movePlayerWithTouch(float speedX, float speedY) {
        playerPosition.set(playerPosition.x + speedX*INITIAL_PLAYER_SPEED, playerPosition.y + speedY*INITIAL_PLAYER_SPEED);
    }
}
