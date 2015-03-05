package com.nejmgames.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.nejmgames.DiodonHelpers.AssetLoader;

public class Character {
	
    private Vector2 position;
    private Vector2 initialPosition;
    private Vector2 velocity;
    private float blowSpeed = 0.2f;
    private Vector2 acceleration;

    private float rotation;
    private int width;
    private int height;
    
    private float radius;
    private float maxRadius = 40;
    private float minRadius = 15;
    
    

    public Character(float radius, float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        this.radius = radius;
        initialPosition = new Vector2(x, y);
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
    }

    public void update(float delta) {
    	
    	Vector2 deltaVelocity = velocity.cpy().scl(delta*32); // *16 speed of animation
        position.add(deltaVelocity);
        velocity.sub(deltaVelocity);
    	if (radius<maxRadius){
    		radius *= 1+(blowSpeed*delta);
    	}else{
    		radius=maxRadius;
    	}
    	
    	

    }

    public void onClick(float screenX,float screenY) {
    	
    	if (radius > minRadius) {
    		radius *= (1- 0.02 * (100/radius));
    	}
    	ScrollHandler.doExplode(position.x,position.y);

    }
    
    public void onRestart(float radius, float x, float y) {
    	position.x = x;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        initialPosition.x=x;
        initialPosition.y=y;
        this.radius = radius;
        blowSpeed = 0.2f;
    }

    public float getRadius() {
        return radius;
    }
    
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }
    
    public float getInitialX() {
        return initialPosition.x;
    }

    public float getInitialY() {
        return initialPosition.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }
    
    public void setBlowSpeed (float speed){
    	blowSpeed = speed;
    }

	public void goRight() {
		if (position.x+velocity.x < (initialPosition.x * 1.49)) {
			velocity.x += (initialPosition.x * 0.5);
		}
			
	}

	public void goLeft() {
		if (position.x+velocity.x > (initialPosition.x * 0.51)) {
			velocity.x -= (initialPosition.x * 0.5);
		}	
	}
	
	public Circle getBoundingCircle(){
		return new Circle(getX(), getY(), getRadius());
	}

}
