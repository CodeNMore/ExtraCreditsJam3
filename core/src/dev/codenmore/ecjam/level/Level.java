package dev.codenmore.ecjam.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import dev.codenmore.ecjam.level.tile.Tile;
import dev.codenmore.ecjam.level.tile.TileFactory;
import dev.codenmore.ecjam.screens.GameScreen;

public class Level {
	
	private GameScreen gameScreen;
	private int id;
	private int width, height;
	private int[][] tileIds;

	public Level(GameScreen gameScreen, int levelId) {
		this.gameScreen = gameScreen;
		fromJson(new JsonReader().parse(Gdx.files.internal("levels/" + levelId + ".txt")));
	}
	
	public void tick(float delta) {
		
	}
	
	public void render(SpriteBatch batch) {
		for(int y = 0;y < height;y++) {
			for(int x = 0;x < width;x++) {
				TileFactory.getTile(tileIds[x][y]).render(batch, x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
			}
		}
	}
	
	public void fromJson(JsonValue json) {
		id = json.getInt("id");
		width = json.getInt("width");
		height = json.getInt("height");
		tileIds = new int[width][height];
		
		JsonValue rows = json.get("tileIds");
		for(int y = 0;y < height;y++) {
			JsonValue cols = rows.get(y);
			for(int x = 0;x < width;x++) {
				tileIds[x][y] = cols.getInt(x);
			}
		}
	}
	
}
