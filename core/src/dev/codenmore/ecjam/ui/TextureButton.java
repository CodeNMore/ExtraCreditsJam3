package dev.codenmore.ecjam.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class TextureButton {
	
	private TextureRegion texture;
	private Rectangle bounds;
	private ClickHandler handler;

	public TextureButton(TextureRegion texture, float x, float y, float width, float height, ClickHandler handler) {
		this.texture = texture;
		bounds = new Rectangle(x, y, width, height);
		this.handler = handler;
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	public void onClick() {
		handler.onClick();
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
}
