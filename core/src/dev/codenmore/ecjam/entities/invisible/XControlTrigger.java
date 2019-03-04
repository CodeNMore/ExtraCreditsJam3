package dev.codenmore.ecjam.entities.invisible;

import com.badlogic.gdx.utils.JsonValue;

import dev.codenmore.ecjam.entities.Entity;
import dev.codenmore.ecjam.entities.player.Player;

public class XControlTrigger extends Entity {
	
	private String cmd;

	public XControlTrigger(String name, float x, String cmd) {
		super(name, x, 0, 0, 0);
		texture = null;
		
		// Format:   control:true for enable or control:false to disable, separate by ;
		this.cmd = cmd;
	}
	
	public XControlTrigger(JsonValue json) {
		super(json);
	}

	@Override
	public void tick(float delta) {
		Player p = manager.getPlayer();
		if(p != null) {
			if(p.getX() + p.getWidth() / 6 >= x) {
				String[] cmds = cmd.split(";");
				for(String cmd : cmds) {
					String[] ps = cmd.split(":");
					if(ps[0].equals("jump")) {
						p.setJumpAllow(Boolean.parseBoolean(ps[1]));
					}else if(ps[0].equals("evolve")) {
						// -1 is infinite, positive is limited, 0 is disabled
						p.setEvolutionAvailable(Integer.parseInt(ps[1]));
					}else {
						System.err.println("WARNING: unknown control for command " + cmd);
					}
				}
				manager.removeEntity(this);
			}
		}
	}
	
	@Override
	protected void fromJson(JsonValue val) {
		super.fromJson(val);
		cmd = val.getString("cmd");
	}
	
	@Override
	public JsonValue toJson() {
		JsonValue ret = super.toJson();
		ret.addChild("cmd", new JsonValue(cmd));
		return ret;
	}

}
