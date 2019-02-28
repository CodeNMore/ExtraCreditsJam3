package dev.codenmore.ecjam.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.PrettyPrintSettings;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.ObjectMap;

import dev.codenmore.ecjam.screens.Screen;

public class LevelEditorScreen extends Screen {

	private int id;
	private ObjectMap<Integer, RoomTemplate> rooms;
	private int curIdx = 0;
	
	public LevelEditorScreen(int levelId) {
		id = levelId;
		rooms = new ObjectMap<Integer, RoomTemplate>();
		
		if(Gdx.files.internal("levels/" + levelId + ".txt").exists()) {
			// Load level
			fromJson(new JsonReader().parse(Gdx.files.internal("levels/" + levelId + ".txt")));
		}else {
			// New level
			rooms.put(0, new RoomTemplate(0));
			curIdx = 0;
		}
	}
	
	@Override
	public void tick(float delta) {
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			// Save the level
			JsonValue json = toJson();
			PrettyPrintSettings ps = new PrettyPrintSettings();
			ps.outputType = OutputType.json;
			Gdx.files.absolute("C:\\Users\\Tony\\Desktop\\Workspaces\\GDX_Workspace\\ECJam3\\core\\assets\\levels\\" + id + ".txt")
				.writeString(json.prettyPrint(ps), false);
			System.out.println("level " + id + " saved");
		}
	}
	
	@Override
	public void render() {
		super.render();
		batch.begin();
		
		
		
		batch.end();
	}
	
	public RoomTemplate getCurTemplate() {
		return rooms.get(curIdx);
	}
	
	public JsonValue toJson() {
		JsonValue ret = new JsonValue(ValueType.object);
		ret.addChild("id", new JsonValue(id));
		JsonValue jrooms = new JsonValue(ValueType.object);
		for(int i : rooms.keys()) {
			jrooms.addChild(i + "", rooms.get(i).toJson());
		}
		ret.addChild("rooms", jrooms);
		return ret;
	}
	
	public void fromJson(JsonValue json) {
		id = json.getInt("id");
		rooms.clear();
		curIdx = 0;
		JsonValue jrooms = json.get("rooms");
		for(int i = 0;i < jrooms.size;i++){
			RoomTemplate r = new RoomTemplate(jrooms.get(i));
			rooms.put(r.id, r);
		}
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
}
