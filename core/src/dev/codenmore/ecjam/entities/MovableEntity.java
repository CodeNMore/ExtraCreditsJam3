package dev.codenmore.ecjam.entities;

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
		if(oldY >= y) {
			// Downward or stationary travel
			boolean shouldBeOnGround = false;
			
			// Calculate tile coordinates
			int oldTy = (int) (oldY / Tile.TILE_SIZE);
			int farTy = (int) (y / Tile.TILE_SIZE);
			int startTx = (int) (x / Tile.TILE_SIZE);
			int endTx = (int) ((x + width - 1) / Tile.TILE_SIZE);
			
			// Return y value
			float retY = y;
			
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
						int bottomRightIntoTile = (int) ((x + width - 1) % Tile.TILE_SIZE);
						float newRetY = (ty * Tile.TILE_SIZE + Tile.TILE_SIZE) - (Tile.TILE_SIZE - bottomRightIntoTile - 1);
						if(newRetY > retY || onGround) {
							retY = newRetY;
							shouldBeOnGround = true;
							vy = 0f;
						}
					}else if(tx == startTx && getLevel().getTile(tx, ty).getSlope() == TileSlope.RIGHT) {
						// Right slope (up to down)
						int bottomLeftIntoTile = (int) (x % Tile.TILE_SIZE);
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
		}else if(oldY < y){
			// Upward travel
			onGround = false;
			
			// Calculate tile coordinates
			int oldTy = (int) ((oldY + height - 1) / Tile.TILE_SIZE);
			int farTy = (int) ((y + height - 1) / Tile.TILE_SIZE);
			
			// Check collisions
			for(int tx = (int) (x / Tile.TILE_SIZE);tx <= (int) ((x + width - 1) / Tile.TILE_SIZE);tx++) {
				for(int ty = oldTy;ty <= farTy;ty++) {
					// For now only checking if solid
					if(getLevel().getTile(tx, ty).isSolid()) {
						vy = 0f;
						return ty * Tile.TILE_SIZE - height;
					}
				}
			}
		}
		
		// Base case always return current position
		return y;
	}
	
	private float isXCollisions(float oldX) {
		if(oldX > x) {
			// Left travel
			// Calculate tile coordinates
			int oldTx = (int) (oldX / Tile.TILE_SIZE);
			int farTx = (int) (x / Tile.TILE_SIZE);
			
			// Check collisions
			for(int ty = (int) (y / Tile.TILE_SIZE);ty <= (int) ((y + height - 1) / Tile.TILE_SIZE);ty++) {
				for(int tx = oldTx;tx >= farTx;tx--) {
					// For now only checking if solid
					if(getLevel().getTile(tx, ty).getSlope() == TileSlope.SOLID) {
						vx = 0f;
						return tx * Tile.TILE_SIZE + Tile.TILE_SIZE;
					}
				}
			}
		}else if(oldX < x){
			// Right travel
			// Calculate tile coordinates
			int oldTx = (int) ((oldX + width - 1) / Tile.TILE_SIZE);
			int farTx = (int) ((x + width - 1) / Tile.TILE_SIZE);
			
			// Check collisions
			for(int ty = (int) (y / Tile.TILE_SIZE);ty <= (int) ((y + height - 1) / Tile.TILE_SIZE);ty++) {
				for(int tx = oldTx;tx <= farTx;tx++) {
					// For now only checking if solid
					if(getLevel().getTile(tx, ty).getSlope() == TileSlope.SOLID) {
						vx = 0f;
						return tx * Tile.TILE_SIZE - width;
					}
				}
			}
		}
		
		// Base case always return current position
		return x;
	}
	
	protected void processMovements(float delta) {
		// Apply gravity
		vy -= Level.GRAVITY;
		
		// Store old position
		float oldX = x;
		float oldY = y;
		
		// Move with Y collisions
		y += vy * delta;
		y = isYCollisions(oldY);
		
		// Move with X collisions
		x += vx * delta;
		x = isXCollisions(oldX);
		
		// Check if moving
		moving = !(oldX == x && oldY == y);
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
