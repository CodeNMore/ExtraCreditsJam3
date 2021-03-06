package dev.codenmore.ecjam.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import dev.codenmore.ecjam.entities.player.Player;
import dev.codenmore.ecjam.level.Level;

public class EntityManager {
	
	private Level level;
	private Array<Entity> entities, toAdd, toRemove, solidEntities;
	private Player player;
	
	public EntityManager(Level level) {
		this.level = level;
		entities = new Array<Entity>();
		toAdd = new Array<Entity>();
		toRemove = new Array<Entity>();
		solidEntities = new Array<Entity>();
	}
	
	public void tick(float delta) {
		for(Entity e : toAdd) {
			if(e instanceof Player)
				player = (Player) e;
			if(e.isSolid())
				solidEntities.add(e);
			entities.add(e);
		}
		toAdd.clear();
		
		for(int i = 0;i < entities.size;i++) {
			entities.get(i).tick(delta);
		}
		
		for(Entity e : toRemove) {
			if(player.equals(e))
				player = null;
			if(solidEntities.contains(e, true))
				solidEntities.removeValue(e, true);	
			entities.removeValue(e, true);
		}
		toRemove.clear();
	}
	
	public void render(SpriteBatch batch) {
		for(int i = 0;i < entities.size;i++) {
			Entity e = entities.get(i);
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
	
	public Array<Entity> getSolidEntities(){
		return solidEntities;
	}
	
	public Player getPlayer() {
		return player;
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
