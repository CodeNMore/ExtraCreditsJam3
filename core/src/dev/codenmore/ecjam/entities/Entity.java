package dev.codenmore.ecjam.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;

import dev.codenmore.ecjam.level.Level;

public abstract class Entity {
	
	protected EntityManager manager;
	protected TextureRegion texture;
	protected String name;
	protected float x, y;
	protected float width, height;
	protected Rectangle collisionOffsets;
	private Rectangle bounds;
	
	public Entity(String name, float x, float y, float width, float height) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		collisionOffsets = new Rectangle(0, 0, 0, 0);
		bounds = new Rectangle(x, y, width, height);
	}
	
	public Entity(JsonValue json) {
		this("", 0, 0, 0, 0);
		fromJson(json);
	}
	
	public abstract void tick(float delta);
	
	protected void fromJson(JsonValue val) {
		x = val.getFloat("x");
		y = val.getFloat("y");
		name = val.getString("name");
	}
	
	public JsonValue toJson() {
		JsonValue ret = new JsonValue(ValueType.object);
		ret.addChild("x", new JsonValue(x));
		ret.addChild("y", new JsonValue(y));
		ret.addChild("name", new JsonValue(name));
		return ret;
	}
	
	public void render(SpriteBatch batch) {
		if(texture != null)
			batch.draw(texture, x, y, width, height);
	}
	
	public Level getLevel() {
		return manager.getLevel();
	}
	
	public Rectangle getCollisionBounds() {
		return new Rectangle(x + collisionOffsets.x, y + collisionOffsets.y, 
				width - collisionOffsets.x - collisionOffsets.width, 
				height - collisionOffsets.y - collisionOffsets.height);
	}
	
	public Rectangle getRenderBounds() {
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
