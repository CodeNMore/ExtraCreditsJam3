package dev.codenmore.ecjam.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import dev.codenmore.ecjam.level.Level;

public abstract class Entity {
	
	protected EntityManager manager;
	protected TextureRegion texture;
	protected float x, y;
	protected float width, height;
	private Rectangle bounds;
	
	public Entity(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle(x, y, width, height);
	}
	
	public abstract void tick(float delta);
	
	public void render(SpriteBatch batch) {
		if(texture != null)
			batch.draw(texture, x, y, width, height);
	}
	
	public Level getLevel() {
		return manager.getLevel();
	}
	
	public Rectangle getBounds() {
		bounds.set(x, y, width, height);
		return bounds;
	}
	
	public void setManager(EntityManager manager) {
		this.manager = manager;
	}
	
	public EntityManager getManager() {
		return manager;
	}

	public TextureRegion getTexture() {
		return texture;
	}

	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

}