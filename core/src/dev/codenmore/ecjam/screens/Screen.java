package dev.codenmore.ecjam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import dev.codenmore.ecjam.Game;

public abstract class Screen {

protected InputMultiplexer inputMultiplexer;
	
	//Display stuff
	protected OrthographicCamera cam;
	protected SpriteBatch batch;
	protected Viewport viewport;

	public Screen() {
		// Setup graphics
		cam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
		cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
		viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, cam);
		batch = new SpriteBatch();
				
		inputMultiplexer = new InputMultiplexer();
	}
	
	public abstract void tick(float delta);
	
	public void render() {
		// Update graphics
		cam.update();
		batch.setProjectionMatrix(cam.combined);
	}
	
	public void dispose() {
		batch.dispose();
	}
	
	public void show() {
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
	
	public abstract void pause();
	
	public abstract void resume();
	
	public InputMultiplexer getInputMultiplexer() {
		return inputMultiplexer;
	}
	
}
