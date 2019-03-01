package dev.codenmore.ecjam.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import dev.codenmore.ecjam.level.Level;

public class EntityManager {
	
	private Level level;
	private Array<Entity> entities, toAdd, toRemove;
	
	public EntityManager(Level level) {
		this.level = level;
		entities = new Array<Entity>();
		toAdd = new Array<Entity>();
		toRemove = new Array<Entity>();
	}
	
	public void tick(float delta) {
		for(Entity e : toAdd)
			entities.add(e);
		toAdd.clear();
		
		for(Entity e : entities)
			e.tick(delta);
		
		for(Entity e : toRemove)
			entities.removeValue(e, true);
		toRemove.clear();
	}
	
	public void render(SpriteBatch batch) {
		for(Entity e : entities) {
			if(inFrustum(e))
				e.render(batch);
		}
	}
	
	private boolean inFrustum(Entity e) {
		return level.getCam().frustum.pointInFrustum(e.getX(), e.getY(), 0) ||
				level.getCam().frustum.pointInFrustum(e.getX() + e.getWidth(), e.getY(), 0) ||
				level.getCam().frustum.pointInFrustum(e.getX(), e.getY() + e.getHeight(), 0) ||
				level.getCam().frustum.pointInFrustum(e.getX() + e.getWidth(), e.getY() + e.getHeight(), 0);
	}
	
	public void addEntity(Entity e) {
		toAdd.add(e);
		e.setManager(this);
	}
	
	public void removeEntity(Entity e) {
		toRemove.add(e);
	}
	
	public Level getLevel() {
		return level;
	}

}
