package dev.codenmore.ecjam.entities.player;

public enum AgeState {
	
	baby("Newborn", 10 * 5, 11 * 5, 25f, 0),
	teen("Childish Learning", 12 * 5, 14 * 5, 140f, 40),
	adult("Growing Strength", 16 * 5, 15 * 5, 115f, 55), 
	old("Old and Wise", 10  * 5, 14 * 5, 50f, 35);
	
	public final String slogan;
	public final float width, height;
	public final float speed;
	public final int jumpStrength;
	
	private AgeState(String slogan, float width, float height, float speed, int jumpStrength) {
		this.slogan = slogan;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.jumpStrength = jumpStrength;
	}

}
