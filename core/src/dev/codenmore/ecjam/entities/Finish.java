package dev.codenmore.ecjam.entities;

import com.badlogic.gdx.utils.JsonValue;

import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.entities.player.Player;
import dev.codenmore.ecjam.level.tile.Tile;

public class Finish extends Entity {

	public Finish(float x, float y) {
		super("Finish", x, y, Tile.TILE_SIZE, Tile.TILE_SIZE);
		texture = Assets.getRegion("finish");
		solid = false;
	}
	
	public Finish(JsonValue json) {
		super(json);
		texture = Assets.getRegion("finish");
	}

	@Override
	public void tick(float delta) {
		Player p = manager.getPlayer();
		if(p.getCollisionBounds().overlaps(getCollisionBounds())) {
			System.out.println("Donezone");
		}
	}

}
