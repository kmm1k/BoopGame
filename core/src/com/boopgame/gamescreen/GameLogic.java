package com.boopgame.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.boopgame.gameobjects.BoopInterface;
import com.boopgame.gameobjects.PlayerBoop;
import com.boopgame.gameobjects.WallBoop;
import com.boopgame.helpers.GameContactListener;
import com.boopgame.helpers.GameInputHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private HashMap<String, BoopInterface> renderQueue;
    private ArrayList<JSONObject> updateQueue;
    float updateTimer;
    private String id;
    private float deleteTimer;
    private List<String> removeQueue;
    private List<JSONObject> addQueue;

    public GameLogic(int width, int height, Socket socket) {
        connectionMade = false;
        this.socket = socket;
        screenWidth = width;
        screenHeight = height;
        initVariables();
        Gdx.input.setInputProcessor(new GameInputHandler(this));
        world = new World(new Vector2(0, 0), true);
        renderQueue = new HashMap<String, BoopInterface>();
        updateQueue = new ArrayList<JSONObject>();
        removeQueue = new ArrayList<String>();
        addQueue = new ArrayList<JSONObject>();
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
        deleteTimer = 0;
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
                        //make user game object
                        makePlayer(player);
                        connectionMade = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("player", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if (!connectionMade)
                        return;
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String playerData = data.getString("player");
                        JSONObject player = new JSONObject(playerData);
                        // draw out other players when they move
                        updateQueue.add(player);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("playerMap", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if (!connectionMade)
                        return;
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String playerMapData = data.getString("playerMap");
                        //Gdx.app.log("BoopGame", playerMapData);
                        JSONArray playerMap = new JSONArray(playerMapData);
                        // draw out other players when they move
                        for (int i = 0; i < playerMap.length(); i++) {
                            updateQueue.add((JSONObject) playerMap.get(i));
                        }
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
                        // when smb eats an entity, delete it from my game
                        removeQueue.add(idData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("getMap", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String mapString = data.getString("map");
                        JSONArray mapData = new JSONArray(mapString);
                        for (int i = 0; i < mapData.length(); i++) {
                                addNewEntity((JSONObject) mapData.get(i));
                        }
                        // when an entity is added add it to my game
                        //Gdx.app.log("BoopGame", id.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("addEntity", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String otherPlayerData = data.getString("otherPlayer");
                        JSONObject entityObject = new JSONObject(otherPlayerData);
                        // when an entity is added add it to my game
                        addQueue.add(entityObject);
                        // when an entity is added add it to my game
                        Gdx.app.log("BoopGame","add other entiuty");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addNewEntity(JSONObject JSONentity) {
        String name = "";
        float speed = 0;
        try {
            String type = JSONentity.get("type").toString();
            String id = JSONentity.get("id").toString();
            float size = Float.parseFloat(JSONentity.get("size").toString());
            float x = Float.parseFloat(JSONentity.getJSONObject("position").get("x").toString());
            float y = Float.parseFloat(JSONentity.getJSONObject("position").get("y").toString());
            name = JSONentity.get("name").toString();
            speed = Float.parseFloat(JSONentity.get("speed").toString());
            BoopInterface entity;

            if (type.equals("circle")){
                entity = new PlayerBoop(size, x, y, world, id, speed, name);
            } else {
                float angle = Float.parseFloat(JSONentity.get("angle").toString());
                float width = Float.parseFloat(JSONentity.get("width").toString());
                float height = Float.parseFloat(JSONentity.get("height").toString());
                entity = new WallBoop(size, x, y, world, id, angle, width, height);
            }
            synchronized (renderQueue) {
                renderQueue.put(id, entity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void drawOutOtherPlayer(JSONObject player) {
        try {
            String id = player.get("id").toString();
            float size = Float.parseFloat(player.get("size").toString());
            float x = Float.parseFloat(player.getJSONObject("position").get("x").toString());
            float y = Float.parseFloat(player.getJSONObject("position").get("y").toString());
            float speed = Float.parseFloat(player.get("speed").toString());
            //Gdx.app.log("BoopGame", renderQueue.toString());
            try {
                renderQueue.get(id).update(size, x, y, speed);
            } catch (NullPointerException e) {

            }
        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }

    private void removeEntity(String idData) {
        synchronized (renderQueue){
            renderQueue.remove(idData);
        }
    }


    private void makePlayer(JSONObject jsonObject) throws JSONException {

        try {
            String id = jsonObject.get("id").toString();
            float size = Float.parseFloat(jsonObject.get("size").toString());
            float x = Float.parseFloat(jsonObject.getJSONObject("position").get("x").toString());
            float y = Float.parseFloat(jsonObject.getJSONObject("position").get("y").toString());
            String name = jsonObject.get("name").toString();
            float speed = Float.parseFloat(jsonObject.get("speed").toString());
            if (playerBoop == null){
                this.id = id;
                playerBoop = new PlayerBoop(size, x, y, world, id, speed, name);
                synchronized (renderQueue) {
                    renderQueue.put(id, playerBoop);
                }
            }
        } catch (JSONException e) {

        }
    }

    private void updatePlayerPosition(float delta) {
        updateTimer += delta;
        if (updateTimer > .05f) {
            updateTimer = 0f;
            socket.emit("updatePlayer", playerBoop.getData());
        }
        deleteTimer += delta;
        if (deleteTimer > .1f){
            deleteTimer = 0f;
            deleteBodys();
        }
    }

    public void update(float delta) {

        if (!connectionMade)
            return;
        for (JSONObject obj :
                updateQueue) {
            drawOutOtherPlayer(obj);
        }
        updateQueue.clear();
        for (String id :
                removeQueue) {
            removeEntity(id);
        }
        removeQueue.clear();
        for (JSONObject obj :
                addQueue) {
            addNewEntity(obj);
        }
        addQueue.clear();
        updatePlayerPosition(delta);
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
        //Gdx.app.log("BoopGame", " " + speedX + " y " + speedY);
        playerBoop.movePlayerWithTouch(speedX, speedY);
    }

    public HashMap<String, BoopInterface> getGameEntities() {
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
        world.step(1 / 60f, 0, 1);
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
            boop.dispose();
            world.destroyBody(body);
        }
        bodyDeleteList.clear();
    }

    public void removeEntityFromServer(String boopId) {
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
