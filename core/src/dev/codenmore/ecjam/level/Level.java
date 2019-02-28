package dev.codenmore.ecjam.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

import dev.codenmore.ecjam.screens.GameScreen;

public class Level {
	
	private GameScreen gameScreen;
	private ObjectMap<Integer, Room> rooms;
	private Room curRoom;

	public Level(GameScreen gameScreen, int levelId) {
		this.gameScreen = gameScreen;
		rooms = new ObjectMap<Integer, Room>();
		
		fromJson(new JsonReader().parse(Gdx.files.internal("levels/" + levelId + ".txt")));
	}
	
	public void tick(float delta) {
		
	}
	
	public void render(SpriteBatch batch) {
		
	}
	
	public void fromJson(JsonValue json) {
		
	}
	
}
