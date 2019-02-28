package dev.codenmore.ecjam.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codenmore.ecjam.screens.GameScreen;

public class Level {
	
	private GameScreen gameScreen;
	private Room curRoom;

	public Level(GameScreen gameScreen, int levelId) {
		this.gameScreen = gameScreen;
	}
	
	public void tick(float delta) {
		
	}
	
	public void render(SpriteBatch batch) {
		
	}
	
}
