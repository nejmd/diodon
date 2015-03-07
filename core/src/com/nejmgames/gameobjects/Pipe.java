package com.nejmgames.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Pipe extends Scrollable {

	private Rectangle leftRect, rightRect;
	
	int leftWcount,rightWcount,Hcount; //left Width count, ...

	
    private Random r;
    private int middlePosition;
    private int center;
    

	private int centerPos = 0; // = -1 | 0 | 1 <=> left | center | right
    
    private boolean isScored = false;

    // When Pipe's constructor is invoked, invoke the super (Scrollable) constructor
    public Pipe(float x, float y, int width, int height,int middlePosition, float scrollSpeed ) {
        super(x, y, width, height, scrollSpeed);
        r = new Random();
        this.middlePosition = middlePosition;
        leftRect = new Rectangle();
        rightRect = new Rectangle();
   
    }

    

    public void reset(float newY, int centerPos, int gameWidth ,  int textureRegionW, int textureRegionH) {
    	
        // Change the height to a random number
    	this.width = r.nextInt(30) + 40;
    	this.height = ((r.nextInt(3000)%3)* 49) + 49;
    	
        // Call the reset method in the superclass (Scrollable)
        super.reset(newY - height);
        
        this.centerPos = centerPos;
        this.center = (int) (middlePosition * (1+0.5 * centerPos ));
        
        leftRect.set(getX(), getY(),getCenter() - getWidth()/2,getHeight());
        rightRect.set(getCenter() + getWidth()/2, getY(),
        		middlePosition*2 - getCenter() - getWidth()/2, getHeight());
        
        leftWcount = (int)((leftRect.width / textureRegionW) + 1);
        rightWcount = (int)((rightRect.width / textureRegionW) + 1);
        Hcount = (int)(rightRect.height / textureRegionH);
        
        isScored = false;
        
     
    }
    
    @Override
    public void update(float delta ) {
    	super.update(delta);
    	leftRect.y = this.position.y;
    	rightRect.y = this.position.y;
    	
    	
    	
    }
    
    public boolean collides(Character character) {
        if (position.y+height > character.getY() - character.getRadius()) {
            return (Intersector.overlaps(character.getBoundingCircle(), getLeftRect()) || Intersector.overlaps(character.getBoundingCircle(), getRightRect()));
        }
        return false;
    }
    
    public int getCenter(){
    	return center;
    }
    public Rectangle getLeftRect (){
    	return leftRect;
    }
    public Rectangle getRightRect (){
    	return rightRect;
    }
    
    public int getCenterPos() {
		return centerPos;
	}
    
    public boolean isScored() {
        return isScored;
    }
    

    public void setScored(boolean b) {
        isScored = b;
    }

	public void onRestart(float x,float y,int centerPos,int gameWidth, float scrollSpeed ,  int textureRegionW, int textureRegionH) {
		velocity.y = scrollSpeed;
		position.x=x;
		position.y=y;
        this.reset(y,centerPos,gameWidth,textureRegionW,textureRegionH);

	}

	public void draw(SpriteBatch batcher, TextureRegion box) {
		
		//Draw Left Side
		int currentX = (int)(leftRect.width - leftWcount*box.getRegionWidth());
		int currentY= (int)leftRect.y;
		
		
		for (int i=0;i<leftWcount;i++){
			for (int j=0;j<Hcount;j++){
				batcher.draw(box, currentX, currentY,box.getRegionWidth(), box.getRegionHeight());
				currentY += box.getRegionHeight();
			}
			currentX += box.getRegionWidth();
			currentY= (int)leftRect.y;
		}
		
		//Draw Right Side
		currentX= (int)rightRect.x;
		currentY= (int)rightRect.y;
		
		for (int i=0;i<rightWcount;i++){
			for (int j=0;j<Hcount;j++){
				batcher.draw(box, currentX, currentY,box.getRegionWidth(), box.getRegionHeight());
				currentY += box.getRegionHeight();
			}
			currentX += box.getRegionWidth();
			currentY= (int)rightRect.y;
		}
	}

  }


