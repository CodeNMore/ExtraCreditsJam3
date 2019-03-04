package dev.codenmore.ecjam.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codenmore.ecjam.assets.Assets;

public class TextButton extends TextureButton {

	private String title;
	private GlyphLayout glyphLayout;
	
	public TextButton(String title, float x, float y, float width, float height, ClickHandler handler) {
		super(Assets.getRegion("pixel"), x, y, width, height, handler);
		this.title = title;
		glyphLayout = new GlyphLayout();
		glyphLayout.setText(Assets.getFontSm(Color.RED), title);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		Assets.getFontSm(Color.RED).draw(batch, title, (bounds.x + bounds.width / 2) - glyphLayout.width / 2,
													(bounds.y + bounds.height / 2) + glyphLayout.height / 2);
	}

}
