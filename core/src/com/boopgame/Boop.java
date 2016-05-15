package com.boopgame;

import com.badlogic.gdx.Game;
import com.boopgame.helpers.AssetLoader;
import com.boopgame.screens.GameScreen;

public class Boop extends Game {
	
	@Override
	public void create () {
		AssetLoader.load();
		//setScreen(new MenuScreen(this));
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose () {
		super.dispose();
		AssetLoader.dispose();
	}
}
