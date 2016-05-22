package com.boopgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.boopgame.Boop;
import com.boopgame.helpers.AssetLoader;
import com.boopgame.menuscreen.MenuRenderer;

import io.socket.client.Socket;


/**
 * Created by karl on 11.05.2016.
 */
public class MenuScreen extends AbstractScreen {
    private final FitViewport viewport;
    private final Stage stage;
    private final int savedHighScore;
    private final Skin skin;
    private final Boop boop;
    private final Socket socket;
    private final String id;
    private TextButton buttonExit;
    private TextButton buttonPlay;
    private final MenuRenderer menuRenderer;

    public MenuScreen(Boop boop, Socket socket, String id) {
        super();
        this.socket = socket;
        this.boop = boop;
        this.id = id;
        skin = AssetLoader.getSkin();
        Preferences prefs = Gdx.app.getPreferences("preferences");
        savedHighScore = prefs.getInteger("score", 0);
        viewport = new FitViewport(gameWidth, gameHeight, cam);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        createUITable();
        menuRenderer = new MenuRenderer(stage);
    }

    private void createUITable() {
        Table table = new Table(skin);
        buttonExit = new TextButton("Exit", skin);
        buttonPlay = new TextButton("Play", skin);

        table.setBounds(0, 0, gameWidth, gameHeight);
        buttonPlay.pad(10);
        buttonExit.pad(10);
        Label highScoreLabel = new Label("High score : " + savedHighScore, skin);
        table.add(highScoreLabel);
        table.row();
        table.add(AssetLoader.getTextfield()).prefWidth(gameWidth - 40).prefHeight(50f).space(10);
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
        menuRenderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {


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
        //stage.dispose();
    }
}
