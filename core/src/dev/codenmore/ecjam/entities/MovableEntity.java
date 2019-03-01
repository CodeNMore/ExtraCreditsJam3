package dev.codenmore.ecjam.entities;

public abstract class MovableEntity extends Entity {

	protected float vx, vy;
	
	public MovableEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

}
