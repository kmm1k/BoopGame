package com.boopgame.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by karl on 17.05.2016.
 */
public class WallBoop extends EntityBoop {

    private final Body body;
    private float entityAngle;

    public WallBoop(float entitySize, float x, float y, World world, String id, float angle, float hy, float hx) {
        super(entitySize, x, y, world, id);
        this.entityAngle = angle;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(entityPosition.x, entityPosition.y);
        body = world.createBody(bodyDef);
        PolygonShape wall = new PolygonShape();
        wall.setAsBox(100.0f, 10.0f);


        body.createFixture(wall, 0.0f);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        wall.dispose();
    }



}
