package com.raumo0.snake.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.raumo0.snake.GameMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = GameMain.HEIGHT/2;
		config.width = GameMain.WIDTH/2;
		new LwjglApplication(new GameMain(), config);
	}
}
