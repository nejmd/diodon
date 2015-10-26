package com.nejmgames.diodon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nejmgames.diodon.DiodonGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Diodon";
        config.width = 272;
        config.height = 408;
		new LwjglApplication(new DiodonGame(), config);
	}
}
