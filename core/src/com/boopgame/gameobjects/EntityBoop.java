package com.boopgame.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by karl on 11.05.2016.
 */
public class EntityBoop implements BoopInterface {
    protected float entitySize;
    protected final Vector2 entityPosition;
    protected final Body body;
    protected final String id;
    protected float entitySpeed;


    public EntityBoop(float entitySize, float x, float y, World world, String id) {
        this.entitySpeed = 0;
        this.entitySize = entitySize;
        this.id = id;
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
        body.setUserData(this);
        body.createFixture(fixtureDef);

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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setRadius(float radius) {

        this.entitySize = radius*2;
        Gdx.app.log("BoopGame", entitySize+"");

    }

    @Override
    public void dispose() {
        entitySize = 0;
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
    public JSONObject getData() {
        JSONObject data = new JSONObject();
        try {
            data.put("id", id);
            data.put("x", entityPosition.x);
            data.put("y", entityPosition.y);
            data.put("size", entitySize);
            data.put("speed", entitySpeed);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public Body getBody() {
        return body;
    }
}
