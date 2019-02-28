package dev.codenmore.ecjam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.level.Room;
import dev.codenmore.ecjam.level.tile.Tile;
import dev.codenmore.ecjam.screens.LoadingScreen;
import dev.codenmore.ecjam.screens.ScreenManager;
import dev.codenmore.ecjam.ui.UIDisplay;

public class Game extends ApplicationAdapter {
	
	// Globals
	public static final String TITLE = "Life Recycle";
	public static final float WIDTH = Tile.TILE_SIZE * Room.ROOM_WIDTH,
							HEIGHT = Tile.TILE_SIZE * Room.ROOM_HEIGHT + UIDisplay.HEIGHT;
	
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
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
