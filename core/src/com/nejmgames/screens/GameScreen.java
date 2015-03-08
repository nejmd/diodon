package com.nejmgames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.nejmgames.DiodonHelpers.AssetLoader;
import com.nejmgames.DiodonHelpers.InputHandler;
import com.nejmgames.gameworld.GameRenderer;
import com.nejmgames.gameworld.GameWorld;


public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;
    private float runTime;
    public static float gameHeight,gameWidth;

    public GameScreen() {

    	AssetLoader.mp3Music.play();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        this.gameHeight = 408;
        this.gameWidth = screenWidth / (screenHeight / gameHeight);

        int midPointY = (int) (gameHeight / 2);
        int midPointX = (int) (gameWidth / 2);
        
        world = new GameWorld(midPointX, midPointY);
        new InputHandler(world, screenWidth / gameWidth, screenHeight / gameHeight);
       
        renderer = new GameRenderer(world, (int) gameWidth,(int) gameHeight, midPointX, midPointY);

       

    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(delta, runTime);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");
        if (world.isRunning()){
        	world.pause();
        }
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void dispose() {
    }

}
