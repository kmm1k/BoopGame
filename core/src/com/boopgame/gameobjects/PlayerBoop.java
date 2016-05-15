package com.boopgame.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by karl on 11.05.2016.
 */
public class PlayerBoop implements BoopInterface {
    private final float INITIAL_PLAYER_SIZE = 20f;
    private final float INITIAL_PLAYER_SPEED = 300f;
    private final float playerSize;
    private final float playerSpeed;
    private final Vector2 playerPosition;
    private final Body body;

    public PlayerBoop(World world) {
        playerSize = INITIAL_PLAYER_SIZE;
        playerSpeed = INITIAL_PLAYER_SPEED;
        playerPosition = new Vector2(100, 100);


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(playerPosition.x, playerPosition.y);
        body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(INITIAL_PLAYER_SIZE/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();
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
        return playerSize / 2;
    }

    public void moveUp() {
        body.setLinearVelocity(body.getLinearVelocity().x, playerSpeed);
        playerPosition.set(body.getPosition().x, body.getPosition().y);
    }

    public void moveDown() {
        body.setLinearVelocity(body.getLinearVelocity().x, -playerSpeed);
        playerPosition.set(body.getPosition().x, body.getPosition().y);
    }

    public void moveLeft() {
        body.setLinearVelocity(-playerSpeed, body.getLinearVelocity().y);
        playerPosition.set(body.getPosition().x, body.getPosition().y);
    }

    public void moveRight() {
        body.setLinearVelocity(playerSpeed, body.getLinearVelocity().y);
        playerPosition.set(body.getPosition().x, body.getPosition().y);
    }

    public void movePlayerWithTouch(float speedX, float speedY) {
        body.setLinearVelocity(speedX * INITIAL_PLAYER_SPEED, speedY * INITIAL_PLAYER_SPEED);
        playerPosition.set(body.getPosition().x, body.getPosition().y);
        //playerPosition.set(playerPosition.x + speedX * INITIAL_PLAYER_SPEED, playerPosition.y + speedY * INITIAL_PLAYER_SPEED);
    }

    public void stopMoving() {
        body.setLinearVelocity(0f, 0f);
        playerPosition.set(body.getPosition().x, body.getPosition().y);
    }
}
