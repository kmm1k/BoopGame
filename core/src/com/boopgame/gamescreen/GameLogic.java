package com.boopgame.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.boopgame.gameobjects.BoopInterface;
import com.boopgame.gameobjects.EntityBoop;
import com.boopgame.gameobjects.PlayerBoop;
import com.boopgame.helpers.GameContactListener;
import com.boopgame.helpers.GameInputHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by karl on 11.05.2016.
 */
public class GameLogic {

    private PlayerBoop playerBoop;
    private final Socket socket;

    private boolean gameOver;
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean downPressed;
    private boolean upPressed;
    private boolean touchDown;
    private Vector2 touchPosition;
    private int screenWidth;
    private int screenHeight;
    private final World world;
    private final ArrayList<Body> bodyDeleteList;
    private boolean connectionMade;
    private ArrayList<BoopInterface> renderQueue;
    float updateTimer;
    private String id;

    public GameLogic(int width, int height, Socket socket) {
        connectionMade = false;
        this.socket = socket;
        screenWidth = width;
        screenHeight = height;
        initVariables();
        Gdx.input.setInputProcessor(new GameInputHandler(this));
        world = new World(new Vector2(0, 0), true);
        renderQueue = new ArrayList<BoopInterface>();
        SocketIOConnection(socket);

        GameContactListener contactListener = new GameContactListener(this);
        world.setContactListener(contactListener);
        bodyDeleteList = new ArrayList<Body>();
    }

    private void initVariables() {
        touchPosition = new Vector2(0, 0);
        gameOver = false;
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        touchDown = false;
        updateTimer = 0;
    }

    private void SocketIOConnection(Socket socket) {
        JSONObject playerData = new JSONObject();
        try {
            playerData.put("name", "username");
            socket.emit("addPlayer", playerData);
            socket.on("getPlayer", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String playerData = data.getString("player");
                        JSONObject player = new JSONObject(playerData);
                        makeGameObject(player);
                        connectionMade = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("map", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String mapData = data.getString("map");
                        JSONArray mapJSON = new JSONArray(mapData);
                        for (int i = 0; i < mapJSON.length(); i++) {
                            makeGameObject(mapJSON.getJSONObject(i));
                        }
                        Gdx.app.log("BoopGame", mapData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("removeEntity", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String idData = data.getString("id");
                        synchronized (renderQueue){
                            for (BoopInterface boop :
                                    renderQueue) {
                                if (boop.getId().equals(idData)) {
                                    bodyDeleteList.add(boop.getBody());
                                }
                            }
                        }

                        Gdx.app.log("BoopGame", id.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void makeGameObject(JSONObject jsonObject) throws JSONException {
        String id = jsonObject.get("id").toString();
        float size = Float.parseFloat(jsonObject.get("size").toString());
        float x = Float.parseFloat(jsonObject.getJSONObject("position").get("x").toString());
        float y = Float.parseFloat(jsonObject.getJSONObject("position").get("y").toString());
        float speed = 0;
        String name = "";
        try {
            name = jsonObject.get("name").toString();
            speed = Float.parseFloat(jsonObject.get("speed").toString());
        } catch (JSONException e) {

        }
        for (BoopInterface boop :
                renderQueue) {
            if (boop.getId().equals(id)) {
                boop.update(size, x, y, speed);
                return;
            }
        }
        if (speed == 0) {
            EntityBoop entity = new EntityBoop(size, x, y, world, id);
            synchronized (renderQueue) {
                renderQueue.add(entity);
            }
        } else {
            if (playerBoop == null){
                this.id = id;
                playerBoop = new PlayerBoop(size, x, y, world, id, speed, name);
                synchronized (renderQueue) {
                    renderQueue.add(playerBoop);
                }
            }else {
                PlayerBoop playerEntity = new PlayerBoop(size, x, y, world, id, speed, name);
                synchronized (renderQueue) {
                    renderQueue.add(playerEntity);
                }
            }

        }
    }

    private void updatePlayerPosition(float delta) {
        updateTimer += delta;
        if (updateTimer > .9f) {
            updateTimer = 0;
            socket.emit("updatePlayer", playerBoop.getData());
        }
    }

    public void update(float delta) {

        if (!connectionMade)
            return;
        synchronized (renderQueue) {
            updatePlayerPosition(delta);
        }
        deleteBodys();
        boolean actionInitiated = false;
        if (upPressed) {
            actionInitiated = true;
            playerBoop.moveUp();
        }
        if (downPressed) {
            actionInitiated = true;
            playerBoop.moveDown();
        }
        if (leftPressed) {
            actionInitiated = true;
            playerBoop.moveLeft();
        }
        if (rightPressed) {
            actionInitiated = true;
            playerBoop.moveRight();
        }
        if (touchDown) {
            actionInitiated = true;
            calculatePlayerMovementFromTouch();
        }
        if (!actionInitiated) {
            playerBoop.stopMoving();
        }

    }

    private void calculatePlayerMovementFromTouch() {
        float speedX = ((screenWidth / 2 - touchPosition.x) * -1) / (screenWidth / 2);
        float speedY = ((screenHeight / 2 - touchPosition.y)) / (screenHeight / 2);
        float speedSum = Math.abs(speedX) + Math.abs(speedY);
        speedX = speedX / speedSum;
        speedY = speedY / speedSum;
        Gdx.app.log("BoopGame", " " + speedX + " y " + speedY);
        playerBoop.movePlayerWithTouch(speedX, speedY);
    }

    public ArrayList<BoopInterface> getGameEntities() {
        synchronized (renderQueue){
            return renderQueue;
        }

    }

    public World getWorld() {
        return world;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setTouchDown(boolean touchDown) {
        this.touchDown = touchDown;
    }

    public void setTouchPosition(int screenX, int screenY) {
        this.touchPosition.set(screenX, screenY);
    }

    public void resize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    public void stepWorld() {
        world.step(1 / 60f, 6, 2);
    }

    public void addItemToDelete(Body body) {
        bodyDeleteList.add(body);
    }

    private void deleteBodys() {
        for (Body body :
                bodyDeleteList) {
            BoopInterface boop = (BoopInterface) body.getUserData();
            synchronized (renderQueue)
            {
                removeEntityFromServer(boop.getId());
                renderQueue.remove(boop);
            }
            //boop.dispose();
            world.destroyBody(body);
        }
        bodyDeleteList.clear();
    }

    private void removeEntityFromServer(String boopId) {
        JSONObject data = new JSONObject();
        try {
            data.put("id", boopId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("removeEntity", data);
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean getConnectionMade() {
        return connectionMade;
    }

    public String getId() {
        return id;
    }
}
