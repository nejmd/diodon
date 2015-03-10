package com.nejmgames.gameworld;

import com.badlogic.gdx.Gdx;
import com.nejmgames.DiodonHelpers.AssetLoader;
import com.nejmgames.gameobjects.Character;
import com.nejmgames.gameobjects.ScrollHandler;

public class GameWorld {
    private Character character;
    private ScrollHandler scroller;
    private static int score = 0;
    
    public enum GameState {MENU,PAUSED,READY, RUNNING, GAMEOVER,HIGHSCORE}
    private GameState currentState;
    
    private int midPointX,midPointY;
    private float runTime = 0;

    
    public GameWorld(int midPointX, int midPointY) {
    	currentState = GameState.MENU;
    	character = new Character(20, midPointX, (int)(midPointY * 1.5) , 17, 12);
    	scroller = new ScrollHandler(midPointY + 66,2 * midPointX);
    	this.midPointX=midPointX;
    	this.midPointY=midPointY;
    }
    
    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }
    
    public void updateRunning(float delta) {
    	if (delta > .15f) {
            delta = .15f;
        }
    	
    	AssetLoader.remainingFartLock -= (AssetLoader.remainingFartLock>0?delta:0);
    	
    	
    	character.update(delta);
    	scroller.update(delta);
    	if (scroller.collides(character)) {
    	       // Clean up on game over
    	       scroller.stop();
    	       character.setBlowSpeed(0);
    	       currentState = GameState.GAMEOVER;
    	       //if (score > -1) {
    	       if (score > AssetLoader.getHighScore()) {
                   AssetLoader.setHighScore(score);
                   currentState = GameState.HIGHSCORE;
               }
    	}
    }
    
    public void update(float delta) {
    	runTime += delta;

        switch (currentState) {
        case READY:
            updateReady(delta);
            break;

        case RUNNING:
            updateRunning(delta);
            break;
        default:
            break;
        }
    }

    private void updateReady(float delta) {
        // Do nothing for now
    }
    
    public boolean isReady() {
        return currentState == GameState.READY;
    }
    
    public boolean isPaused() {
        return currentState == GameState.PAUSED;
    }
    public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}

    public void start() {
        currentState = GameState.RUNNING;
        score = 0;
        character.onRestart(20, midPointX, (int)(midPointY * 1.5));
        scroller.onRestart();
        
        
    }
    
    public void resume() {
        currentState = GameState.RUNNING;
    }
    
    public void pause() {
        currentState = GameState.PAUSED;
    }
    
    public void ready() {
        currentState = GameState.READY;
        
    }
    
    public void menu() {
        currentState = GameState.MENU;
        
    }

    public void restart() {
        score = 0;
        character.onRestart(20, midPointX, (int)(midPointY * 1.5));
        scroller.onRestart();
        currentState = GameState.READY;
    }


    
    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }
    
    
    
    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public Character getCharacter() {
        return character;

    }
    
    public ScrollHandler getScroller() {
        return scroller;
    }
    
    public int getScore() {
        return score;
    }

    public static void addScore(int increment) {
        score += increment;
    }

	
}
