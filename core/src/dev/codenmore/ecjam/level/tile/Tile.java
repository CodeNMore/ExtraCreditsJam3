package dev.codenmore.ecjam.level.tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import dev.codenmore.ecjam.assets.Assets;

public class Tile {
	
	// Tile slope enum
	public enum TileSlope {
		NONE,	//  Background
		SOLID, 	//  [_]
		LEFT, 	//  /_]
		RIGHT;	//  [_\
	}
	
	// Globals
	public static final float TILE_SIZE = 32f;
	
	private final int id;
	private final TileSlope slope;
	private TextureRegion texture;
	
	public Tile(int id, TileSlope slope) {
		this.id = id;
		this.slope = slope;
		texture = Assets.getRegion("tile/" + id);
	}
	
	public void tick(float delta) {}
	
	public void render(SpriteBatch batch, float x, float y) {
		render(batch, x, y, TILE_SIZE, TILE_SIZE);
	}
	
	public void render(SpriteBatch batch, float x, float y, float width, float height) {
		batch.draw(texture, x, y, width, height);
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
