package dev.codenmore.ecjam.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codenmore.ecjam.Game;
import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.level.tile.Tile;
import dev.codenmore.ecjam.screens.GameScreen;

public class UIDisplay {
	
	// Globals
	public static final float WIDTH = Game.WIDTH, HEIGHT = Tile.TILE_SIZE * 2,
							YOFFSET = Game.HEIGHT - HEIGHT;
	
	private GameScreen gameScreen;
	
	public UIDisplay(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}
	
	public void tick(float delta) {
		
	}
	
	public void render(SpriteBatch batch) {
		batch.setColor(Color.PURPLE);
		batch.draw(Assets.getRegion("pixel"), 0, YOFFSET, WIDTH, HEIGHT);
		batch.setColor(Color.WHITE);
	}
	
	public GameScreen getGameScreen() {
		return gameScreen;
	}

}
