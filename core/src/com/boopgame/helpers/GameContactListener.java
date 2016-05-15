package com.boopgame.helpers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.boopgame.gameobjects.BoopInterface;
import com.boopgame.gamescreen.GameLogic;

import io.socket.client.Socket;

/**
 * Created by karl on 15.05.2016.
 */
public class GameContactListener implements ContactListener {

    private final GameLogic gameLogic;
    private final Socket socket;

    public GameContactListener(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        this.socket = gameLogic.getSocket();
    }

    @Override
    public void beginContact(Contact contact) {
        //float fixtureARadius = contact.getFixtureA().getShape().getRadius();
        //float fixtureBRadius = contact.getFixtureB().getShape().getRadius();
        BoopInterface boopA = (BoopInterface) contact.getFixtureA().getBody().getUserData();
        BoopInterface boopB = (BoopInterface) contact.getFixtureB().getBody().getUserData();
        float radius = boopA.getRadius() + boopB.getRadius();
        if (boopA.getRadius() > boopB.getRadius()) {
            boopA.setRadius(radius);
            contact.getFixtureA().getShape().setRadius(radius);
            //BoopInterface boop = (BoopInterface) contact.getFixtureA().getBody().getUserData();
            //Gdx.app.log("BoopGame", boop.getRadius()+"");
            //socket.emit()
            gameLogic.addItemToDelete(contact.getFixtureB().getBody());
        } else {
            boopB.setRadius(radius);
            contact.getFixtureB().getShape().setRadius(radius);
            gameLogic.addItemToDelete(contact.getFixtureA().getBody());
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
