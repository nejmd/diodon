package com.nejmgames.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Gift extends Scrollable {

	private boolean isScored;
	private boolean available=true;
	private int bonus = 0;
	private Rectangle boundingRect;


    


	public Gift(float x, float y, int width, int height, float scrollSpeed) {
    	super(x, y, width, height, scrollSpeed);
    	isScored = false;
    	boundingRect=new Rectangle (x, y, width, height);
    }
	
	@Override
	public void update(float delta){
		super.update(delta);
		boundingRect.setY(this.position.y);
	}
    
    public boolean isAvailable(){
    	return available;
    }
    
    public void setAvailable(boolean value){
    	available = value;
	
    }
    public void add(float x, float y, int bonus) {
		this.position.x=x;
		this.position.y=y;
		this.available=false;
		this.isScrolledBottom = false;
		this.bonus = bonus;
		isScored = false;
    	boundingRect.set(x, y, width, height);
	}



	public void onRestart(float scrollSpeed) {
		available=true;
		velocity.y = scrollSpeed;
		isScored = false;
		
	}
	public int getBonus(){
		return this.bonus;
	}
	
	
	public boolean collides(Character character) {
        if (position.y+height > character.getY() - character.getRadius()) {
            return Intersector.overlaps(character.getBoundingCircle(), this.getBoundingRect() );
        }
        return false;
    }
	public Rectangle getBoundingRect() {
		return boundingRect;
	}
	public boolean isScored() {
        return isScored;
    }
	public void setScored(boolean b) {
        isScored = b;
    }
}
