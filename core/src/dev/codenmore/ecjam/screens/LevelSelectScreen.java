package dev.codenmore.ecjam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import dev.codenmore.ecjam.Game;
import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.assets.Saver;
import dev.codenmore.ecjam.level.tile.Tile;
import dev.codenmore.ecjam.ui.ClickHandler;
import dev.codenmore.ecjam.ui.TextButton;
import dev.codenmore.ecjam.ui.TextureButton;

public class LevelSelectScreen extends Screen {
	
	private static final int NUM_LEVELS = 1;
	private static final float SPACING = 32;
	private static final Color VALID_COLOR = new Color(114f/255f, 126f/255f, 234f/255f, 1);
	private Array<TextureButton> buttons;

	private GlyphLayout glyph;
	private boolean needsRelease = false;
	
	public LevelSelectScreen() {
		buttons = new Array<TextureButton>();
		
		glyph = new GlyphLayout();
		glyph.setText(Assets.getFontLg(Color.GOLD), "Level Select");
	}
	
	@Override
	public void tick(float delta) {
		if(!needsRelease && Gdx.input.isButtonPressed(Buttons.LEFT)) {
			Vector2 coords = unproject(Gdx.input.getX(), Gdx.input.getY());
			for(TextureButton b : buttons)
				if(b.getBounds().contains(coords))
					b.onClick();
			needsRelease = true;
		}else if(!Gdx.input.isButtonPressed(Buttons.LEFT)){
			needsRelease = false;
		}
	}
	
	@Override
	public void render() {
		super.render();
		batch.begin();
		
		Assets.getFontLg(Color.GOLD).draw(batch, "Level Select", Game.WIDTH / 2 - glyph.width / 2, Game.HEIGHT - glyph.height * 2);
		
		batch.setColor(Color.LIGHT_GRAY);
		for(TextureButton b : buttons)
			b.render(batch);
		batch.setColor(Color.WHITE);
		
		batch.end();
	}
	
	private boolean levelUnlocked(int l) {
		return l <= Saver.getLatestCompleted() + 1;
	}
	
	@Override
	public void show() {
		super.show();
		// Create level buttons
		buttons.clear();
		int col = 0;
		int row = 4;
		for(int i = 1;i <= NUM_LEVELS;i++) {
			final int lvlId = i;
			buttons.add(new TextButton(i + "", Assets.getRegion("pixel"), 
					SPACING + col * Tile.TILE_SIZE + col * SPACING, 
					SPACING + row * Tile.TILE_SIZE + row * SPACING, Tile.TILE_SIZE, Tile.TILE_SIZE, true, 
					(levelUnlocked(lvlId) ? VALID_COLOR : Color.DARK_GRAY),
					new ClickHandler() {
				@Override
				public void onClick() {
					if(levelUnlocked(lvlId)) {
						Assets.playSound("unlock");
						ScreenManager.pushScreen(new GameScreen(lvlId));
					}
				}}));
			col++;
			if(col > 7) {
				col = 0;
				row--;
			}
		}
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
}
