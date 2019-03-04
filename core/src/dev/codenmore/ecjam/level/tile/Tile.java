package dev.codenmore.ecjam.level.tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.level.Level;

public class Tile {
	
	// Tile slope enum
	public enum TileSlope {
		NONE,	//  Background
		SOLID, 	//  [_]
		LEFT, 	//  /_]
		RIGHT;	//  [_\
	}
	
	// Globals
	public static final float TILE_SIZE = 64f, TILE_SIZE_HALF = TILE_SIZE / 2f;
	
	private final int id;
	private final TileSlope slope;
	private TextureRegion texture;
	
	public Tile(int id, TileSlope slope) {
		this.id = id;
		this.slope = slope;
		texture = Assets.getRegion("tile/" + id);
	}
	
	public void tick(float delta) {}
	
	public void render(SpriteBatch batch, int tx, int ty, Level level) {
		render(batch, tx * TILE_SIZE, ty * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}
	
	public void render(SpriteBatch batch, float x, float y, float width, float height) {
		batch.draw(texture, x, y, width, height);
	}
	
	public TextureRegion getTexture() {
		return texture;
	}
	
	public boolean isSolid() {
		return slope != TileSlope.NONE;
	}
	
	public TileSlope getSlope() {
		return slope;
	}
	
	public int getId() {
		return id;
	}

}
