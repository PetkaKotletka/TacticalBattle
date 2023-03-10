package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.MouseHandler;

public class TileManager {
	private GamePanel _gp;
	private MouseHandler _mouseHandler;
	
	public Tile[][] map;
	
	public TileManager(GamePanel gp, MouseHandler mh) {
		this._gp = gp;
		this.map = new Tile[this._gp.ROWS][this._gp.COLUMNS];
		this.getTextures();
		this._mouseHandler = mh;
	}
	
	private void getTextures() {
		try {
			for (int row = 0; row < this._gp.ROWS; ++row) {
				for (int column = 0; column < this._gp.COLUMNS; ++column) {
					this.map[row][column] = new Tile();
					this.map[row][column].setTextures(ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")), 
													  ImageIO.read(getClass().getResourceAsStream("/tiles/grassHighlighted.png")),
													  ImageIO.read(getClass().getResourceAsStream("/tiles/grassRed.png")));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void drawTile(int row, int column, BufferedImage img, Graphics2D g2) {
		g2.drawImage(img, 
					 this._gp.SCREEN_PADDING + column * this._gp.TILE_SIZE, 
					 this._gp.SCREEN_PADDING + row * this._gp.TILE_SIZE, 
					 this._gp.TILE_SIZE, 
					 this._gp.TILE_SIZE, 
					 null);
	}
	
	public boolean mouseOnTile(int row, int column) {
		return (this._gp.SCREEN_PADDING + column * this._gp.TILE_SIZE < this._mouseHandler.mousePosition.x &&
				this._mouseHandler.mousePosition.x < this._gp.SCREEN_PADDING + (column + 1) * this._gp.TILE_SIZE && 
				this._gp.SCREEN_PADDING + row * this._gp.TILE_SIZE < this._mouseHandler.mousePosition.y &&
				this._mouseHandler.mousePosition.y < this._gp.SCREEN_PADDING + (row + 1) * this._gp.TILE_SIZE);
	}
	
	public boolean tileClicked(int row, int column) {
		return (this._mouseHandler.isClicked  && 
				this._gp.SCREEN_PADDING + column * this._gp.TILE_SIZE < this._mouseHandler.lastClickPosition.x &&
				this._mouseHandler.lastClickPosition.x < this._gp.SCREEN_PADDING + (column + 1) * this._gp.TILE_SIZE && 
				this._gp.SCREEN_PADDING + row * this._gp.TILE_SIZE < this._mouseHandler.lastClickPosition.y &&
				this._mouseHandler.lastClickPosition.y < this._gp.SCREEN_PADDING + (row + 1) * this._gp.TILE_SIZE);
	}
	
	public void draw(Graphics2D g2) {
		for (int row = 0; row < this._gp.ROWS; ++row) {
			for (int column = 0; column < this._gp.COLUMNS; ++column) {
				if (this.map[row][column].isHighlighted) {
					this.drawTile(row, column, this.map[row][column].getHighlightImage(), g2);
				} else {
					this.drawTile(row, column, this.map[row][column].getDefaultImage(), g2);
				}
			}
		}
	}
}
