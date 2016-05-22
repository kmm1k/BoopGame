package com.boopgame.menuscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by karl on 11.05.2016.
 */
public class MenuRenderer {

    private Stage stage;

    public MenuRenderer(Stage stage) {
        this.stage = stage;
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(119 / 255.0f, 202/ 255.0f, 230/ 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
}
