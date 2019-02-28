package dev.codenmore.ecjam.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

import dev.codenmore.ecjam.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		// Setup window information
		config.title = Game.TITLE + " by CodeNMore - Extra Credits Game Jam #3";
		config.width = (int) Game.WIDTH;
		config.height = (int) Game.HEIGHT;
		config.resizable = true;
		
		// Texture Packer - DEVELOPMENT CODE ONLY
		Settings s = new Settings();
		s.pot = true;
		s.combineSubdirectories = true;
		s.edgePadding = false;
		s.paddingX = 0;
		s.paddingY = 0;
		s.fast = false;
		
		// Change to core if no android project
		TexturePacker.process(s, "../assets_topack/", "../core/assets/textures/", "pack");
		
		new LwjglApplication(new Game(), config);
	}
}
