package dev.codenmore.ecjam.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonValue;

public class Room {

	// Globals
	public static final int ROOM_WIDTH = 28, ROOM_HEIGHT = 20;
	
	private Level level;
	private int[][] tileIds;
	
	public Room(Level level, JsonValue json) {
		this.level = level;
		tileIds = new int[ROOM_WIDTH][ROOM_HEIGHT];
		
		fromJson(json);
	}
	
	public void tick(float delta) {
		
	}
	
	public void render(SpriteBatch batch) {
		
	}
	
	private void fromJson(JsonValue json) {
		
	}
	
}
