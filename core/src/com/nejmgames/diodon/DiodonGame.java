package com.nejmgames.diodon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.nejmgames.DiodonHelpers.AssetLoader;
import com.nejmgames.screens.SplashScreen;

public class DiodonGame extends Game {
	@Override
    public void create() {
        AssetLoader.load();
        setScreen(new SplashScreen(this));
    }
	
	@Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
