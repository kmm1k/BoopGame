package com.boopgame.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;

import org.json.JSONObject;

/**
 * Created by karl on 15.05.2016.
 */
public interface BoopInterface {
    float getX();
    float getY();
    float getRadius();
    float getSize();
    String getId();
    void setRadius(float radius);
    void dispose();
    void update(float size, float x, float y, float speed);
    JSONObject getData();
    Body getBody();
    void setSize(float size);
}
