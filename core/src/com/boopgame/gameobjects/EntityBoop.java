package com.boopgame.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by karl on 11.05.2016.
 */
public class EntityBoop implements BoopInterface {
    private final float entitySize;
    private final Vector2 entityPosition;


    public EntityBoop(float entitySize, float x, float y) {
        this.entitySize = entitySize;
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
}
