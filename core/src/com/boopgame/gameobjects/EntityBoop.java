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
public class EntityBoop implements BoopInterface {
    private final float entitySize;
    private final Vector2 entityPosition;
    private final Body body;


    public EntityBoop(float entitySize, float x, float y, World world) {
        this.entitySize = entitySize;
        entityPosition = new Vector2(x, y);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(entityPosition.x, entityPosition.y);
        body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(entitySize/2);

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


    @Override
    public float getX() {
        return entityPosition.x;
    }

    @Override
    public float getY() {
        return entityPosition.y;
    }

    @Override
    public float getRadius() {
        return this.entitySize/2;
    }
}
