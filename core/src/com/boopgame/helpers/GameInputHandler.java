package com.boopgame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.boopgame.gamescreen.GameLogic;

/**
 * Created by karl on 11.05.2016.
 */
public class GameInputHandler implements InputProcessor {

    private final GameLogic gameLogic;

    public GameInputHandler(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                gameLogic.setUpPressed(true);
                break;
            case Input.Keys.DOWN:
                gameLogic.setDownPressed(true);
                break;
            case Input.Keys.RIGHT:
                gameLogic.setRightPressed(true);
                break;
            case Input.Keys.LEFT:
                gameLogic.setLeftPressed(true);
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                gameLogic.setUpPressed(false);
                break;
            case Input.Keys.DOWN:
                gameLogic.setDownPressed(false);
                break;
            case Input.Keys.RIGHT:
                gameLogic.setRightPressed(false);
                break;
            case Input.Keys.LEFT:
                gameLogic.setLeftPressed(false);
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        gameLogic.setTouchDown(true);
        gameLogic.setTouchPosition(screenX, screenY);
        Gdx.app.log("BoopGame", "down "+screenX+" "+screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        gameLogic.setTouchDown(false);
        gameLogic.setTouchPosition(screenX, screenY);
        Gdx.app.log("BoopGame", "up "+screenX+" "+screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        gameLogic.setTouchPosition(screenX, screenY);
        Gdx.app.log("BoopGame", "drag "+screenX+" "+screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
