package dev.codenmore.ecjam.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import dev.codenmore.ecjam.assets.Assets;

public class TextButton extends TextureButton {

	private String title;
	private boolean lg;
	private Color color;
	private GlyphLayout glyphLayout;
	
	public TextButton(String title, TextureRegion tex, float x, float y, float width, float height, boolean lg, Color c, ClickHandler handler) {
		super(tex, x, y, width, height, handler);
		this.title = title;
		this.lg = lg;
		color = c;
		glyphLayout = new GlyphLayout();
		glyphLayout.setText(Assets.getFontSm(color), title);
		if(lg)
			glyphLayout.setText(Assets.getFontLg(color), title);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		(lg ? Assets.getFontLg(color) : Assets.getFontSm(color))
							.draw(batch, title, (bounds.x + bounds.width / 2) - glyphLayout.width / 2,
									(bounds.y + bounds.height / 2) + glyphLayout.height / 2);
	}

}
