package com.upa.gun.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.upa.gun.GunGame;
import com.upa.gun.Settings;
public class DesktopLauncher {
	public static void main (String[] arg) {
		if (arg.length > 0) {
			Settings.PERCENT_SPAWN_CHANCE = Integer.parseInt(arg[0]);
		}

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("There is no Gun");
		config.setWindowedMode((int) Settings.RESOLUTION.x, (int) Settings.RESOLUTION.y);
		config.setIdleFPS(144);

		new Lwjgl3Application(new GunGame(), config);
	}
}