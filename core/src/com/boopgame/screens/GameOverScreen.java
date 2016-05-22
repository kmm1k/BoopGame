package com.boopgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.boopgame.Boop;
import com.boopgame.gameoverscreen.GameOverRenderer;
import com.boopgame.helpers.AssetLoader;

import io.socket.client.Socket;

/**
 * Created by karl on 11.05.2016.
 */
public class GameOverScreen extends AbstractScreen {


    private final Socket socket;
    private final Boop boop;
    private final String id;
    private final Stage stage;
    private final boolean won;
    private TextButton buttonExit;
    private TextButton buttonPlay;
    private final GameOverRenderer gameOverRenderer;

    public GameOverScreen(Boop boop, Socket socket, String id, boolean won) {
        super();
        this.socket = socket;
        this.boop = boop;
        this.id = id;
        this.won = won;
        Skin skin = AssetLoader.getSkin();
        FitViewport viewport = new FitViewport(gameWidth, gameHeight, cam);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        createUITable(skin);
        gameOverRenderer = new GameOverRenderer(stage);
    }

    private void createUITable(Skin skin) {
        Table table = new Table(skin);
        buttonExit = new TextButton("Exit", skin);
        buttonPlay = new TextButton("Play again", skin);

        table.setBounds(0, 0, gameWidth, gameHeight);
        buttonPlay.pad(10);
        buttonExit.pad(10);
        String endText = "";
        if (won) {
            endText = "You have won";
        }else{
            endText = "You have lost";
        }
        Label gameOverLable = new Label(endText, skin);
        table.add(gameOverLable);
        table.row();
        table.add(buttonPlay).prefWidth(gameWidth - 40).space(10);
        table.row();
        table.add(buttonExit).prefWidth(gameWidth - 40).space(10);
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        buttonPlay.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                boop.setScreen(new GameScreen(boop, socket, id));
                stage.dispose();
                dispose();
            }
        });
        buttonExit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
        });
    }


    @Override
    public void render(float delta) {
        gameOverRenderer.render(delta);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
