package com.nejmgames.gameobjects;



public class Explode extends Scrollable {

	private boolean available=true;
	private float runTime = 0;
	private final float maxRunTime = 2; // explode available after 2 seconds
	
    // When Plants' constructor is invoked, invoke the super (Scrollable) constructor
    public Explode(float x, float y, int width, int height, float scrollSpeed) {
    	super(x, y, width, height, scrollSpeed);
    }
    
    public void update(float delta){
    	super.update(delta);
    	if (runTime>maxRunTime){
    		runTime=0;
    		available = true;
    	}else{
    		runTime += delta;
    	}
    	
    	
    }
    
    public boolean isAvailable(){
    	return available;
    }
    
    public void setAvailable(boolean value){
    	available = value;
    }

	public void reset(float x, float y) {
		this.position.x=x;
		this.position.y=y;
		available=false;	
	}
	public float getRunTime(){
		return runTime;
	}

	public void onRestart(float scrollSpeed) {
		available=true;
		runTime = 0;
		velocity.y = scrollSpeed;
		
	}

}
