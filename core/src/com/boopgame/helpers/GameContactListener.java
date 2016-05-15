package com.boopgame.helpers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.boopgame.gamescreen.GameLogic;

/**
 * Created by karl on 15.05.2016.
 */
public class GameContactListener implements ContactListener {

    private final GameLogic gameLogic;

    public GameContactListener(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public void beginContact(Contact contact) {
        float fixtureARadius = contact.getFixtureA().getShape().getRadius();
        float fixtureBRadius = contact.getFixtureB().getShape().getRadius();
        if (fixtureARadius > fixtureBRadius) {
            contact.getFixtureA().getShape().setRadius(fixtureARadius + fixtureBRadius);
            gameLogic.addItemToDelete(contact.getFixtureB().getBody());
        } else {
            contact.getFixtureB().getShape().setRadius(fixtureBRadius + fixtureARadius);
            gameLogic.addItemToDelete(contact.getFixtureB().getBody());
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
