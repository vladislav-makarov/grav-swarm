package com.mygdx.gravswarm.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.gravswarm.GameScreen;
import com.mygdx.gravswarm.GravSwarm;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "GravSwarm";
		new LwjglApplication(new GravSwarm(), config);
	}
}
