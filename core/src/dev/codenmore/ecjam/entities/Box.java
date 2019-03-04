package dev.codenmore.ecjam.entities;

import com.badlogic.gdx.utils.JsonValue;

import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.level.tile.Tile;

public class Box extends PushableEntity {

	public Box(float x, float y) {
		super("Box", x, y, Tile.TILE_SIZE, Tile.TILE_SIZE);
		texture = Assets.getRegion("box");
		collisionOffsets.set(0, 0, 0, 0);
	}

	public Box(JsonValue json) {
		super(json);
		texture = Assets.getRegion("box");
		fromJson(json);
	}

	@Override
	public void tick(float delta) {
		// Movements
		processMovements(delta);
		vx = 0f;
	}

}
