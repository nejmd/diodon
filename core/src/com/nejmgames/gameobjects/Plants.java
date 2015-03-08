package com.nejmgames.gameobjects;

public class Plants extends Scrollable {

    // When Plants' constructor is invoked, invoke the super (Scrollable)
    // constructor
    public Plants(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);

    }

	public void onRestart( float y, float scrollSpeed) {
		velocity.y = scrollSpeed;
        reset(y);

		
	}

}