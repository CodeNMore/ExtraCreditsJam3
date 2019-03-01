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
		level.tick(delta);
		ui.tick(delta);
		
		// Temporary: check for level editor entry
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
			ScreenManager.pushScreen(new LevelEditorScreen());
	}
	
	@Override
	public void render() {
		super.render();
		batch.begin();
		
		level.render(batch);
		ui.render(batch);
		
		// End batch
		batch.end();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
}
