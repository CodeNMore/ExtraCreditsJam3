package dev.codenmore.ecjam.editor;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;

import dev.codenmore.ecjam.level.Room;

public class RoomTemplate {
	
	public int id;
	public int[][] tileIds;
	
	public int leftId = -1, rightId = -1, upId = -1, downId = -1;
	
	public RoomTemplate(int id) {
		this.id = id;
		tileIds = new int[Room.ROOM_WIDTH][Room.ROOM_HEIGHT];
	}
	
	public RoomTemplate(JsonValue json) {
		this(-1);
		fromJson(json);
	}
	
	public JsonValue toJson() {
		JsonValue ret = new JsonValue(ValueType.object);
		ret.addChild("id", new JsonValue(id));
		ret.addChild("leftId", new JsonValue(leftId));
		ret.addChild("rightId", new JsonValue(rightId));
		ret.addChild("upId", new JsonValue(upId));
		ret.addChild("downId", new JsonValue(downId));
		
		JsonValue tids = new JsonValue(ValueType.array);
		for(int x = 0;x < Room.ROOM_WIDTH;x++) {
			JsonValue row = new JsonValue(ValueType.array);
			for(int y = 0;y < Room.ROOM_HEIGHT;y++) {
				row.addChild(new JsonValue(tileIds[x][y]));
			}
			tids.addChild(row);
		}
		ret.addChild("tileIds", tids);
		
		return ret;
	}
	
	public void fromJson(JsonValue json) {
		id = json.getInt("id");
		leftId = json.getInt("leftId");
		rightId = json.getInt("rightId");
		upId = json.getInt("upId");
		downId = json.getInt("downId");
		
		JsonValue rows = json.get("tileIds");
		for(int x = 0;x < Room.ROOM_WIDTH;x++) {
			JsonValue row = rows.get(x);
			for(int y = 0;y < Room.ROOM_HEIGHT;y++) {
				tileIds[x][y] = row.getInt(y);
			}
		}
	}

}
