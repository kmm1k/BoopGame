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
    private final EntityBoop entityBoop;
    private final EntityBoop entityBoop2;
    private final World world;
    private final ArrayList<Body> bodyDeleteList;
    private boolean connectionMade;

    public GameLogic(int width, int height, Socket socket) {
        connectionMade = false;
        this.socket = socket;
        screenWidth = width;
        screenHeight = height;
        initVariables();
        Gdx.input.setInputProcessor(new GameInputHandler(this));
        world = new World(new Vector2(0, 0), true);
        //socket code
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
                        Gdx.app.log("SocketIO", "My ID: " + player.get("size"));
                        float size = Float.parseFloat(player.get("size").toString());
                        float x = Float.parseFloat(player.getJSONObject("position").get("x").toString());
                        float y = Float.parseFloat(player.getJSONObject("position").get("y").toString());
                        String id = player.get("id").toString();
                        float speed = Float.parseFloat(player.get("speed").toString());
                        String name = player.get("name").toString();
                        playerBoop = new PlayerBoop(size, x, y, world, id, speed, name);
                        connectionMade = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Gdx.app.log("SocketIO", "Error getting ID");
                    }
                }
            }).on("map", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String mapData = data.getString("map");
                        Gdx.app.log("BoopGame", mapData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Gdx.app.log("SocketIO", "Error getting ID");
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


        entityBoop = new EntityBoop(10, 50, 50, world, "k");
        entityBoop2 = new EntityBoop(10, 300, 50, world, "b");
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
    }

    public void update(float delta) {
        if (!connectionMade)
            return;
        deleteBodys();
        //TODO: tell gameRenderer that I moved, cam.translate(x,y,z)
        boolean actionInitiated = false;
        if (upPressed){
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
        float speedX = ((screenWidth/2 - touchPosition.x)*-1)/(screenWidth/2);
        float speedY = ((screenHeight/2 - touchPosition.y))/(screenHeight/2);
        float speedSum = Math.abs(speedX) + Math.abs(speedY);
        speedX = speedX / speedSum;
        speedY = speedY / speedSum;
        Gdx.app.log("BoopGame", " "+speedX+" y "+speedY);
        playerBoop.movePlayerWithTouch(speedX, speedY);
    }

    public PlayerBoop getPlayerBoop() {
        return playerBoop;
    }

    public ArrayList<BoopInterface> getGameEntities() {
        ArrayList<BoopInterface> renderQueue = new ArrayList<BoopInterface>();
        renderQueue.add(playerBoop);
        renderQueue.add(entityBoop);
        renderQueue.add(entityBoop2);
        return renderQueue;
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
        world.step(1/60f, 6, 2);
    }

    public void addItemToDelete(Body body) {
        bodyDeleteList.add(body);
    }

    private void deleteBodys() {
        for (Body body :
                bodyDeleteList) {
            BoopInterface boop = (BoopInterface) body.getUserData();
            boop.dispose();
            world.destroyBody(body);
        }
        bodyDeleteList.clear();
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean getConnectionMade() {
        return connectionMade;
    }
}
