package dev.codenmore.ecjam.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import dev.codenmore.ecjam.assets.Assets;
import dev.codenmore.ecjam.entities.Entity;
import dev.codenmore.ecjam.entities.MovableEntity;
import dev.codenmore.ecjam.entities.particles.Particle;

public class Player extends MovableEntity {
	
	private AgeState ageState;
	private int evolutionAvailable = -1;
	private int lives;
	private boolean jumpAllow = true;

	public Player(float x, float y, int lives) {
		super("Player", x, y, 64, 64);
		solid = true;
		this.lives = lives;
		
		updateState(AgeState.baby);
	}
	
	@Override
	public void tick(float delta) {
		// Process controls
		updateControls(delta);
		
		if(Gdx.input.isKeyPressed(Keys.G))
			vy = 200;
		
		// Apply gravity
		processMovements(delta);
		
		// Update state information
		updateState(null);
		
		// Center on us
		getLevel().centerOn(getRenderBounds(), delta);
	}
	
	private void updateControls(float delta) {
		vx = 0f;
		if(Gdx.input.isKeyPressed(Keys.A))
			vx = -speed;
		else if(Gdx.input.isKeyPressed(Keys.D))
			vx = speed;
		
		if(jumpAllow && Gdx.input.isKeyJustPressed(Keys.SPACE))
			jump(ageState.jumpStrength);
	}
	
	private void updateState(AgeState ns) {
		// Check if changing state
		if((evolutionAvailable != 0 && Gdx.input.isKeyJustPressed(Keys.E) && canGoToNextAgeState()) || ns != null) {
			// Check which state to transition to
			ageState = (ns != null ? ns : getNextAgeState());
			if(ns == null && ageState == AgeState.baby) {
				lives--;
			}
			// Set our new speed
			speed = ageState.speed;
			// Set new collisions
			collisionOffsets.set(ageState.collisionOffsets);
			// Set proper sizes
			width = ageState.width;
			height = ageState.height;
			if(evolutionAvailable > 0)
				evolutionAvailable--; //negative stays infinite
			// Can push?
			canPushOthers = ageState.canPushOthers;
			// BOOM! Particles!
			if(ns == null) {
				Particle.spawnParticles(manager, x + width / 2, y + height / 2, 25, 25, 
						1, 6, 250, 250, 
						-50, 50, -30, 200, 
						0.5f, 2.5f, new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 0.5f));
			}
		}
		// Always set texture
		texture = Assets.getRegion("player/" + ageState.toString() + "_" + (vx < 0 ? "left" : "right"));
	}
	
	private boolean canGoToNextAgeState() {
		if(!isOnGround() || isMoving()) return false;
		collisionOffsets = getNextAgeState().collisionOffsets;
		width = getNextAgeState().width;
		height = getNextAgeState().height;
		boolean se = true;
		Array<Entity> ents = manager.getSolidEntities();
		for(int i = 0;i < ents.size;i++) {
			Entity e = ents.get(i);
			if(e.equals(this))
				continue;
			if(e.isSolid() && e.getCollisionBounds().overlaps(getCollisionBounds())) {
				se = false;
				break;
			}
		}
		boolean ret = !getLevel().anyTileSolidWithin(getCollisionBounds()) && se;
		collisionOffsets = ageState.collisionOffsets;
		width = ageState.width;
		height = ageState.height;
		return ret;
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

	public int getEvolutionAvailable() {
		return evolutionAvailable;
	}

	public void setEvolutionAvailable(int evolutionAvailable) {
		this.evolutionAvailable = evolutionAvailable;
	}

	public boolean isJumpAllow() {
		return jumpAllow;
	}

	public void setJumpAllow(boolean jumpAllow) {
		this.jumpAllow = jumpAllow;
	}
	
	public int getLives() {
		return lives;
	}

}
