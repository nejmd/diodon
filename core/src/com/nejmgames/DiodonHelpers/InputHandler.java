package com.nejmgames.DiodonHelpers;



import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.nejmgames.gameobjects.Character;
import com.nejmgames.gameworld.GameWorld;
import com.nejmgames.screens.GameScreen;
import com.nejmgames.ui.SimpleButton;

public class InputHandler implements GestureListener, InputProcessor {

	private Character myCharacter;
	private GameWorld myWorld;
	
	private static List<SimpleButton> mainMenuButtons, pauseMenuButtons, topMenuButtons;
	
	private SimpleButton playBtn, howToBtn, scoreBtn,quitBtn,resumeBtn,restartPauseBtn,mainMenuBtn, pauseBtn, muteBtn;
	private static SimpleButton restartBtn;
	private float scaleFactorX,scaleFactorY,mainMenuX,mainMenuY,pauseMenuX,pauseMenuY,muteX, topMenuY,restartBtnX,restartBtnY;
	

    // Ask for a reference to the Character when InputHandler is created.
    public InputHandler(GameWorld myWorld , float scaleFactorX, float scaleFactorY) {
        
        this.myWorld = myWorld;
        myCharacter = myWorld.getCharacter();
        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        im.addProcessor(this);

        Gdx.input.setInputProcessor(im);
        
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        
        this.mainMenuX = (GameScreen.gameWidth - AssetLoader.mainMenu.getRegionWidth())/2;
        this.mainMenuY = (GameScreen.gameHeight - AssetLoader.mainMenu.getRegionHeight())/2;

        
        mainMenuButtons = new ArrayList<SimpleButton>();
        playBtn = new SimpleButton(mainMenuX+57,mainMenuY+162, 125,40);
        mainMenuButtons.add(playBtn);
        howToBtn = new SimpleButton(mainMenuX+57,mainMenuY+210, 125,40);
        mainMenuButtons.add(howToBtn);
        scoreBtn = new SimpleButton(mainMenuX+57,mainMenuY+255, 125,40);
        mainMenuButtons.add(scoreBtn);
        quitBtn = new SimpleButton(mainMenuX+57,mainMenuY+305, 125,40);
        mainMenuButtons.add(quitBtn);
        
        this.pauseMenuX = (GameScreen.gameWidth - AssetLoader.pauseMenu.getRegionWidth())/2;
        this.pauseMenuY = (GameScreen.gameHeight - AssetLoader.pauseMenu.getRegionHeight())/2;
        
        pauseMenuButtons = new ArrayList<SimpleButton>();
        resumeBtn = new SimpleButton(pauseMenuX+35,pauseMenuY+45, 45,45);
        pauseMenuButtons.add(resumeBtn);
        restartPauseBtn = new SimpleButton(pauseMenuX+100,pauseMenuY+45, 45,45);
        pauseMenuButtons.add(restartPauseBtn);
        mainMenuBtn = new SimpleButton(pauseMenuX+165,pauseMenuY+45, 45,45);
        pauseMenuButtons.add(mainMenuBtn);
        
        this.muteX = (GameScreen.gameWidth - AssetLoader.mute.getRegionWidth());
        this.topMenuY = 5;
        
        topMenuButtons = new ArrayList<SimpleButton>();
        
        pauseBtn = new SimpleButton(0,topMenuY, 40,41);
        topMenuButtons.add(pauseBtn);
        muteBtn = new SimpleButton(muteX,topMenuY, 40,41);
        topMenuButtons.add(muteBtn);
        
        this.restartBtnX = (GameScreen.gameWidth - AssetLoader.restartBtn.getRegionWidth())/2;
        this.restartBtnY = 250;
        restartBtn = new SimpleButton(restartBtnX,restartBtnY,128 ,43);
        
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    	screenX = scaleX(screenX);
        screenY = scaleY(screenY);
    	//Gdx.app.log("InputHandler", "Touch Down "+screenX + " " + screenY);
        if (myWorld.isMenu()) {
        	
        	for (SimpleButton btn : mainMenuButtons) {
        		btn.isTouchDown(screenX, screenY);
            }
        	
        }else{
        	boolean treated = muteBtn.isTouchDown(screenX, screenY);
        	if (myWorld.isPaused()) {
        	for (SimpleButton btn : pauseMenuButtons) {
        		btn.isTouchDown(screenX, screenY);
            }
        }else if (myWorld.isReady()) {
            myWorld.start();
        }else if (myWorld.isRunning()) {
        	
        	for (SimpleButton btn : topMenuButtons) {
        		treated = treated  || btn.isTouchDown(screenX, screenY);
            }
        	if(!treated){
        		myCharacter.onClick(screenX,screenY);
        	}
        }
        
        if (myWorld.isGameOver() || myWorld.isHighScore()) {
        		restartBtn.isTouchDown(screenX, screenY);
        }
        }
        
        return true; // Return true to say we handled the touch.
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    	screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        //Gdx.app.log("InputHandler", "Touch Up "+screenX + " " + screenY);
        if (myWorld.isMenu()) {
            if (playBtn.isTouchUp(screenX, screenY)) {
            	Gdx.app.log("InputHandler", "Action: Play");
                myWorld.restart();
                return true;
            }else if (howToBtn.isTouchUp(screenX, screenY)) {
            	Gdx.app.log("InputHandler", "Action: HowTo");
            	AssetLoader.setHowTo(1);
            	myWorld.ready();
            	return true;
            }else if (scoreBtn.isTouchUp(screenX, screenY)) {
            	Gdx.app.log("InputHandler", "Action: Score");
            	AssetLoader.setShowScore(!AssetLoader.getShowScore());
            	return true;
            }else if (quitBtn.isTouchUp(screenX, screenY)) {
            	Gdx.app.log("InputHandler", "Action: Quit");
            	Gdx.app.exit();

            }
        }else {
        	if (muteBtn.isTouchUp(screenX, screenY)) {
            	Gdx.app.log("InputHandler", "Action: Mute/Unmute");
            	AssetLoader.setVolume((AssetLoader.isMute()?1:0));
            	return true;
            	
            }
        	if (myWorld.isPaused()) {
        	if (resumeBtn.isTouchUp(screenX, screenY)) {
            	Gdx.app.log("InputHandler", "Action: Resume");
            	myWorld.resume();
                return true;
            }else if (restartPauseBtn.isTouchUp(screenX, screenY)) {
            	Gdx.app.log("InputHandler", "Action: Restart");
            	myWorld.restart();
            	return true;
            	
            }else if (mainMenuBtn.isTouchUp(screenX, screenY)) {
            	Gdx.app.log("InputHandler", "Action: MainMenu");
            	myWorld.menu();
            	return true;
            }
        }else if (myWorld.isRunning()) {
        	if (pauseBtn.isTouchUp(screenX, screenY)) {
            	Gdx.app.log("InputHandler", "Action: Pause");
            	myWorld.pause();
                return true;
            }else if (muteBtn.isTouchUp(screenX, screenY)) {
            	Gdx.app.log("InputHandler", "Action: Mute/Unmute");
            	AssetLoader.setVolume((AssetLoader.isMute()?1:0));
            	return true;
            	
            }
        }else if (myWorld.isGameOver()||myWorld.isHighScore()){
        	if (restartBtn.isTouchUp(screenX, screenY)) {
            	Gdx.app.log("InputHandler", "Action: Restart");
            	myWorld.restart();
                return true;
            }
        }
        }
    	return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if (myWorld.isRunning()) {
		if(Math.abs(velocityX)>Math.abs(velocityY)){
			if(velocityX>0){
				myCharacter.goRight();
				AssetLoader.swipe1.play(AssetLoader.volume);
			}else{
				myCharacter.goLeft();
				AssetLoader.swipe2.play(AssetLoader.volume);
			}
		}else{
			if(velocityY>0){
				myCharacter.goDown();
			}else{                                  
				myCharacter.goUp();
			}
		}
		}
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }

    public static List<SimpleButton> getMainMenuButtons() {
        return mainMenuButtons;
    }
    
    public static List<SimpleButton> getPauseMenuButtons() {
        return pauseMenuButtons;
    }
    
    public static List<SimpleButton> getTopMenuButtons() {
        return topMenuButtons;
    }

	public static SimpleButton getRestartButton() {
		return restartBtn;
	}

}
