package dev.codenmore.ecjam.entities.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;

import dev.codenmore.ecjam.entities.EntityManager;
import dev.codenmore.ecjam.entities.MovableEntity;
import dev.codenmore.ecjam.level.tile.Tile;

public class Particle extends MovableEntity implements Disposable {

	private Texture tex;
	private float life, lifeTimer;

	public Particle(float x, float y, float width, float height, float vx, float vy, 
			float life, Color color) {
		super("Particle", x, y, width, height);
		this.life = life;
		this.vx = vx;
		this.vy = vy;
		lifeTimer = 0f;
		solid = false;
		
		Pixmap pix = new Pixmap(1, 1, Format.RGBA8888);
		pix.setColor(color);
		pix.drawPixel(0, 0);
		tex = new Texture(pix);
		pix.dispose();
	}

	@Override
	public void tick(float delta) {
		lifeTimer += delta;
		if(lifeTimer >= life)
			manager.removeEntity(this);
		
		processMovements(delta);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		if(tex != null)
			batch.draw(tex, x, y, width, height);
	}
	
	public static void spawnParticles(EntityManager em, float x, float y, float spawnWidth, float spawnHeight,
			float minPartSize, float maxPartSize, int minCount, int maxCount,
			float minVx, float maxVx, float minVy, float maxVy, 
			float minLife, float maxLife, Color c) {
		// Generate this many particles
		for(int i = 0;i < MathUtils.random(minCount, maxCount);i++) {
			float xp, yp, size;
			int limit = 0;
			do {
				// Make sure particle isn't in solid tile
				xp = MathUtils.random(x - spawnWidth, x + spawnWidth);
				yp = MathUtils.random(y - spawnHeight, y + spawnHeight);
				size = MathUtils.random(minPartSize, maxPartSize);
				limit++;
			}while(em.getLevel().getTile((int) (xp / Tile.TILE_SIZE), (int) (yp / Tile.TILE_SIZE)).isSolid() && limit < 5);
			
			// We tried 5 times to get no solid tile but failed, so skip this particle
			if(limit >= 5)
				continue;
			
			float vx = MathUtils.random(minVx, maxVx);
			float vy = MathUtils.random(minVy, maxVy);
			float life = MathUtils.random(minLife, maxLife);
			em.addEntity(new Particle(xp, yp, size, size, vx, vy, life, c));
		}
	}

	@Override
	public void dispose() {
		if(tex != null) {
			tex.dispose();
			tex = null;
		}
	}

}
