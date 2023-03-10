package tile;

import java.awt.image.BufferedImage;

public class Tile {
	private BufferedImage _defaultImage, _highlightImage, _redImage;
	public boolean isHighlighted, isActive, isAvailable, isRed;
	
	public Tile() {
		this.isHighlighted = false;
		this.isActive = false;
		this.isAvailable = true;
	}
	
	public void setTextures(BufferedImage defaultImage, BufferedImage highlightImage, BufferedImage redImage) {
		this._defaultImage = defaultImage;
		this._highlightImage = highlightImage;
		this._redImage = redImage;
	}
	
	public BufferedImage getDefaultImage() {
		return this._defaultImage;
	}
	
	public BufferedImage getHighlightImage() {
		return this._highlightImage;
	}
	
	public BufferedImage getRedImage() {
		return this._redImage;
	}
}
