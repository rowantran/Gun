package com.upa.gun.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.upa.gun.GunGame;
import com.upa.gun.Settings;
public class DesktopLauncher {
	public static void main (String[] arg) {
		if (arg.length > 0) {
			Settings.PERCENT_SPAWN_CHANCE = Integer.parseInt(arg[0]);
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Gun";
		config.width = (int) Settings.RESOLUTION.x;
		config.height = (int) Settings.RESOLUTION.y;

		new LwjglApplication(new GunGame(), config);
	}
}