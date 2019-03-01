package dev.codenmore.ecjam.entities.player;

import com.badlogic.gdx.math.Rectangle;

public enum AgeState {
	
	//		Slogan				Width	Height		Speed	Jump
	baby("Newborn", 			10 * 5, 11 * 5, 	40f, 	20, new Rectangle(14, 0, 14, 0)),
	teen("Childish Learning", 	12 * 5, 14 * 5, 	180f,	40, new Rectangle(16, 0, 16, 0)),
	adult("Growing Strength", 	16 * 5, 15 * 5, 	160f, 	48, new Rectangle(26, 0, 26, 0)), 
	old("Old and Wise", 		10 * 5, 14 * 5, 	70f, 	35, new Rectangle(16, 0, 16, 0));
	
	public final String slogan;
	public final float width, height;
	public final float speed;
	public final int jumpStrength;
	public final Rectangle collisionOffsets;
	
	private AgeState(String slogan, float width, float height, float speed, int jumpStrength, Rectangle co) {
		this.slogan = slogan;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.jumpStrength = jumpStrength;
		collisionOffsets = co;
	}

}
