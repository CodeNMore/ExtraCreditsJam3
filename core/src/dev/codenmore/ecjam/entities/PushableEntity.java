package dev.codenmore.ecjam.entities;

import com.badlogic.gdx.utils.JsonValue;

public abstract class PushableEntity extends MovableEntity {

	public PushableEntity(String name, float x, float y, float width, float height) {
		super(name, x, y, width, height);
		solid = true;
	}
	
	public PushableEntity(JsonValue json) {
		super(json);
		solid = true;
	}

}
