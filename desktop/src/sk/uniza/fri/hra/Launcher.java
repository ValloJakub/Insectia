package sk.uniza.fri.hra;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Launcher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Insectia");
		config.setWindowedMode(1280, 720);
		config.setResizable(false);
		new Lwjgl3Application(new Insectia(), config);
	}
}
