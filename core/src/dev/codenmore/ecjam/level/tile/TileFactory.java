package dev.codenmore.ecjam.level.tile;

import dev.codenmore.ecjam.level.tile.Tile.TileSlope;

public class TileFactory {
	
	private static Tile[] tiles;
	
	private TileFactory() {}
	
	public static void init() {
		tiles = new Tile[32];
		
		registerTile(new Tile(0, TileSlope.NONE));
		registerTile(new Tile(1, TileSlope.SOLID));
	}
	
	public static Tile getTile(int id) {
		if(id < 0 || id >= tiles.length)
			return null;
		return tiles[id];
	}
	
	private static void registerTile(Tile t) {
		if(tiles[t.getId()] != null)
			throw new RuntimeException("Duplicate tile ID " + t.getId());
		tiles[t.getId()] = t;
	}

}
