package dev.codenmore.ecjam.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import dev.codenmore.ecjam.Game;
import dev.codenmore.ecjam.level.Level;
import dev.codenmore.ecjam.ui.UIDisplay;

public class GameScreen extends Screen {

	// Display stuff
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private Viewport viewport;
	
	// Parts of the game
	private UIDisplay ui;
	private Level level;
	
	public GameScreen(int levelId) {
		// Setup graphics
		cam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, cam);
		batch = new SpriteBatch();

		level = new Level(this, levelId);
		ui = new UIDisplay(this);
	}
	
	@Override
	public void tick(float delta) {
		level.tick(delta);
		ui.tick(delta);
	}
	
	@Override
	public void render() {
		// Update graphics
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		
		level.render(batch);
		ui.render(batch);
		
		// End batch
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
}
