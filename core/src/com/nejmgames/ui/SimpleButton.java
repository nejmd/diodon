package com.nejmgames.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


public class SimpleButton {

    private float x, y, width, height;



    private Rectangle bounds;

    private boolean isPressed = false;
    

    public SimpleButton(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
   


        bounds = new Rectangle(x, y, width, height);

    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }
    
    // for debug
    public void draw(ShapeRenderer shapeRenderer) {
    	shapeRenderer.rect(x, y, width, height);
    }
    

    public boolean isTouchDown(int screenX, int screenY) {

        if (bounds.contains(screenX, screenY)) {
        	
            isPressed = true;
            return true;
        }

        return false;
    }

    public boolean isTouchUp(int screenX, int screenY) {
        
        // It only counts as a touchUp if the button is in a pressed state.
        if (bounds.contains(screenX, screenY) && isPressed) {
            isPressed = false;
            return true;
        }
        
        // Whenever a finger is released, we will cancel any presses.
        isPressed = false;
        return false;
    }
    


}
