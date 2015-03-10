package com.nejmgames.gameworld;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.nejmgames.DiodonHelpers.InputHandler;
import com.nejmgames.DiodonHelpers.AssetLoader;
import com.nejmgames.TweenAccessors.Value;
import com.nejmgames.TweenAccessors.ValueAccessor;
import com.nejmgames.gameobjects.Character;
import com.nejmgames.gameobjects.Explode;
import com.nejmgames.gameobjects.Gift;
import com.nejmgames.gameobjects.Notif;
import com.nejmgames.gameobjects.Pipe;
import com.nejmgames.gameobjects.Plants;
import com.nejmgames.gameobjects.ScrollHandler;
import com.nejmgames.ui.SimpleButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;



public class GameRenderer {

    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    
    private SpriteBatch batcher;


    private int gameWidth, gameHeight;
    private float howToPlayTime = 0,mainMenuX,mainMenuY,pauseMenuX,pauseMenuY,topMenuY,muteX,restartBtnX,restartBtnY;
    private Character character;
    private ScrollHandler scroller;
    private Pipe[] pipes;
    private Explode[] explodes;
    private Gift[] gifts;
    private Plants[] leftPlants, rightPlants;
    
    private Notif notif;
    
    private TextureRegion  characterSprite,box,bg, leftPlantsSprite, rightPlantsSprite;

 // Tween stuff
    private TweenManager manager;
    private Value alpha = new Value();

    // Buttons
    private SimpleButton restartBtn;
    private List<SimpleButton> mainMenuButtons,pauseMenuButtons,topMenuButtons;

    public GameRenderer(GameWorld world, int gameWidth, int gameHeight, int midPointX, int midPointY) {
        myWorld = world;

        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.mainMenuX = (gameWidth - AssetLoader.mainMenu.getRegionWidth())/2;
        this.mainMenuY = (gameHeight - AssetLoader.mainMenu.getRegionHeight())/2;
        this.pauseMenuX = (gameWidth - AssetLoader.pauseMenu.getRegionWidth())/2;
        this.pauseMenuY = (gameHeight - AssetLoader.pauseMenu.getRegionHeight())/2;
        this.muteX = (gameWidth - AssetLoader.mute.getRegionWidth());
        this.topMenuY = 5;
        this.restartBtnX = (gameWidth - AssetLoader.restartBtn.getRegionWidth())/2;
        this.restartBtnY = 250;

        this.mainMenuButtons = InputHandler.getMainMenuButtons();
        this.pauseMenuButtons = InputHandler.getPauseMenuButtons();
        this.topMenuButtons = InputHandler.getTopMenuButtons();
        this.restartBtn = InputHandler.getRestartButton();
        
        cam = new OrthographicCamera();
        cam.setToOrtho(true, gameWidth, gameHeight);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        
     // Call helper methods to initialize instance variables
        initGameObjects();
        initAssets();
        setupTweens();
    }
    
    private void setupTweens() {
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad)
                .start(manager);
    }
    
    private void initGameObjects() {
    	character = myWorld.getCharacter();
        scroller = myWorld.getScroller();
        pipes = scroller.getPipes();
        leftPlants = scroller.getLeftPlants();
        rightPlants = scroller.getRightPlants();
        explodes=scroller.getExplodes();
        gifts=scroller.getGifts();
        notif= scroller.getNotif();
       
    }
    
    private void initAssets() {
        bg = AssetLoader.bg;
        characterSprite = AssetLoader.character;
        box = AssetLoader.box;
        leftPlantsSprite = AssetLoader.leftPlants;
        rightPlantsSprite = AssetLoader.rightPlants;
    }
    
    private void drawPipes() {
    	Color[] RGB_Values = {Color.RED,Color.GREEN,Color.BLUE};
    	for (int i = 0; i< scroller.getPipesCount(); i++){
    	
    	
    	shapeRenderer.setColor(RGB_Values[i % 3]);
        shapeRenderer.rect(pipes[i].getLeftRect().x, pipes[i].getLeftRect().y, pipes[i].getLeftRect().width, pipes[i].getLeftRect().height);
        shapeRenderer.rect(pipes[i].getRightRect().x, pipes[i].getRightRect().y, pipes[i].getRightRect().width, pipes[i].getRightRect().height);
    	}
        
        

        
    }
    
    private void drawPipes2() {
    	for (int i = 0; i< scroller.getPipesCount(); i++){
    		pipes[i].draw(batcher,box);
    	}
        
        

        
    }
    
    private void drawPlants() {
    	for (int i = 0; i< scroller.getPlantsCount(); i++){
    	
    		batcher.draw(leftPlantsSprite, leftPlants[i].getX(), leftPlants[i].getY(),leftPlants[i].getWidth(), leftPlants[i].getHeight());
    		batcher.draw(rightPlantsSprite, rightPlants[i].getX(), rightPlants[i].getY(),rightPlants[i].getWidth(), rightPlants[i].getHeight());
        
    	}
        
        

        
    }
    
    private void drawMainMenuUI() {
    	
        batcher.draw(AssetLoader.mainMenu, mainMenuX, mainMenuY);

        //debug
        AssetLoader.fontVerySmall.draw(batcher, "NovaScheme, Copyright © 2015", (gameWidth-AssetLoader.fontVerySmall.getBounds("NovaScheme, Copyright © 2015").width) / 2, (gameHeight - (AssetLoader.fontVerySmall.getBounds("NovaScheme, Copyright © 2015").height * 2)));
    }
    
    private void drawPauseMenuUI() {
    	
        batcher.draw(AssetLoader.pauseMenu, pauseMenuX, pauseMenuY);
        
      //debug
        /*for (SimpleButton button : pauseMenuButtons) {
            button.draw(shapeRenderer);
        }*/


    }
    
    private void drawNotif(){
    	if (notif.getAlpha() > 0){
    		AssetLoader.fontSmall.setColor(1,1,1,notif.getAlpha());
    		AssetLoader.fontSmall.draw(batcher, notif.getMsg(), notif.getX()-AssetLoader.fontSmall.getBounds(notif.getMsg()).width/2, notif.getY() - (AssetLoader.fontSmall.getBounds(notif.getMsg()).height / 2));
    		AssetLoader.fontSmall.setColor(1,1,1,1);	
    	}
    }
    
private void drawRestartUI() {
    	
        batcher.draw(AssetLoader.restartBtn, restartBtnX, restartBtnY);
        
      //debug
        //restartBtn.draw(shapeRenderer);
    }

 private void drawTopMenuUI(boolean drawPause) {
	 Color c = batcher.getColor();
	 batcher.setColor(c.r, c.g, c.b, .7f);	
	 if (AssetLoader.isMute()){
        batcher.draw(AssetLoader.unmute, muteX, topMenuY);
	 }else{
		 batcher.draw(AssetLoader.mute, muteX, topMenuY);
	 }
	 if (drawPause){
		 batcher.draw(AssetLoader.pause, 0, topMenuY);
	 }
	 
	 batcher.setColor(c.r, c.g, c.b, 1);	  
      //debug
        /*for (SimpleButton button : topMenuButtons) {
            button.draw(shapeRenderer);
        }*/


    }
    
    private void drawExplodes() {
    	for (int i = 0; i<scroller.getExplodesCount(); i++){
    		if (!explodes[i].isAvailable()){
    			// X-width/4 to keep it centered
    			batcher.draw(AssetLoader.explodeAnimation.getKeyFrame(explodes[i].getRunTime()), explodes[i].getX()-explodes[i].getWidth()/4, explodes[i].getY(),explodes[i].getWidth()/2, explodes[i].getHeight()/2);
    		}
    	}
        
        

        
    }
    
    private void drawGifts() {
    	for (int i = 0; i<scroller.getGiftsCount(); i++){
    		if (!gifts[i].isAvailable()){
    			batcher.draw(AssetLoader.gift, gifts[i].getX(), gifts[i].getY(),gifts[i].getWidth(), gifts[i].getHeight());
    		}
    	}
        
        

        
    }
    
    private void drawCharacter(float runTime) {
        batcher.draw(AssetLoader.characterAnimation.getKeyFrame(runTime), (character.getX()-character.getRadius())-(int)(character.getRadius()*0.44), (character.getY()-character.getRadius())+(int)(character.getRadius()*0.01), (int)((character.getRadius()*2)*1.4), (int)(character.getRadius()*2*1.4));
    }
    
    private void drawScore() {
    	// Convert integer into String
        String score = myWorld.getScore() + "";

        
        // Draw text
        AssetLoader.font.draw(batcher, score, (gameWidth-AssetLoader.font.getBounds(score).width) / 2, 50);
    }
    public void render(float delta, float runTime) {
    	
    	
    	
        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
       
     //For debug
     // Begin ShapeRenderer
        shapeRenderer.begin(ShapeType.Filled);

        // Draw Background color
        //shapeRenderer.setColor(0 / 255.0F, 0 / 255.0F, 0 / 255.0F, 1);
       // shapeRenderer.rect(0, 0, 800, 480);

        //drawPipes();
        
        // Draw Character
        //shapeRenderer.setColor(255 / 255.0f, 255 / 255.0f, 255 / 255.0f, 1);
        //shapeRenderer.circle(character.getX(), character.getY(), character.getRadius());

        
     
     // End ShapeRenderer
        shapeRenderer.end();
        
     // Begin SpriteBatch
        batcher.begin();
        // Disable transparency
        // This is good for performance when drawing images that do not require
        // transparency.
        batcher.disableBlending();
        batcher.draw(bg, 0, 0, gameWidth, gameHeight);

        // The character needs transparency, so we enable that again.
        batcher.enableBlending();
        
        drawExplodes();
        drawGifts();
        
        
        
        if (myWorld.isRunning() || myWorld.isReady() || myWorld.isGameOver() || myWorld.isHighScore() || myWorld.isPaused()) {
        	drawCharacter(runTime);
        	drawPipes2();
            drawPlants();
            
        	
        }
        
        if (myWorld.isRunning()){
        	drawTopMenuUI(true);
        }else if (myWorld.isPaused()){
        	drawTopMenuUI(false);
        }
        
        

        if (myWorld.isRunning()) {
        	if (AssetLoader.getHowTo() == 1) {
        		howToPlayTime += delta;
        		if (howToPlayTime < 3){
        			AssetLoader.fontSmall.draw(batcher, "Swipe to go", (gameWidth-AssetLoader.fontSmall.getBounds("Swipe to go").width) / 2, 110);
        			AssetLoader.fontSmall.draw(batcher, "right or left", (gameWidth-AssetLoader.fontSmall.getBounds("right or left").width) / 2, 150);
        		}else if (howToPlayTime < 6){
        			AssetLoader.fontSmall.draw(batcher, "Tap to keep", (gameWidth-AssetLoader.fontSmall.getBounds("Tap to keep").width) / 2, 110);
        			AssetLoader.fontSmall.draw(batcher, "the fish small", (gameWidth-AssetLoader.fontSmall.getBounds("the fish small").width) / 2, 150);
        		}else if (howToPlayTime < 9){
        			AssetLoader.fontSmall.draw(batcher, "Avoid obstacles", (gameWidth-AssetLoader.fontSmall.getBounds("Avoid obstacles").width) / 2, 110);
        			AssetLoader.fontSmall.draw(batcher, "to gain points", (gameWidth-AssetLoader.fontSmall.getBounds("to gain points").width) / 2, 150);
        		}else if (howToPlayTime < 12){
        			AssetLoader.fontSmall.draw(batcher, "Enjoy!", (gameWidth-AssetLoader.fontSmall.getBounds("Enjoy!").width) / 2, 110);
        		}else{
        			AssetLoader.setHowTo(0);
        			howToPlayTime = 0;
        		}
            }
        	
            drawScore();
            drawNotif();
        } else if (myWorld.isReady()) {
        	AssetLoader.fontSmall.draw(batcher, "Tap to start", (gameWidth-AssetLoader.fontSmall.getBounds("Tap to start").width) / 2, 50);

        } else if (myWorld.isMenu()) {
            drawMainMenuUI();
            if (AssetLoader.getShowScore()){
            	String highScore = AssetLoader.getHighScore() + "";
        		AssetLoader.fontSmall.draw(batcher, highScore, mainMenuX+57+125,mainMenuY+255+5 );
            }
        } else if (myWorld.isPaused()) {
            drawScore();
            drawNotif();
            drawPauseMenuUI();
        } else{ 
        	if (myWorld.isGameOver()) {

        		drawScore();
        		drawNotif();
        		AssetLoader.fontSmall.draw(batcher, "Game Over", (gameWidth-AssetLoader.fontSmall.getBounds("Game Over").width) / 2, 110);
        		String highScore = AssetLoader.getHighScore() + "";
        		AssetLoader.fontSmall.draw(batcher, "Highest Score: ", (gameWidth-AssetLoader.fontSmall.getBounds("Highest Score: ").width) / 2, 150);
        		AssetLoader.fontSmall.draw(batcher, highScore, (gameWidth-AssetLoader.fontSmall.getBounds(highScore).width) / 2, 190);
            
        	} else if (myWorld.isHighScore()) {
        		AssetLoader.fontSmall.draw(batcher, "Highest Score!", (gameWidth-AssetLoader.fontSmall.getBounds("Highest Score!").width) / 2, 110);
        		AssetLoader.fontSmall.draw(batcher, "Keep it up! ;)", (gameWidth-AssetLoader.fontSmall.getBounds("Keep it up! ;)").width) / 2, 150);
        		drawScore();
        		drawNotif();
        	}
        	drawRestartUI();
        	
        }
        

        
   
        
         
        
        

        
        // End SpriteBatch
        batcher.end();

     
     
        
        drawTransition(delta);
         
        
       
        
    }
    
    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(0.18f,0.18f, 0.18f, alpha.getValue());
            shapeRenderer.rect(0, 0, gameWidth, gameHeight);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
    }
    
}
