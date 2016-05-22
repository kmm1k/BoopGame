package com.boopgame.gamescreen;

import com.badlogic.gdx.Gdx;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.socket.client.Socket;

/**
 * Created by karl on 22.05.2016.
 */
public class ServerObjectRemover extends Thread {

    private final ArrayList<String> removeQueue;
    private final int delayTime = 50;
    private final Socket socket;
    private String lobbyId;

    public ServerObjectRemover(Socket socket) {
        this.socket = socket;
        removeQueue = new ArrayList<String>();
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public void run() {
        while (true) {
            synchronized (removeQueue) {
                if (removeQueue.size() < 1) {
                    try {
                        removeQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    loopItems();
                }
            }

        }
    }

    private void loopItems() {
        synchronized (removeQueue) {
            for (String boopId :
                    removeQueue) {
                try {
                    //TODO: test if sleep is needed, if needed calculate delay based on player count
                    sleep(delayTime);
                    Gdx.app.log("BoopGame", "delay " + delayTime + "ms");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                removeEntityFromServer(boopId);
            }
            removeQueue.clear();
        }
    }

    public void addItemToRemove(String id) {
        synchronized (removeQueue) {
            removeQueue.add(id);
            removeQueue.notify();
        }
    }

    private void removeEntityFromServer(String boopId) {
        JSONObject data = new JSONObject();
        try {
            data.put("id", boopId);
            data.put("lobbyId", lobbyId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("removeEntity", data);
    }
}
