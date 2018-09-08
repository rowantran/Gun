package com.upa.gun.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.upa.gun.GunGame;
import com.upa.gun.Settings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Gun";
		config.width = (int) Settings.RESOLUTION.x;
		config.height = (int) Settings.RESOLUTION.y;

		new LwjglApplication(new GunGame(), config);
	}
}
