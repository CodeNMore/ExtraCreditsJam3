package dev.codenmore.ecjam.entities.player;

public enum AgeState {
	
	baby("Newborn", 25f, 0),
	teen("Childish Learning", 110f, 40),
	adult("Growing Strength", 85f, 55), 
	old("Old and Wise", 40f, 35);
	
	public final String slogan;
	public final float speed;
	public final int jumpStrength;
	
	private AgeState(String slogan, float speed, int jumpStrength) {
		this.slogan = slogan;
		this.speed = speed;
		this.jumpStrength = jumpStrength;
	}

}
