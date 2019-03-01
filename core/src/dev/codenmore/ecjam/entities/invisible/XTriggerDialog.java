package dev.codenmore.ecjam.entities.invisible;

import com.badlogic.gdx.utils.JsonValue;

import dev.codenmore.ecjam.entities.Entity;
import dev.codenmore.ecjam.entities.player.Player;

public class XTriggerDialog extends Entity {
	
	private String msg;

	public XTriggerDialog(String name, float x, String msg) {
		super(name, x, 0, 0, 0);
		texture = null;
		
		this.msg = msg;
	}
	
	public XTriggerDialog(JsonValue json) {
		super(json);
	}

	@Override
	public void tick(float delta) {
		Player p = manager.getPlayer();
		if(p != null) {
			if(p.getX() >= x) {
				System.out.println("Message: " + msg);
				manager.removeEntity(this);
			}
		}
	}
	
	@Override
	protected void fromJson(JsonValue val) {
		super.fromJson(val);
		msg = val.getString("msg");
	}
	
	@Override
	public JsonValue toJson() {
		JsonValue ret = super.toJson();
		ret.addChild("msg", new JsonValue(msg));
		return ret;
	}

}
