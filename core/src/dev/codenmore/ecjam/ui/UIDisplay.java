package dev.codenmore.ecjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Queue;

import dev.codenmore.ecjam.Game;
import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.entities.player.Player;
import dev.codenmore.ecjam.level.tile.Tile;
import dev.codenmore.ecjam.screens.GameScreen;

public class UIDisplay {
	
	// Globals
	public static final float WIDTH = Game.WIDTH, HEIGHT = 48,
							YOFFSET = Game.HEIGHT - HEIGHT;
	
	private GameScreen gameScreen;
	private GlyphLayout glyphLayout;
	private Queue<String> messages;
	
	public UIDisplay(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		messages = new Queue<String>();
		glyphLayout = new GlyphLayout();
	}
	
	public void tick(float delta) {
		// Check if messages are being displayed
		if(!messages.isEmpty()) {
			// Check if message skipped
			if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
				// Next message (or no more messages)
				messages.removeFirst();
			}
		}else {
			// Normal UI
			
		}
	}
	
	public void render(SpriteBatch batch) {
		batch.setColor(Color.BLACK);
		batch.draw(Assets.getRegion("pixel"), 0, YOFFSET, WIDTH, HEIGHT);
		batch.setColor(Color.WHITE);
		
		// Check if message to be displayed
		if(!messages.isEmpty()) {
			// Display the message
			String msg = messages.first();
			glyphLayout.setText(Assets.getFontMd(Color.GOLD), msg);
			Assets.getFontMd(Color.GOLD).draw(batch, msg, Game.WIDTH / 2 - glyphLayout.width / 2, YOFFSET + 34);
			Assets.getFontMicro(Color.WHITE).draw(batch, "[Enter]", Game.WIDTH - 56, YOFFSET + 12);
		}else {
			// Normal UI
			Player p = gameScreen.getLevel().getEntityManager().getPlayer();
			if(p != null) {
				glyphLayout.setText(Assets.getFontMd(Color.WHITE), p.getAgeState().slogan);
				Assets.getFontMd(Color.WHITE).draw(batch, p.getAgeState().slogan,
						Game.WIDTH / 2 - glyphLayout.width / 2, YOFFSET + 34);
			}
			
			// Life count
			batch.draw(Assets.getRegion("heart"), 12, YOFFSET + 8, Tile.TILE_SIZE_HALF, Tile.TILE_SIZE_HALF);
			Assets.getFontMd(Color.WHITE).draw(batch, "x5", 52, YOFFSET + 34);
		}
	}
	
	public void addMessage(String msg) {
		messages.addLast(msg);
	}
	
	public boolean isShowingMessage() {
		return !messages.isEmpty();
	}
	
	public GameScreen getGameScreen() {
		return gameScreen;
	}

}
