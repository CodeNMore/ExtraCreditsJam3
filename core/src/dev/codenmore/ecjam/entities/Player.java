package dev.codenmore.ecjam.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import dev.codenmore.ecjam.assets.Assets;

public class Player extends MovableEntity {
	
	public enum PlayerState {
		baby, teen, adult, old
	}
	
	private PlayerState state;

	public Player(float x, float y) {
		super(x, y, 64, 64);
		state = PlayerState.baby;
	}
	
	@Override
	public void tick(float delta) {
		
		// Update state information
		updateState();
	}
	
	private void updateState() {
		// Check if changing state
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			if(state == PlayerState.baby) {
				state = PlayerState.teen;
			}else if(state == PlayerState.teen) {
				state = PlayerState.adult;
			}else if(state == PlayerState.adult) {
				state = PlayerState.old;
			}else if(state == PlayerState.old) {
				state = PlayerState.baby;
			}
		}
		
		// Set proper texture
		texture = Assets.getRegion("player/" + state.toString() + "_right");
		width = texture.getRegionWidth() * 5;
		height = texture.getRegionHeight() * 5;
	}
	
	public PlayerState getState() {
		return state;
	}

}
