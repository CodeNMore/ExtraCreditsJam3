package dev.codenmore.ecjam.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.JsonValue;

import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.entities.particles.Particle;
import dev.codenmore.ecjam.entities.player.AgeState;
import dev.codenmore.ecjam.entities.player.Player;
import dev.codenmore.ecjam.level.tile.Tile;

public class Door extends Entity {
	
	private static final float UNLOCK_TIME = 3f;
	private float unlockTimer;
	
	private GlyphLayout glyphLayout;
	private Animation<TextureRegion> anim;
	private float animTimer;

	public Door(float x, float y) {
		super("Door", x, y, Tile.TILE_SIZE, Tile.TILE_SIZE);
		solid = true;
		setup();
	}
	
	public Door(JsonValue json) {
		super(json);
		setup();
	}
	
	private void setup() {
		unlockTimer = 0f;
		glyphLayout = new GlyphLayout();
		anim = new Animation<TextureRegion>(0.5f, Assets.getRegion("door0"), Assets.getRegion("door1"), Assets.getRegion("door2"));
		anim.setPlayMode(PlayMode.LOOP_PINGPONG);
	}

	@Override
	public void tick(float delta) {
		Player p = manager.getPlayer();
		if(p != null) {
			Rectangle check = getCollisionBounds();
			check = check.set(check.x - 4f, check.y - 4f, check.width + 4f, check.height + 4f);
			if(p.getAgeState() == AgeState.old && p.getCollisionBounds().overlaps(check)) {
				unlockTimer += delta;
				if(unlockTimer >= UNLOCK_TIME) {
					Particle.spawnParticles(manager, x + width / 2, y + height / 2, 25, 25, 
							1, 6, 50, 100, 
							-50, 50, -30, 250, 
							1f, 3f, new Color(MathUtils.random(), MathUtils.random(), 0.1f, 0.8f));
					manager.removeEntity(this);
				}
			}else {
				unlockTimer = 0f;
			}
		}else {
			unlockTimer = 0f;
		}
		
		animTimer += delta;
		texture = anim.getKeyFrame(animTimer, true);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		if(unlockTimer > 0f) {
			String msg = MathUtils.round(UNLOCK_TIME - unlockTimer) + "s";
			glyphLayout = new GlyphLayout(Assets.getFontSm(Color.WHITE), msg);
			Assets.getFontSm(Color.WHITE).draw(batch, msg, x + width / 2 - glyphLayout.width / 2, y - glyphLayout.height);
		}
	}

}
