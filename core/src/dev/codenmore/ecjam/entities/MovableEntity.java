package dev.codenmore.ecjam.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;

import dev.codenmore.ecjam.level.Level;
import dev.codenmore.ecjam.level.tile.Tile;
import dev.codenmore.ecjam.level.tile.Tile.TileSlope;

public abstract class MovableEntity extends Entity {

	protected float speed;
	protected float vx, vy;
	
	private boolean onGround = false, moving = true;
	
	public MovableEntity(String name, float x, float y, float width, float height) {
		super(name, x, y, width, height);
	}
	
	public MovableEntity(JsonValue json) {
		super(json);
	}
	
	private float isYCollisions(float oldY) {
		Rectangle cb = getCollisionBounds();
		
		// Return y value
		float retY = cb.y;
		
		if(oldY >= cb.y) {
			// Downward or stationary travel
			boolean shouldBeOnGround = false;
			
			// Calculate tile coordinates
			int oldTy = (int) (oldY / Tile.TILE_SIZE);
			int farTy = (int) (cb.y / Tile.TILE_SIZE);
			int startTx = (int) (cb.x / Tile.TILE_SIZE);
			int endTx = (int) ((cb.x + cb.width - 1) / Tile.TILE_SIZE);
			
			// Check collisions
			for(int tx = startTx;tx <= endTx;tx++) {
				for(int ty = oldTy;ty >= farTy;ty--) {
					// For now only checking if solid
					Tile t = getLevel().getTile(tx, ty);
					if(t == null || t.getSlope() == TileSlope.SOLID) {
						// Normal full-solid tile
						float newRetY = ty * Tile.TILE_SIZE + Tile.TILE_SIZE;
						if(newRetY > retY) {
							retY = newRetY;
							shouldBeOnGround = true;
							vy = 0f;
						}
					}else if(tx == endTx && getLevel().getTile(tx, ty).getSlope() == TileSlope.LEFT) {
						// Left-slope (down to up)
						int bottomRightIntoTile = (int) ((cb.x + cb.width - 1) % Tile.TILE_SIZE);
						float newRetY = (ty * Tile.TILE_SIZE + Tile.TILE_SIZE) - (Tile.TILE_SIZE - bottomRightIntoTile - 1);
						if(newRetY > retY || onGround) {
							retY = newRetY;
							shouldBeOnGround = true;
							vy = 0f;
						}
					}else if(tx == startTx && getLevel().getTile(tx, ty).getSlope() == TileSlope.RIGHT) {
						// Right slope (up to down)
						int bottomLeftIntoTile = (int) (cb.x % Tile.TILE_SIZE);
						float newRetY = (ty * Tile.TILE_SIZE + Tile.TILE_SIZE) - (bottomLeftIntoTile - 1);
						if(newRetY > retY || onGround) {
							retY = newRetY;
							shouldBeOnGround = true;
							vy = 0f;
						}
					}
				}
			}
			
			// Return appropriate Y value
			onGround = shouldBeOnGround;
		}else if(oldY < cb.y){
			// Upward travel
			onGround = false;
			
			// Calculate tile coordinates
			int oldTy = (int) ((oldY + cb.height - 1) / Tile.TILE_SIZE);
			int farTy = (int) ((cb.y + cb.height - 1) / Tile.TILE_SIZE);
			
			// Check collisions
			for(int tx = (int) (cb.x / Tile.TILE_SIZE);tx <= (int) ((cb.x + cb.width - 1) / Tile.TILE_SIZE);tx++) {
				for(int ty = oldTy;ty <= farTy;ty++) {
					// For now only checking if solid
					Tile t = getLevel().getTile(tx, ty);
					if(t == null || t.isSolid()) {
						vy = 0f;
						retY = ty * Tile.TILE_SIZE - cb.height;
					}
				}
			}
		}
		
		// Check entities collisions
		if(solid) {
			Array<Entity> ents = manager.getSolidEntities();
			for(int i = 0;i < ents.size;i++) {
				Entity e = ents.get(i);
				if(e.equals(this))
					continue;
				// Still check if solid in case
				if(e.isSolid() && e.getCollisionBounds().overlaps(getCollisionBounds())) {
					retY = oldY;
					if(vy < 0f)
						onGround = true;
					vy = 0f;
				}
			}
		}
		
		// Base case always return current position
		return retY;
	}
	
	private float isXCollisions(float oldX) {
		Rectangle cb = getCollisionBounds();
		float retX = cb.x;
		
		if(oldX > cb.x) {
			// Left travel
			// Calculate tile coordinates
			int oldTx = (int) (oldX / Tile.TILE_SIZE);
			int farTx = (int) (cb.x / Tile.TILE_SIZE);
			
			// Check collisions
			for(int ty = (int) (cb.y / Tile.TILE_SIZE);ty <= (int) ((cb.y + cb.height - 1) / Tile.TILE_SIZE);ty++) {
				for(int tx = oldTx;tx >= farTx;tx--) {
					// For now only checking if solid
					Tile t = getLevel().getTile(tx, ty);
					if(t == null || t.getSlope() == TileSlope.SOLID) {
						vx = 0f;
						retX = tx * Tile.TILE_SIZE + Tile.TILE_SIZE;
					}
				}
			}
		}else if(oldX < cb.x){
			// Right travel
			// Calculate tile coordinates
			int oldTx = (int) ((oldX + cb.width - 1) / Tile.TILE_SIZE);
			int farTx = (int) ((cb.x + cb.width - 1) / Tile.TILE_SIZE);
			
			// Check collisions
			for(int ty = (int) (cb.y / Tile.TILE_SIZE);ty <= (int) ((cb.y + cb.height - 1) / Tile.TILE_SIZE);ty++) {
				for(int tx = oldTx;tx <= farTx;tx++) {
					// For now only checking if solid
					Tile t = getLevel().getTile(tx, ty);
					if(t == null || t.getSlope() == TileSlope.SOLID) {
						vx = 0f;
						retX = tx * Tile.TILE_SIZE - cb.width;
					}
				}
			}
		}
		
		// Check entities collisions
		if(solid) {
			Array<Entity> ents = manager.getSolidEntities();
			for(int i = 0;i < ents.size;i++) {
				Entity e = ents.get(i);
				if(e.equals(this))
					continue;
				// Still check if solid in case
				if(e.isSolid() && e.getCollisionBounds().overlaps(getCollisionBounds())) {
					if(canPushOthers && e instanceof PushableEntity) {
						if(oldX < retX) {
							// Right push
							((PushableEntity) e).push(250);
						}else if(retX < oldX) {
							// Left push
							((PushableEntity) e).push(-250);
						}
					}
					// Still collide
					retX = oldX;
				}
			}
		}
		
		return retX;
	}
	
	protected void processMovements(float delta) {
		// Apply gravity
		vy -= Level.GRAVITY;
		
		// Store old position
		Rectangle oldCb = getCollisionBounds();
		
		// Move with Y collisions
		y += vy * delta;
		float cby = isYCollisions(oldCb.y);
		y = cby - collisionOffsets.y;
		
		// Move with X collisions
		x += vx * delta;
		float cbx = isXCollisions(oldCb.x);
		x = cbx - collisionOffsets.x;
		
		// Check if moving
		moving = !(oldCb.x == cbx && oldCb.y == cby);
	}
	
	public boolean isOnGround() {
		return onGround;
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	protected void jump(int strength) {
		if(!isOnGround()) return;
		vy += Level.GRAVITY * strength;
	}
	
	protected void push(int strength) {
		vx += strength;
	}

}
