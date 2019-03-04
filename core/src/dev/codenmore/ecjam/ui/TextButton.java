package dev.codenmore.ecjam.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codenmore.ecjam.assets.Assets;

public class TextButton extends TextureButton {

	private String title;
	
	public TextButton(String title, float x, float y, float width, float height, ClickHandler handler) {
		super(Assets.getRegion("pixel"), x, y, width, height, handler);
		this.title = title;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		Assets.getFontSm(Color.RED).draw(batch, title, bounds.x, bounds.y + 15);
	}

}
