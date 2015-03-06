package com.nejmgames.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Scrollable {
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    protected boolean isScrolledBottom;

    public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, scrollSpeed);
        this.width = width;
        this.height = height;
        isScrolledBottom = false;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
        velocity.y = ScrollHandler.SCROLL_SPEED;
        // If the Scrollable object is no longer visible:
        if (position.y > 408) { // gameHeight set in GameScreen
            isScrolledBottom = true;
        }
    }

    // Reset: Should Override in subclass for more specific behavior.
    public void reset(float newY) {
        position.y = newY;
        isScrolledBottom = false;
    }

    // Getters for instance variables
    public boolean isScrolledBottom() {
        return isScrolledBottom;
    }
    
    public void stop() {
        velocity.y = 0;
    }

    public float getTailY() {
        return position.y;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
