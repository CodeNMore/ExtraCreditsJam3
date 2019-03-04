package dev.codenmore.ecjam.entities;

import javax.swing.JOptionPane;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

import dev.codenmore.ecjam.entities.invisible.XControlTrigger;
import dev.codenmore.ecjam.entities.invisible.XTriggerDialog;

public class EntityFactory {
	
	private static ObjectMap<String, EntityMaker> makers;
	
	private EntityFactory() {}
	
	public static void init() {
		makers = new ObjectMap<String, EntityMaker>();
		
		registerEntity("XTriggerDialog", new EntityMaker() {
			@Override
			public Entity makeEditor(float x, float y) {
				return new XTriggerDialog("XTriggerDialog", x, 
						promptStr("The message for the trigger to show:"));
			}
			@Override
			public Entity make(JsonValue json) {
				return new XTriggerDialog(json);
			}});
		registerEntity("XCntrlTrig", new EntityMaker() {
			@Override
			public Entity makeEditor(float x, float y) {
				return new XControlTrigger("XCntrlTrig", x, 
						promptStr("Commands 'cntrl:true;cntrl:false':"));
			}
			@Override
			public Entity make(JsonValue json) {
				return new XControlTrigger(json);
			}});
	}
	
	public static Entity makeEntity(String name, JsonValue json) {
		return makers.get(name).make(json);
	}
	
	public static Entity makeEntityEditor(String name, float x, float y) {
		return makers.get(name).makeEditor(x, y);
	}
	
	private static String promptStr(String msg) {
		return JOptionPane.showInputDialog(msg);
	}
	
	private static void registerEntity(String name, EntityMaker maker) {
		makers.put(name, maker);
	}
	
	public static Array<String> getAllEntityNames(){
		return makers.keys().toArray();
	}
	
	private static interface EntityMaker{
		public Entity makeEditor(float x, float y);
		public Entity make(JsonValue json);
	}

}
