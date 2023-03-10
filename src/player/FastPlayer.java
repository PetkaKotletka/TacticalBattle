package player;

public class FastPlayer extends Player {
	public FastPlayer() {
		super();
		this._strength = FAST_STRENGTH;
		this._toughness = FAST_TOUGHNESS;
	}
	
	public String toString() {
		return "Fast";
	}

	protected void setCharacterImages() {
		super.setCharacterImages(FAST_UNARMED_IMAGE_PATH, 
								 FAST_MELEE_IMAGE_PATH, 
								 FAST_RANGE_IMAGE_PATH, 
								 FAST_SWEEP_IMAGE_PATH);
	}
	
	protected boolean isClose(int row, int column) {
		if (((row - 1 <= this._row && this._row <= row + 1) && 
			 (column - 1 <= this._column && this._column <= column + 1)) || 
			(row == this._row && 
			 (column - 2 <= this._column && this._column <= column + 2)) ||
			(column == this._column && 
			 (row - 2 <= this._row && this._row <= row + 2))) {
			return true;
		}
		return false;
	}
}
