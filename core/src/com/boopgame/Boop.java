package com.boopgame;

import com.badlogic.gdx.Game;
import com.boopgame.helpers.AssetLoader;
import com.boopgame.screens.MenuScreen;

public class Boop extends Game {
	
	@Override
	public void create () {
		AssetLoader.load();
		//setScreen(new MenuScreen(this));
		setScreen(new MenuScreen(this));
	}

	@Override
	public void dispose () {
		super.dispose();
		AssetLoader.dispose();
	}
}
