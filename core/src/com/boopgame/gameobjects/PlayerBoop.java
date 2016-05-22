package com.boopgame.gameobjects;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by karl on 11.05.2016.
 */
public class PlayerBoop extends CircleBoop {

    public PlayerBoop(float entitySize, float x, float y, World world, String id, float speed, String name) {
        super(entitySize, x, y, world, id);
        this.entitySpeed = speed;
    }

    public void moveUp() {
        body.setLinearVelocity(body.getLinearVelocity().x, entitySpeed);
        entityPosition.set(body.getPosition().x, body.getPosition().y);
    }

    public void moveDown() {
        body.setLinearVelocity(body.getLinearVelocity().x, -entitySpeed);
        entityPosition.set(body.getPosition().x, body.getPosition().y);
    }

    public void moveLeft() {
        body.setLinearVelocity(-entitySpeed, body.getLinearVelocity().y);
        entityPosition.set(body.getPosition().x, body.getPosition().y);
    }

    public void moveRight() {
        body.setLinearVelocity(entitySpeed, body.getLinearVelocity().y);
        entityPosition.set(body.getPosition().x, body.getPosition().y);
    }

    public void movePlayerWithTouch(float speedX, float speedY) {
        body.setLinearVelocity(speedX * entitySpeed, speedY * entitySpeed);
        entityPosition.set(body.getPosition().x, body.getPosition().y);
    }

    public void stopMoving() {
        body.setLinearVelocity(0f, 0f);
        entityPosition.set(body.getPosition().x, body.getPosition().y);
    }
}
