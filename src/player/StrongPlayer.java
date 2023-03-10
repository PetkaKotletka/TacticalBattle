package player;

public class StrongPlayer extends Player {
	public StrongPlayer() {
		super();
		this._strength = STRONG_STRENGTH;
		this._toughness = STRONG_TOUGHNESS;
	}
	
	public String toString() {
		return "Strong";
	}
	
	protected void setCharacterImages() {
		super.setCharacterImages(STRONG_UNARMED_IMAGE_PATH, 
								 STRONG_MELEE_IMAGE_PATH, 
								 STRONG_RANGE_IMAGE_PATH, 
								 STRONG_SWEEP_IMAGE_PATH);
	}
}
