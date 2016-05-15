package com.boopgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.boopgame.Boop;
import com.boopgame.helpers.AssetLoader;


/**
 * Created by karl on 11.05.2016.
 */
public class MenuScreen extends AbstractScreen {
    private final FitViewport viewport;
    private final Stage stage;
    private final int savedHighScore;
    private final Skin skin;

    public MenuScreen(Boop boop) {
        super();
        skin = AssetLoader.getSkin();
        Preferences prefs = Gdx.app.getPreferences("preferences");
        savedHighScore = prefs.getInteger("score", 0);
        viewport = new FitViewport(gameWidth, gameHeight, cam);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        createUITable();
    }

    private void createUITable() {
        Table table = new Table(skin);
        TextButton buttonExit = new TextButton("Exit", skin);
        TextButton buttonPlay = new TextButton("Play", skin);

        table.setBounds(0, 0, gameWidth, gameHeight);
        buttonPlay.pad(10);
        buttonExit.pad(10);
        Label highScoreLabel = new Label("High score : " + savedHighScore, skin);
        table.add(highScoreLabel);
        table.row();
        table.add(buttonPlay).prefWidth(gameWidth - 40).space(10);
        table.row();
        table.add(buttonExit).prefWidth(gameWidth - 40).space(10);
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
