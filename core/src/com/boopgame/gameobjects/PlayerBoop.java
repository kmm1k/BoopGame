package com.boopgame.gameobjects;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by karl on 11.05.2016.
 */
public class PlayerBoop extends EntityBoop {
    private float playerSpeed;

    public PlayerBoop(float entitySize, float x, float y, World world, String id, float speed, String name) {
        super(entitySize, x, y, world, id);
        playerSpeed = speed;
    }

    public void moveUp() {
        body.setLinearVelocity(body.getLinearVelocity().x, playerSpeed);
        entityPosition.set(body.getPosition().x, body.getPosition().y);
    }

    public void moveDown() {
        body.setLinearVelocity(body.getLinearVelocity().x, -playerSpeed);
        entityPosition.set(body.getPosition().x, body.getPosition().y);
    }

    public void moveLeft() {
        body.setLinearVelocity(-playerSpeed, body.getLinearVelocity().y);
        entityPosition.set(body.getPosition().x, body.getPosition().y);
    }

    public void moveRight() {
        body.setLinearVelocity(playerSpeed, body.getLinearVelocity().y);
        entityPosition.set(body.getPosition().x, body.getPosition().y);
    }

    public void movePlayerWithTouch(float speedX, float speedY) {
        body.setLinearVelocity(speedX * playerSpeed, speedY * playerSpeed);
        entityPosition.set(body.getPosition().x, body.getPosition().y);
        //playerPosition.set(playerPosition.x + speedX * INITIAL_PLAYER_SPEED, playerPosition.y + speedY * INITIAL_PLAYER_SPEED);
    }

    public void stopMoving() {
        body.setLinearVelocity(0f, 0f);
        entityPosition.set(body.getPosition().x, body.getPosition().y);
    }
}
