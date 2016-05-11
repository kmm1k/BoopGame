package com.boopgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.boopgame.helpers.AssetLoader;
import com.boopgame.screens.GameScreen;
import com.boopgame.screens.MenuScreen;

public class Boop extends Game {
	
	@Override
	public void create () {
		Gdx.app.log("SheepRush", "launching the game");
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
