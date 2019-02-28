package dev.codenmore.ecjam.screens;

import dev.codenmore.ecjam.assets.Assets;

public class LoadingScreen extends Screen {

	public LoadingScreen() {
		// Initialize assets
		Assets.init();
	}
	
	@Override
	public void tick(float delta) {
		// Step loading
		if(Assets.step()) {
			// Done loading
			ScreenManager.swapScreen(new LevelSelectScreen());
		}
	}
	
	@Override
	public void render() {
		
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
}
