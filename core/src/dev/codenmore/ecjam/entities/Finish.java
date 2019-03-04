package dev.codenmore.ecjam.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonValue;

import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.entities.player.Player;
import dev.codenmore.ecjam.level.tile.Tile;

public class Finish extends Entity {

	private float rot = 0;
	
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
			manager.getLevel().complete();
		}
		
		rot += 20 * delta;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texture, x, y, width / 2, height / 2, width, height, 1f, 1f, rot);
	}

}
