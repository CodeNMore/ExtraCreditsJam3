package dev.codenmore.ecjam.screens;

public class LevelSelectScreen extends Screen {

	public LevelSelectScreen() {
		
	}
	
	@Override
	public void tick(float delta) {
		ScreenManager.pushScreen(new GameScreen(1));
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
