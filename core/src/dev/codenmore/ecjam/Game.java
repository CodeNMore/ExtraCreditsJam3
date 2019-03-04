package dev.codenmore.ecjam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.screens.LoadingScreen;
import dev.codenmore.ecjam.screens.ScreenManager;

public class Game extends ApplicationAdapter {
	
	// Globals
	public static final String TITLE = "Life Recycle";
	public static final float WIDTH = 800, HEIGHT = 600;
	public static final Color BG_COLOR = new Color(0.090196f, 0.098039f, 0.184313f, 1f);
	
	@Override
	public void create () {
		// Initialize assets
		Assets.init();
		// Start with loading screen
		ScreenManager.pushScreen(new LoadingScreen());
	}

	@Override
	public void render () {
		// Clear the screen
		Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, BG_COLOR.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Tick and render
		if(ScreenManager.getCurrentScreen() != null) {
			ScreenManager.getCurrentScreen().tick(Gdx.graphics.getDeltaTime());
			ScreenManager.getCurrentScreen().render();
		}
	}
	
	@Override
	public void dispose () {
		// Dispose all screens
		ScreenManager.disposeAllScreens();
		// Dispose all assets
		Assets.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		if(ScreenManager.getCurrentScreen() != null)
			ScreenManager.getCurrentScreen().resize(width, height);
	}

	@Override
	public void pause() {
		if(ScreenManager.getCurrentScreen() != null)
			ScreenManager.getCurrentScreen().pause();
	}

	@Override
	public void resume() {
		if(ScreenManager.getCurrentScreen() != null)
			ScreenManager.getCurrentScreen().resume();
	}
	
}
