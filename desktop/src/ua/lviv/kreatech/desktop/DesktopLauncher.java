package ua.lviv.kreatech.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ua.lviv.kreatech.SpaceShip;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Graphics.DisplayMode displayMode = LwjglApplicationConfiguration.getDesktopDisplayMode();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.addIcon("spaceico32x32.png", Files.FileType.Internal);
		config.setFromDisplayMode(displayMode);
		//config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		//config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		config.fullscreen = false;
		config.vSyncEnabled = true;
		config.title = "SpaceShip";
		new LwjglApplication(new SpaceShip(), config);
	}
}
