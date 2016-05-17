package com.boopgame.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by karl on 17.05.2016.
 */
public class CircleBoop extends EntityBoop {
    protected final Body body;

    public CircleBoop(float entitySize, float x, float y, World world, String id) {
        super(entitySize, x, y, id);
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
        body.setUserData(this);
        body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();
    }

    @Override
    public void update(float size, float x, float y, float speed) {
        this.entitySize = size;
        this.entityPosition.x = x;
        this.entityPosition.y = y;
        this.entitySpeed = speed;
        body.setTransform(entityPosition, body.getAngle());
    }

    @Override
    public Body getBody() {
        return body;
    }
}
