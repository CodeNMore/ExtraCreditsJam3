package dev.codenmore.ecjam.level.tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.level.Level;

public class EdgeTile extends Tile {

	private TextureRegion edge;
	
	public EdgeTile(int id, TileSlope slope) {
		super(id, slope);
		edge = Assets.getRegion("tile/" + id + "_edge");
	}
	
	@Override
	public void render(SpriteBatch batch, int tx, int ty, Level level) {
		super.render(batch, tx, ty, level);
		if(level != null) {
			renderEdge(batch, level, tx, ty, -1, 0, 90);
			renderEdge(batch, level, tx, ty, 1, 0, 270);
			renderEdge(batch, level, tx, ty, 0, 1, 0);
			renderEdge(batch, level, tx, ty, 0, -1, 180);
		}
	}
	
	private void renderEdge(SpriteBatch batch, Level level, int tx, int ty, int mx, int my, float rot) {
		Tile t = level.getTile(tx + mx, ty + my);
		if(t != null && t.getId() != getId()) {
			float xp = tx * Tile.TILE_SIZE;
			float yp = ty * Tile.TILE_SIZE;
			batch.draw(edge, xp, yp, Tile.TILE_SIZE_HALF, Tile.TILE_SIZE_HALF, 
					Tile.TILE_SIZE, Tile.TILE_SIZE, 1f, 1f, rot);
		}
	}

}
