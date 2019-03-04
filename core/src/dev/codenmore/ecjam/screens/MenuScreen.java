package dev.codenmore.ecjam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import dev.codenmore.ecjam.Game;
import dev.codenmore.ecjam.assets.Assets;

public class MenuScreen extends Screen {
	
	private GlyphLayout glyph, glyph2;

	public MenuScreen() {
		glyph = new GlyphLayout();
		glyph.setText(Assets.getFontHuge(Color.GOLD), Game.TITLE);
		glyph2 = new GlyphLayout();
		glyph2.setText(Assets.getFontLg(Color.WHITE), "[Press Any Key]");
	}
	
	@Override
	public void tick(float delta) {
		if(Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
			Assets.playSound("unlock");
			ScreenManager.swapScreen(new LevelSelectScreen());
		}
	}
	
	@Override
	public void render() {
		super.render();
		batch.begin();
		
		Assets.getFontHuge(Color.GOLD).draw(batch, Game.TITLE, Game.WIDTH / 2 - glyph.width / 2, (Game.HEIGHT - Game.HEIGHT / 4) + glyph.height / 2);
		Assets.getFontLg(Color.WHITE).draw(batch, "[Press Any Key]", Game.WIDTH / 2 - glyph2.width / 2, Game.HEIGHT / 4 + glyph2.height / 2);
		
		batch.end();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
}
