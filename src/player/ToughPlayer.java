package player;

public class ToughPlayer extends Player {
	public ToughPlayer() {
		super();
		this._strength = TOUGH_STRENGTH;
		this._toughness = TOUGH_TOUGHNESS;
	}
	
	public String toString() {
		return "Tough";
	}
	
	public void setCharacterImages() {
		super.setCharacterImages(TOUGH_UNARMED_IMAGE_PATH, 
								 TOUGH_MELEE_IMAGE_PATH, 
								 TOUGH_RANGE_IMAGE_PATH, 
								 TOUGH_SWEEP_IMAGE_PATH);
	}
}
