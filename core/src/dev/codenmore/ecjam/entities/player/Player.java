package dev.codenmore.ecjam.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.entities.MovableEntity;

public class Player extends MovableEntity {
	
	private AgeState ageState;

	public Player(float x, float y) {
		super(x, y, 64, 64);
		ageState = AgeState.baby;
		speed = ageState.speed;
	}
	
	@Override
	public void tick(float delta) {
		// Process controls
		updateControls(delta);
		
		// Apply gravity
		processMovements(delta);
		
		// Update state information
		updateState();
		
		// Center on us
		getLevel().centerOn(getBounds());
	}
	
	private void updateControls(float delta) {
		vx = 0f;
		if(Gdx.input.isKeyPressed(Keys.A))
			vx = -speed;
		else if(Gdx.input.isKeyPressed(Keys.D))
			vx = speed;
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE))
			jump(ageState.jumpStrength);
	}
	
	private void updateState() {
		// Check if changing state
		if(Gdx.input.isKeyJustPressed(Keys.E)) {
			// Check which state to transition to
			if(ageState == AgeState.baby) {
				ageState = AgeState.teen;
			}else if(ageState == AgeState.teen) {
				ageState = AgeState.adult;
			}else if(ageState == AgeState.adult) {
				ageState = AgeState.old;
			}else if(ageState == AgeState.old) {
				ageState = AgeState.baby;
			}
			// Set our new speed
			speed = ageState.speed;
		}
		
		// Set proper texture
		texture = Assets.getRegion("player/" + ageState.toString() + "_" + (vx < 0 ? "left" : "right"));
		width = texture.getRegionWidth() * 5;
		height = texture.getRegionHeight() * 5;
	}
	
	public AgeState getAgeState() {
		return ageState;
	}

}
