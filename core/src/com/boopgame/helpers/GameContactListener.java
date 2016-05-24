package com.boopgame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.boopgame.gameobjects.BoopInterface;
import com.boopgame.gameobjects.PlayerBoop;
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
        BoopInterface boopA = (BoopInterface) contact.getFixtureA().getBody().getUserData();
        BoopInterface boopB = (BoopInterface) contact.getFixtureB().getBody().getUserData();
        Gdx.app.log("BoopGameD", "a"+boopA.getSize()+""+boopB.getSize());
        float sizeA = boopA.getSize();
        float sizeB = boopB.getSize();
        if (sizeA > sizeB) {
            if (boopA instanceof PlayerBoop) {
                //boopA.setSize(size);
                //contact.getFixtureA().getShape().setRadius(size / 2);
                gameLogic.addItemsToEat(boopA.getId(), boopB.getId());
                gameLogic.addItemToDelete(boopB.getId());
            }
        } else if (sizeA == sizeB){
            Gdx.app.log("BoopGameD", "lolol a"+boopA.getSize()+""+boopB.getSize());

        } else {
            if (boopB instanceof PlayerBoop) {
                //boopB.setSize(size);
                //contact.getFixtureB().getShape().setRadius(size/2);
                gameLogic.addItemsToEat(boopB.getId(), boopA.getId());
                gameLogic.addItemToDelete(boopA.getId());
            }
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
