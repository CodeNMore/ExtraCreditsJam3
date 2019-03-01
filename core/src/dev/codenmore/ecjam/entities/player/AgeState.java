package dev.codenmore.ecjam.entities.player;

public enum AgeState {
	
	//		Slogan				Width	Height		Speed	Jump
	baby("Newborn", 			10 * 5, 11 * 5, 	40f, 	20),
	teen("Childish Learning", 	12 * 5, 14 * 5, 	180f,	40),
	adult("Growing Strength", 	16 * 5, 15 * 5, 	160f, 	48), 
	old("Old and Wise", 		10 * 5, 14 * 5, 	70f, 	35);
	
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
