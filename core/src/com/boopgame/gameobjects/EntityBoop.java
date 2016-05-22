package com.boopgame.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by karl on 11.05.2016.
 */
public class EntityBoop implements BoopInterface {
    protected float entitySize;
    protected final Vector2 entityPosition;
    protected final String id;
    protected float entitySpeed;


    public EntityBoop(float entitySize, float x, float y, String id) {
        this.entitySpeed = 0;
        this.entitySize = entitySize;
        this.id = id;
        entityPosition = new Vector2(x, y);
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
    public float getSize() {
        return entitySize;
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
        return null;
    }


    @Override
    public void setSize(float size) {
        this.entitySize = size;
    }

    @Override
    public void setSpeed(float speed) {
        this.entitySpeed = speed;
    }
}
