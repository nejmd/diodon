package com.nejmgames.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.nejmgames.DiodonHelpers.AssetLoader;
import com.nejmgames.gameworld.GameWorld;




public class ScrollHandler {

    private final static int pipesCount=6;
    private final static int plantsCount=2;
    private final static int explodesCount=100;
    private final static int giftsCount=8;
    private static Plants[] leftPlants = new Plants[plantsCount], rightPlants = new Plants[plantsCount];
    private static Pipe[] pipes = new Pipe[pipesCount];
    private static Explode[] explodes = new Explode[explodesCount];
    private static Gift[] gifts = new Gift[giftsCount];
    public static float SCROLL_SPEED;
    public static final int INITIAL_SCROLL_SPEED = 75; 
    public static final int MIN_PIPE_GAP = 75;
    public static final int DELTA_MAX_PIPE_GAP = 90; // MAX PIPE GAP - MIN PIPE GAP
    private int gameWidth;
    private static Random r = new Random();
    
    private Notif notif;
    
    public ScrollHandler(float yPos,int gameWidth) {
    	SCROLL_SPEED = INITIAL_SCROLL_SPEED;
    	for (int i = 0; i<giftsCount; i++){
    		gifts[i] = new Gift(0, 0 , 32, 32, INITIAL_SCROLL_SPEED);
    	}
    	
    	this.gameWidth = gameWidth;
    	for (int i = 0; i<pipesCount; i++){
            pipes[i] = new Pipe(0, 0 ,0,0,  gameWidth/2,SCROLL_SPEED);
    	}
    	
    	float prevY = 399;
    	for (int i = 0; i<plantsCount; i++){
    		leftPlants[i] = new Plants(-10, prevY- 398 , 40, 400, INITIAL_SCROLL_SPEED);
    		rightPlants[i] = new Plants(gameWidth-30, prevY - 398 , 40, 400, INITIAL_SCROLL_SPEED);
    		prevY = leftPlants[i].getY();
    	}
    	for (int i = 0; i<explodesCount; i++){
    		explodes[i] = new Explode(0, 0 , 82, 70, INITIAL_SCROLL_SPEED);
    	}
    	
    	
    	notif = new Notif(0,0,0,"");
    	
        
    }

    public void update(float delta) {
    	SCROLL_SPEED += (SCROLL_SPEED != 0?delta/2:0);
    	
    	
    	if (notif.getAlpha()> 0){
    		notif.setY(notif.getY()- (int)(0.8*delta*SCROLL_SPEED));
    		notif.setAlpha(notif.getAlpha() - delta);
    	}
    	
        // Update our objects
    	
    	
    	for (int i = 0; i<giftsCount; i++){
    		if (!gifts[i].isAvailable()){
    			gifts[i].update(delta);
    			gifts[i].setAvailable(gifts[i].isScrolledBottom());
    		}
    	}
    	
    	for (int i = 0; i<pipesCount; i++){
    		pipes[i].update(delta);
    		// Check if any of the pipes are scrolled bottom, and reset accordingly
    		if (pipes[i].isScrolledBottom()) {
    			int prevPipeId=(i+5)%6;
    			int gap = MIN_PIPE_GAP + r.nextInt(DELTA_MAX_PIPE_GAP);
    			int prevCenterPos = pipes[prevPipeId].getCenterPos();		
    			int newCenterPos = (r.nextInt(2000) % 2  + prevCenterPos + 2)%3 - 1; // newCenterPos != prevCenterPos
    			
    			pipes[i].reset(pipes[prevPipeId].getY() - gap,newCenterPos,gameWidth,AssetLoader.box.getRegionWidth(),AssetLoader.box.getRegionHeight());
    			int bonus = ((int)SCROLL_SPEED - gap/2)/5;
    			if (bonus>0){
    				if (prevCenterPos == 0 ){
    					addGift((int) (gameWidth/2 * (1-0.5 * newCenterPos ))- AssetLoader.gift.getRegionWidth()/2,pipes[prevPipeId].getY()-(gap/2) - AssetLoader.gift.getRegionHeight()/2 ,bonus);
    				} else if (newCenterPos == 0 ){
    					addGift((int) (gameWidth/2 * (1-0.5 * prevCenterPos ))- AssetLoader.gift.getRegionWidth()/2,pipes[prevPipeId].getY()-(gap/2) - AssetLoader.gift.getRegionHeight()/2 ,bonus);
                	
    				}
    			}
    			
            }
    	}
    	
    	for (int i = 0; i<plantsCount; i++){
    		
    		leftPlants[i].update(delta);
    		rightPlants[i].update(delta);
    		if (leftPlants[i].isScrolledBottom()) {
    			leftPlants[i].reset(leftPlants[(i+1)%2].getY() - 398);
            }
    		if (rightPlants[i].isScrolledBottom()) {
    			rightPlants[i].reset(rightPlants[(i+1)%2].getY() - 398);
            }
    	}
    	
    	for (int i = 0; i<explodesCount; i++){
    		if (!explodes[i].isAvailable()){
    			explodes[i].update(delta);
    		}
    	}
    	
    	

    }
    
    public void stop() {
    	SCROLL_SPEED = 0;
    }
    
    public static void doExplode(float x,float y){
    	int j=0;
    	while (!explodes[j].isAvailable()){j++;}
    	explodes[j].reset(x,y);
    	if (AssetLoader.remainingFartLock <= 0){
    		int tmpR=r.nextInt(1000);
    		if (tmpR < 100){
    			AssetLoader.farts[r.nextInt(AssetLoader.fartsCount)].play(AssetLoader.volume);
    			AssetLoader.remainingFartLock = 0.70f;
    		}else{
    			AssetLoader.pressures[r.nextInt(AssetLoader.pressuresCount)].play(AssetLoader.volume);
    			AssetLoader.remainingFartLock = 0.25f;
    		}
    		
        }
    }
    
    public static void addGift(float x,float y, int bonus){
    	int j=0;
    	while (!gifts[j].isAvailable()){j++;}
    	gifts[j].add(x,y,bonus);
    }

    // Return true if ANY pipe hits the bird.
    public boolean collides(Character character) {
    	boolean hit=false;
    	for (int i = 0; i<pipesCount; i++){ // /!\ We go till the end to calculate score
    		
    		float characterTail =  character.getY()+character.getRadius();
    		if (!pipes[i].isScored() && pipes[i].getY() > characterTail) {
                addScore(1);
                pipes[i].setScored(true);
                AssetLoader.coin.play(AssetLoader.volume);
            }
    		
    		hit = hit || pipes[i].collides(character);
    	}
    	
    	if (!hit){
    		for (int i = 0; i<giftsCount; i++){
    			if (!gifts[i].isAvailable() && !gifts[i].isScored() && gifts[i].collides(character)){
    				addScore(gifts[i].getBonus());
    				AssetLoader.giftFx.play(AssetLoader.volume);
    				notif.set((int) gifts[i].getX(),(int) gifts[i].getY(), 1, "+"+Integer.toString(gifts[i].getBonus()));
    				gifts[i].setScored(true);
    				gifts[i].setAvailable(true);
    				
    				
    			}
    		}
    	}
    	return hit;
       
    }

    private void addScore(int increment) {
        GameWorld.addScore(increment);
    }
    
    public void onRestart() {
    	SCROLL_SPEED = INITIAL_SCROLL_SPEED;
    	notif.onRestart();
    	
    	for (int i = 0; i<giftsCount; i++){
    		gifts[i].onRestart(INITIAL_SCROLL_SPEED);
    	}
    	
        float prevY = 0;
        int prevCenterPos=0;
        int newCenterPos;
        for (int i = 0; i<pipesCount; i++){
    		int gap = (i==0?0:MIN_PIPE_GAP + r.nextInt(DELTA_MAX_PIPE_GAP));
            
            	newCenterPos = (r.nextInt(2000) % 2  + prevCenterPos + 2)%3 - 1; // newCenterPos != prevCenterPos
            
            pipes[i].onRestart(0,prevY - gap,prevCenterPos, gameWidth/2, INITIAL_SCROLL_SPEED,AssetLoader.box.getRegionWidth(),AssetLoader.box.getRegionHeight());
            prevY = pipes[i].getY();
    		prevCenterPos = newCenterPos;
    		 
    	}
    	
    	prevY = 399;
    	for (int i = 0; i<plantsCount; i++){
    		leftPlants[i].onRestart(prevY - 398, INITIAL_SCROLL_SPEED);
    		rightPlants[i].onRestart(prevY - 398, INITIAL_SCROLL_SPEED);
    		prevY = leftPlants[i].getY();
    	}
    	for (int i = 0; i<explodesCount; i++){
    		explodes[i].onRestart(INITIAL_SCROLL_SPEED);
    	}
    	
    }

    
    public Pipe[] getPipes() {
        return pipes;
    }
    
    public int getPipesCount() {
        return pipesCount;
    }

	public int getPlantsCount() {
		 return plantsCount;
	}

	public Plants[] getLeftPlants() {
		return leftPlants;
	}
	
	public Plants[] getRightPlants() {
		return rightPlants;
	}
	
	public int getExplodesCount() {
        return explodesCount;
    }
	public Explode[] getExplodes(){
		return explodes;
	}
	
	public int getGiftsCount() {
        return giftsCount;
    }
	public Gift[] getGifts(){
		return gifts;
	}
	
	public Notif getNotif(){
		return notif;
	}

}
