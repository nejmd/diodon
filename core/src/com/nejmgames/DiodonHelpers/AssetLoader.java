package com.nejmgames.DiodonHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

public class AssetLoader {

    public static Texture giftTexture,restartBtnTexture,mainMenuTexture,pauseMenuTexture,unmuteTexture,muteTexture,pauseTexture,splashScreenTexture,texture,textureRepeat,characterTexture, explodeTexture ;


    public static TextureRegion gift,box,restartBtn,splashScreen,mainMenu,mute,unmute,pause,pauseMenu,character,bg, leftPlants, rightPlants;
    public static TextureRegion[] characterSprites = new TextureRegion[8];
    public static TextureRegion[] explodeSprites = new TextureRegion[6];
    public static Animation characterAnimation,explodeAnimation;

    
    public static Sound swipe1, swipe2, coin, giftFx;
    public static int fartsCount = 6;
    public static Sound[] farts = new Sound[fartsCount];
    public static float remainingFartLock = 0;
    public static int pressuresCount = 10;
    public static Sound[] pressures = new Sound[pressuresCount];
    
    public static Music mp3Music;
    
    public static float volume = 1.0f;
    
    
    public static BitmapFont font,fontSmall,fontVerySmall;
    
    public static Preferences prefs;
    public static boolean showScore = false;

    public static void load() {

    	
    
    	
    	
    	splashScreenTexture = new Texture(Gdx.files.internal("splashscreen.png"));
    	splashScreenTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    	splashScreen = new TextureRegion(splashScreenTexture, 0, 0, 280, 400);
    	
    	mainMenuTexture = new Texture(Gdx.files.internal("mainmenu.png"));
    	mainMenuTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    	mainMenu = new TextureRegion(mainMenuTexture, 0, 0, 240, 364);
    	mainMenu.flip(false, true);
    	
    	pauseMenuTexture = new Texture(Gdx.files.internal("pausemenu.png"));
    	pauseMenuTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    	pauseMenu = new TextureRegion(pauseMenuTexture, 0, 0, 240, 109);
    	pauseMenu.flip(false, true);
    	
    	muteTexture = new Texture(Gdx.files.internal("mute.png"));
    	muteTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    	mute = new TextureRegion(muteTexture, 0, 0, 40, 41);
    	mute.flip(false, true);
    	
    	unmuteTexture = new Texture(Gdx.files.internal("unmute.png"));
    	unmuteTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    	unmute = new TextureRegion(unmuteTexture, 0, 0, 40, 41);
    	unmute.flip(false, true);
    	
    	pauseTexture = new Texture(Gdx.files.internal("pause.png"));
    	pauseTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    	pause = new TextureRegion(pauseTexture, 0, 0, 40, 41);
    	pause.flip(false, true);
        
    	restartBtnTexture = new Texture(Gdx.files.internal("restart.png"));
    	restartBtnTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    	restartBtn = new TextureRegion(restartBtnTexture, 0, 0, 128, 43);
    	restartBtn.flip(false, true);
    	
    	texture = new Texture(Gdx.files.internal("sprites.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        textureRepeat = new Texture(Gdx.files.internal("sprites.png"));
        textureRepeat.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

        bg = new TextureRegion(texture, 0, 0, 964, 400);
        bg.flip(false, true);
        
        leftPlants = new TextureRegion(texture, 0, 500, 40, 400);
        leftPlants.flip(false, true);
        
        rightPlants = new TextureRegion(texture, 60, 500, 40, 400);
        rightPlants.flip(false, true);

        box = new TextureRegion(texture, 0, 400, 122, 49);
        box.flip(false, true);

        giftTexture = new Texture(Gdx.files.internal("gift.png"));
        giftTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        gift = new TextureRegion(giftTexture, 0, 0, 32, 32);
        gift.flip(false, true);
        
        characterTexture = new Texture(Gdx.files.internal("character.png"));
        characterTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        for (int i=0;i<8;i++){
        	characterSprites[i]=new TextureRegion(characterTexture, i*72, 0, 72, 70);
        	
        }
        characterAnimation = new Animation(0.03f, characterSprites);
        characterAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        
        character = new TextureRegion(texture, 1114, 0, 108, 101);
        
        explodeTexture = new Texture(Gdx.files.internal("explode.png"));
        explodeTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        for (int i=0;i<6;i++){
        	explodeSprites[i]=new TextureRegion(explodeTexture, i*82, 0, 82, 70);
        }
        explodeAnimation = new Animation(0.15f, explodeSprites);
        
        
        
        coin = Gdx.audio.newSound(Gdx.files.internal("coin.mp3"));
        giftFx = Gdx.audio.newSound(Gdx.files.internal("gift.mp3"));
        swipe1 = Gdx.audio.newSound(Gdx.files.internal("swipe1.mp3"));
        swipe2 = Gdx.audio.newSound(Gdx.files.internal("swipe2.mp3"));
        
        for (int i=0; i < fartsCount; i++){
        	farts[i] = Gdx.audio.newSound(Gdx.files.internal("fart"+i+".mp3"));
        }
        
        for (int i=0; i < pressuresCount; i++){
        	pressures[i] = Gdx.audio.newSound(Gdx.files.internal("pressure"+i+".mp3"));
        }
        
        font = new BitmapFont(Gdx.files.internal("font.fnt"),true);
        
        fontSmall = new BitmapFont(Gdx.files.internal("font.fnt"),true);
        fontSmall.setScale(.75f);
        
        fontVerySmall = new BitmapFont(Gdx.files.internal("font.fnt"),true);
        fontVerySmall.setScale(.25f);
        
     // Create (or retrieve existing) preferences file
        prefs = Gdx.app.getPreferences("FartyFish");

        // Provide default high score of 0
        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }
        
        if (!prefs.contains("howTo")) {
            prefs.putInteger("howTo", 1);
        }
        
        if (!prefs.contains("volume")) {
            prefs.putFloat("volume", 1);
        }else{
        	volume = prefs.getFloat("volume");
        	
        }
        
        mp3Music = Gdx.audio.newMusic(Gdx.files.internal("loop.mp3"));
    	mp3Music.setLooping(true);
        mp3Music.setVolume(volume/4);
        

    }
    
    
 // Receives an integer and maps it to the String highScore in prefs
    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }
    
 // Retrieves the current high score
    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }
    
    public static void setHowTo(int val) {
        prefs.putInteger("howTo", val);
        prefs.flush();
    }
    

    public static boolean getShowScore() {
        return showScore;
    }
    
    public static void setShowScore(boolean val) {
    	showScore = val;
    }
    

    public static int getHowTo() {
        return prefs.getInteger("howTo");
    }
    
    public static void setVolume(float val){
    	volume = val;
    	prefs.putFloat("volume", val);
        prefs.flush();
        mp3Music.setVolume(volume/4);
    }
    
    public static float getVolume(){
    	return volume;
    }
    
    public static boolean isMute(){
    	return volume == 0;
    }
    
    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
        for (int i=0; i < fartsCount; i++){
        	farts[i].dispose();
        }
        for (int i=0; i < pressuresCount; i++){
        	pressures[i].dispose();
        }
        swipe1.dispose();
        swipe2.dispose();
        coin.dispose();
        font.dispose();
        giftFx.dispose();
        mp3Music.dispose();
    }

}

