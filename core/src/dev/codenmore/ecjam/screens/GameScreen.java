package dev.codenmore.ecjam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import dev.codenmore.ecjam.level.Level;
import dev.codenmore.ecjam.ui.UIDisplay;

public class GameScreen extends Screen {
	
	// Parts of the game
	private UIDisplay ui;
	private Level level;
	
	public GameScreen(int levelId) {
		level = new Level(this, levelId);
		ui = new UIDisplay(this);
	}
	
	@Override
	public void tick(float delta) {
		if(!ui.isShowingMessage()) {
			level.tick(delta);
		}
		
		// Always tick UI
		ui.tick(delta);
		
		// Check for escape
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
			ScreenManager.disposeScreen();
		
		// Temporary: check for level editor entry
		if(Gdx.input.isKeyJustPressed(Keys.P))
			ScreenManager.pushScreen(new LevelEditorScreen());
	}
	
	@Override
	public void render() {
		super.render();
		
		// Level has it's own batch/camera/viewport
		level.render();
		
		// Begin our batch
		batch.begin();
		
		// UI and messages
		ui.render(batch);
		
		// End batch
		batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		level.resize(width, height);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		level.dispose();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
	public Level getLevel() {
		return level;
	}
	
	public UIDisplay getUIDisplay() {
		return ui;
	}
	
}
