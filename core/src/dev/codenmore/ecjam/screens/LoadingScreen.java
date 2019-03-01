package dev.codenmore.ecjam.screens;

import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.entities.EntityFactory;
import dev.codenmore.ecjam.level.tile.TileFactory;

public class LoadingScreen extends Screen {

	public LoadingScreen() {
		// Initialize assets
		Assets.init();
	}
	
	@Override
	public void tick(float delta) {
		// Step loading
		if(Assets.step()) {
			// Init tiles
			TileFactory.init();
			// Init entities
			EntityFactory.init();
			// Done loading
			ScreenManager.swapScreen(new LevelSelectScreen());
		}
	}
	
	@Override
	public void render() {
		super.render();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
}
