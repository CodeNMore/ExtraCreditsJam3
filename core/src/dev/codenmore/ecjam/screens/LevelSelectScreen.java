package dev.codenmore.ecjam.screens;

public class LevelSelectScreen extends Screen {

	public LevelSelectScreen() {
		
	}
	
	@Override
	public void tick(float delta) {
		ScreenManager.pushScreen(new GameScreen(0));
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
