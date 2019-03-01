package dev.codenmore.ecjam.entities;

import dev.codenmore.ecjam.level.Level;
import dev.codenmore.ecjam.level.tile.Tile;

public abstract class MovableEntity extends Entity {

	protected float speed;
	protected float vx, vy;
	
	private boolean onGround = false, moving = true;
	
	public MovableEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	private float isYCollisions(float oldY) {
		if(oldY > y) {
			// Downward travel
			onGround = false;
			
			// Calculate tile coordinates
			int oldTy = (int) (oldY / Tile.TILE_SIZE);
			int farTy = (int) (y / Tile.TILE_SIZE);
			
			// Check collisions
			for(int tx = (int) (x / Tile.TILE_SIZE);tx <= (int) ((x + width - 1) / Tile.TILE_SIZE);tx++) {
				for(int ty = oldTy;ty >= farTy;ty--) {
					// For now only checking if solid
					if(getLevel().getTile(tx, ty).isSolid()) {
						onGround = true;
						vy = 0f;
						return ty * Tile.TILE_SIZE + Tile.TILE_SIZE;
					}
				}
			}
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
					if(getLevel().getTile(tx, ty).isSolid()) {
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
					if(getLevel().getTile(tx, ty).isSolid()) {
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
//		if(!isOnGround()) return;
		vy += Level.GRAVITY * strength;
	}

}
