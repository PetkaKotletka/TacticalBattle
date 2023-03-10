package player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import tile.TileManager;
import weapon.SweepWeapon;
import weapon.Weapon;

public abstract class Player implements PlayerConstants {
	
	protected int _x, _y;
	protected int _row, _column;
	
	protected double _strength;
	protected double _health;
	protected double _toughness;
	
	protected Weapon _weapon;
	protected String _name;
	
	protected BufferedImage _unarmedImage, _armedImage, _hitImage, _shieldSelectedImage, _shieldActiveImage;
	protected BufferedImage[] _healthImage;
	
	protected Font _nameFont;
	
	protected GamePanel _gp;
	protected TileManager _tm;
	
	private boolean _isActive;
	private boolean _isAlive;
	private int _moves;
	
	private int _shieldState; // 0 - invisible, 1 - highlighted, 2 - activated
	
	public Player() {
		this._isAlive = true;
		this._isActive = false;
		this._health = MAX_HEALTH;
		this._nameFont = new Font(NAME_FONT, Font.BOLD, NAME_SIZE);
		this._moves = 0;
		this._shieldState = 0;
	}
	
	public void setName(String name) {
		this._name = name;
	}
	
	public void setWeapon(Weapon weapon) {
		this._weapon = weapon;
	}
	
	public void setPosition(int row, int column) {
		this._row = row;
		this._column = column;
		this._x = this._gp.SCREEN_PADDING + column * this._gp.TILE_SIZE;
		this._y = this._gp.SCREEN_PADDING + row * this._gp.TILE_SIZE;
	}
	
	public void setGamePanel(GamePanel gp) {
		this._gp = gp;
	}
	
	public void setTileManager(TileManager tm) {
		this._tm = tm;
	}
	
	private void setInfoImages() {
		this._healthImage = new BufferedImage[HEALTH_BAR_STATES];
		try {
			for (int i = 0; i < HEALTH_BAR_STATES; ++i) {
				this._healthImage[i] = ImageIO.read(getClass().getResourceAsStream(HEALTH_BAR_DIR_PATH + 
																				   Integer.toString(HEALTH_BAR_STATES - i - 1) + 
																				   PNG_EXTENTION));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setShieldImages() {
		try {
			this._shieldSelectedImage = ImageIO.read(getClass().getResourceAsStream(SHIELD_SELECTED_IMAGE_PATH));
			this._shieldActiveImage = ImageIO.read(getClass().getResourceAsStream(SHIELD_ACTIVE_IMAGE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void setCharacterImages(String unarmedPath, String meleePath, String rangePath, String sweepPath) {
		try {
			this._unarmedImage = ImageIO.read(getClass().getResourceAsStream(unarmedPath));
			String armedImagePath = unarmedPath;
			switch (this._weapon.toString()) {
			case "Melee":
				armedImagePath = meleePath;
				break;
			case "Range":
				armedImagePath = rangePath;
				break;
			case "Sweep":
				armedImagePath = sweepPath;
				break;
			}
			this._armedImage = ImageIO.read(getClass().getResourceAsStream(armedImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void setCharacterImages();
	
	public void setImages() {
		this.setInfoImages();
		this.setShieldImages();
		this.setCharacterImages();
	}
	
	public abstract String toString();
	
	public String getName() {
		return this._name;
	}
	
	public boolean isAlive() {
		return this._isAlive;
	}
	
	private void resetShield() {
		this._shieldState = SHIELD_DISABLED_STATE;
	}
	
	private void updateMoves() {
		this._moves = MOVES;
	}
	
	public boolean noMoreMoves() {
		return (this._moves == 0);
	}
	
	public void deactivate() {
		this._isActive = false;
	}
	
	public void activate() {
		this._isActive = true;
		this.updateMoves();
		this.resetShield();
	}
	
	public boolean isActive() {
		return this._isActive;
	}
	
	public double getHealth() {
		return this._health;
	}
	
	protected int getHelthState() {
		if (this._health == MAX_HEALTH) {
			return HEALTH_BAR_STATES - 1;
		}
		return (int)(this._health / HEALTH_IN_ONE_STATE);
	}
	
	public boolean isThisTile(int row, int column) {
		return (row == this._row) && (column == this._column);
	}
	
	public boolean inRange(int row, int column) {
		int offset = this._gp.TILE_SIZE / 2;
		int tileX = this._gp.SCREEN_PADDING + column * this._gp.TILE_SIZE + offset;
		int tileY = this._gp.SCREEN_PADDING + row * this._gp.TILE_SIZE + offset;
		double dist = Point.distance(tileX, tileY, this._x + offset, this._y + offset);
		return dist < this._weapon.getRange(this._gp.TILE_SIZE);
	}
	
	protected boolean isClose(int row, int column) {
		if ((row - 1 <= this._row && this._row <= row + 1) && 
			(column - 1 <= this._column && this._column <= column + 1)) {
			return true;
		}
		return false;
	}
	
	private double computeHitDamage(double strength, double damage) {
		return damage * strength;
	}
	
	private double computeTakenDamage(double damage, double toughness) {
		return damage * toughness;
	}
	
	private void decreaseMoves() {
		if (this._moves > 0) {
			this._moves--;
		}
	}
	
	private void nullifyMoves() {
		this._moves = 0;
	}
	
	public void hit(Player other) {
		other.takeDamage(this.computeHitDamage(this._strength, this._weapon.getDamage()));
	}
	
	public void takeDamage(double damage) {
		double toughness = this._toughness;
		if (this._shieldState == SHIELD_ACTIVE_STATE) {
			toughness *= SHIELD_TOUGHNESS_INCREASE;
		}
		this._health -= this.computeTakenDamage(damage, toughness);
		if (this._health <= 0.) {
			this._health = 0.;
			this._isAlive = false;
		}
	}
	
	public void update() {
		if (!this._isActive)
			return;
		if (this._shieldState != SHIELD_ACTIVE_STATE) {
			this._shieldState = SHIELD_DISABLED_STATE;
		}
		if (this._tm.mouseOnTile(this._row, this._column)) {
			if (this._tm.tileClicked(this._row, this._column)) {
				// shield
				this._shieldState = SHIELD_ACTIVE_STATE;
				this.nullifyMoves();
			} else {
				this._shieldState = SHIELD_SELECTED_STATE;
			}
		}
		for (int row = 0; row < this._gp.ROWS; ++row) {
			for (int column = 0; column < this._gp.COLUMNS; ++column) {
				if (this.isClose(row, column) && 
					this._tm.mouseOnTile(row, column) &&
				    !this._gp.isPlayerOnTile(row, column) && 
				    this._tm.map[row][column].isAvailable) {
					if (this._tm.tileClicked(row, column)) {
						// move
						this.setPosition(row, column);
						this.decreaseMoves();
					} else {
						this._tm.map[row][column].isHighlighted = true;
					}
				} else {
					this._tm.map[row][column].isHighlighted = false;
				}
				if (this.inRange(row, column) && 
					this._gp.isPlayerOnTile(row, column) && 
					!this.isThisTile(row, column) && 
					this._tm.tileClicked(row, column)) {
					// hit
					if (this._weapon.toString() == "Sweep" ) {
						((SweepWeapon) this._weapon).hitSweep(this, this._gp);
					} else {
						this.hit(this._gp.getPlayerOnTile(row, column));
					}
					this.decreaseMoves();
				}
			}
		}
	}
	
	private void drawCharacter(Graphics2D g2) {
		g2.drawImage(this._armedImage, 
				     this._x,
				     this._y, 
				     this._gp.TILE_SIZE, 
				     this._gp.TILE_SIZE, 
				     null);
	}
	
	private void drawHealth(Graphics2D g2) {
		g2.drawImage(this._healthImage[this.getHelthState()], 
					 this._x,
				     this._y + HEALTH_BAR_OFFSET, 
				     this._gp.TILE_SIZE, 
				     this._gp.TILE_SIZE, 
				     null);
	}
	
	private void drawName(Graphics2D g2) {
		g2.setFont(this._nameFont);
		g2.setColor(NAME_COLOR);
		g2.drawString(this._name,
				      this._x, 
				      this._y - NAME_OFFSET);
	}
	
	private void drawShield(Graphics2D g2) {
		switch (this._shieldState) {
		case 1:
			g2.drawImage(this._shieldSelectedImage, 
					     this._x,
					     this._y, 
					     this._gp.TILE_SIZE, 
					     this._gp.TILE_SIZE, 
					     null);
			break;
		case 2:
			g2.drawImage(this._shieldActiveImage, 
				     this._x,
				     this._y, 
				     this._gp.TILE_SIZE, 
				     this._gp.TILE_SIZE, 
				     null);
			break;
		}
	}
	
	private void drawRangeCircle(Graphics2D g2) {
		int r = this._weapon.getRange(this._gp.TILE_SIZE);
		int offset = this._gp.TILE_SIZE / 2;
		g2.setColor(Color.RED);
		g2.drawOval(this._x + offset - r, 
				    this._y + offset - r, 
				    r * 2, 
				    r * 2);
	}
	
	public void draw(Graphics2D g2) {
		this.drawCharacter(g2);
		this.drawHealth(g2);
		this.drawName(g2);
		this.drawShield(g2);
		this.drawRangeCircle(g2);
	}
}
