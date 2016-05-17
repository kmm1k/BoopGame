package com.boopgame.gameobjects;

import com.badlogic.gdx.math.MathUtils;
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
    private float width;
    private float height;

    public WallBoop(float entitySize, float x, float y, World world, String id, float angle, float width, float height) {
        super(entitySize, x, y, id);
        this.entityAngle = angle;
        this.width = width;
        this.height = height;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        if (angle != 0) {
            bodyDef.position.set(entityPosition.x-(height/2), entityPosition.y+(width/2));
        }else {
            bodyDef.position.set(entityPosition.x+(width/2), entityPosition.y+(height/2));
        }

        body = world.createBody(bodyDef);
        PolygonShape wall = new PolygonShape();

        wall.setAsBox(width / 2, height / 2);
        body.createFixture(wall, 0.0f);
        body.setTransform(bodyDef.position, angle * MathUtils.degreesToRadians);
        wall.dispose();
    }

    public float getEntityAngle() {
        return entityAngle;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }


}
