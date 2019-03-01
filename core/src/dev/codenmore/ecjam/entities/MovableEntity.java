package dev.codenmore.ecjam.entities;

import com.badlogic.gdx.math.Rectangle;

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
	
	private float isYCollisions(float oldY) {
		Rectangle cb = getCollisionBounds();
		
		if(oldY >= cb.y) {
			// Downward or stationary travel
			boolean shouldBeOnGround = false;
			
			// Calculate tile coordinates
			int oldTy = (int) (oldY / Tile.TILE_SIZE);
			int farTy = (int) (cb.y / Tile.TILE_SIZE);
			int startTx = (int) (cb.x / Tile.TILE_SIZE);
			int endTx = (int) ((cb.x + cb.width - 1) / Tile.TILE_SIZE);
			
			// Return y value
			float retY = cb.y;
			
			// Check collisions
			for(int tx = startTx;tx <= endTx;tx++) {
				for(int ty = oldTy;ty >= farTy;ty--) {
					// For now only checking if solid
					if(getLevel().getTile(tx, ty).getSlope() == TileSlope.SOLID) {
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
			return retY;
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
					if(getLevel().getTile(tx, ty).isSolid()) {
						vy = 0f;
						return ty * Tile.TILE_SIZE - cb.height;
					}
				}
			}
		}
		
		// Base case always return current position
		return cb.y;
	}
	
	private float isXCollisions(float oldX) {
		Rectangle cb = getCollisionBounds();
		
		if(oldX > cb.x) {
			// Left travel
			// Calculate tile coordinates
			int oldTx = (int) (oldX / Tile.TILE_SIZE);
			int farTx = (int) (cb.x / Tile.TILE_SIZE);
			
			// Check collisions
			for(int ty = (int) (cb.y / Tile.TILE_SIZE);ty <= (int) ((cb.y + cb.height - 1) / Tile.TILE_SIZE);ty++) {
				for(int tx = oldTx;tx >= farTx;tx--) {
					// For now only checking if solid
					if(getLevel().getTile(tx, ty).getSlope() == TileSlope.SOLID) {
						vx = 0f;
						return tx * Tile.TILE_SIZE + Tile.TILE_SIZE;
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
					if(getLevel().getTile(tx, ty).getSlope() == TileSlope.SOLID) {
						vx = 0f;
						return tx * Tile.TILE_SIZE - cb.width;
					}
				}
			}
		}
		
		// Base case always return current position
		return cb.x;
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

}
