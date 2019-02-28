package dev.codenmore.ecjam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

public abstract class Screen {

protected InputMultiplexer inputMultiplexer;
	
	public Screen() {
		inputMultiplexer = new InputMultiplexer();
	}
	
	public abstract void tick(float delta);
	
	public abstract void render();
	
	public abstract void dispose();
	
	public void show() {
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	
	public abstract void resize(int width, int height);
	
	public abstract void pause();
	
	public abstract void resume();
	
	public InputMultiplexer getInputMultiplexer() {
		return inputMultiplexer;
	}
	
}
