package dev.codenmore.ecjam.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;

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
		getLevel().centerOn(getBounds(), delta);
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
		if(Gdx.input.isKeyJustPressed(Keys.E) && canGoToNextAgeState()) {
			// Check which state to transition to
			ageState = getNextAgeState();
			// Set our new speed
			speed = ageState.speed;
		}
		
		// Set proper texture
		texture = Assets.getRegion("player/" + ageState.toString() + "_" + (vx < 0 ? "left" : "right"));
		width = ageState.width;
		height = ageState.height;
	}
	
	private boolean canGoToNextAgeState() {
		if(!isOnGround() || isMoving()) return false;
		AgeState ns = getNextAgeState();
		Rectangle b = new Rectangle(x, y, ns.width, ns.height);
		return !getLevel().anyTileSolidWithin(b);
	}
	
	private AgeState getNextAgeState() {
		// Get next age state
		if(ageState == AgeState.teen) {
			return AgeState.adult;
		}else if(ageState == AgeState.adult) {
			return AgeState.old;
		}else if(ageState == AgeState.old) {
			return AgeState.baby;
		}
		// Baby to teen here
		return AgeState.teen;
	}
	
	public AgeState getAgeState() {
		return ageState;
	}

}
