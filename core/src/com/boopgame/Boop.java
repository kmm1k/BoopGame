package com.boopgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.boopgame.helpers.AssetLoader;
import com.boopgame.screens.GameScreen;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Boop extends Game {

	private Socket socket;
    private String id;

    @Override
	public void create () {
		AssetLoader.load();
		//setScreen(new MenuScreen(this));
		makeConnection();
        configSocketEvents();
        setScreen(new GameScreen(this, socket, id));
	}



    public void configSocketEvents(){
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO", "Connected");
            }
        }).on("socketID", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    id = data.getString("id");
                    Gdx.app.log("SocketIO", "My ID: " + id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting ID");
                }
            }
        }).on("newPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    id = data.getString("id");
                    Gdx.app.log("SocketIO", "New Player Connect: " + id);
                }catch(JSONException e){
                    Gdx.app.log("SocketIO", "Error getting New PlayerID");
                }
            }
        });
    }

    private void makeConnection() {
		try {
			socket = IO.socket("http://localhost:8080");
			socket.connect();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void dispose () {
		super.dispose();
		AssetLoader.dispose();
        socket.disconnect();
	}
}
