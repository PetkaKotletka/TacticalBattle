package player;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.TextLabel;

public class PlayerSetup implements PlayerConstants {
	
	public final int SELECTORS_OFFSET = 30;
	public final int BUTTONS_OFFSET = 65;
	public final int NAME_OFFSET = 30;
	public final Font FONT = new Font("Comic Sans MS", Font.BOLD, 25);
	
	private int _x, _y;
	private int _imgSize;
	private int _typeIdx, _weaponIdx;
	private String[] _types = {"TOUGH", "STRONG", "FAST"};
	private String[] _weapons = {"MELEE", "RANGE", "SWEEP"};
	private BufferedImage _image;
	private String _name;
	private TextLabel _type, _weapon, _leftTypeButton, _rightTypeButton, _leftWeaponButton, _rightWeaponButton, _nameLabel;
	
	public PlayerSetup(int x, int y, int imgSize, String name) {
		this._x = x;
		this._y = y;
		this._imgSize = imgSize;
		this._name = name;
		this._typeIdx = 0;
		this._weaponIdx = 0;
		this._nameLabel = new TextLabel(this._name, new Point(this._x + this._imgSize / 2, this._y- NAME_OFFSET), FONT);
		this._type = new TextLabel(this._types[0], new Point(this._x + this._imgSize / 2, this._y + this._imgSize + SELECTORS_OFFSET), FONT);
		this._weapon = new TextLabel(this._weapons[0], new Point(this._x + this._imgSize / 2, this._y + this._imgSize + SELECTORS_OFFSET * 2), FONT);
		this._leftTypeButton = new TextLabel("<", new Point(this._x + this._imgSize / 2 - BUTTONS_OFFSET, this._y + this._imgSize + SELECTORS_OFFSET), FONT);
		this._rightTypeButton = new TextLabel(">", new Point(this._x + this._imgSize / 2 + BUTTONS_OFFSET, this._y + this._imgSize + SELECTORS_OFFSET), FONT);
		this._leftWeaponButton = new TextLabel("<", new Point(this._x + this._imgSize / 2 - BUTTONS_OFFSET, this._y + this._imgSize + SELECTORS_OFFSET * 2), FONT);
		this._rightWeaponButton = new TextLabel(">", new Point(this._x + this._imgSize / 2 + BUTTONS_OFFSET, this._y + this._imgSize + SELECTORS_OFFSET * 2), FONT);
	}
	
	private String getPath(String character, String weapon) throws IllegalArgumentException, 
																   IllegalAccessException, 
																   NoSuchFieldException, 
																   SecurityException {
		String fieldName = character + "_" + weapon + "_IMAGE_PATH";
		return (String)this.getClass().getField(fieldName).get(this.getClass().getField(fieldName));
	}
	
	public PlayerDescription getInfo() {
		PlayerDescription answer = new PlayerDescription();
		answer.name = this._name;
		answer.type = this._types[this._typeIdx];
		answer.weapon = this._weapons[this._weaponIdx];
		return answer;
	}
	
	private void increaseTypeIdx() {
		this._typeIdx = (this._typeIdx + 1) % this._types.length;
	}
	
	private void decreaseTypeIdx() {
		if (this._typeIdx == 0) {
			this._typeIdx = this._types.length - 1;
		} else {
			this._typeIdx--;
		}
	}
	
	private void increaseWeaponIdx() {
		this._weaponIdx = (this._weaponIdx + 1) % this._weapons.length;
	}
	
	private void decreaseWeaponIdx() {
		if (this._weaponIdx == 0) {
			this._weaponIdx = this._weapons.length - 1;
		} else {
			this._weaponIdx--;
		}
	}
	
	public void update(Point mouse, boolean isClicked) {
		if (this._leftTypeButton.isMouseOnLabel(mouse)) {
			this._leftTypeButton.setShadow(false);
			if (isClicked) {
				this.decreaseTypeIdx();
			}
		} else {
			this._leftTypeButton.setShadow(true);
		}
		if (this._rightTypeButton.isMouseOnLabel(mouse)) {
			this._rightTypeButton.setShadow(false);
			if (isClicked) {
				this.increaseTypeIdx();
			}
		} else {
			this._rightTypeButton.setShadow(true);
		}
		if (this._leftWeaponButton.isMouseOnLabel(mouse)) {
			this._leftWeaponButton.setShadow(false);
			if (isClicked) {
				this.decreaseWeaponIdx();
			}
		} else {
			this._leftWeaponButton.setShadow(true);
		}
		if (this._rightWeaponButton.isMouseOnLabel(mouse)) {
			this._rightWeaponButton.setShadow(false);
			if (isClicked) {
				this.increaseWeaponIdx();
			}
		} else {
			this._rightWeaponButton.setShadow(true);
		}
	}
	
	public void draw(Graphics2D g2) {
		try {
			this._image = ImageIO.read(getClass().getResourceAsStream(this.getPath(this._types[this._typeIdx], this._weapons[this._weaponIdx])));
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | IOException e) {
			e.printStackTrace();
		}
		g2.drawImage(this._image, this._x, this._y, this._imgSize, this._imgSize, null);
		this._type.setText(this._types[this._typeIdx]);
		this._type.draw(g2);
		this._weapon.setText(this._weapons[this._weaponIdx]);
		this._weapon.draw(g2);
		this._nameLabel.draw(g2);
		this._leftTypeButton.draw(g2, true);
		this._rightTypeButton.draw(g2, true);
		this._leftWeaponButton.draw(g2, true);
		this._rightWeaponButton.draw(g2, true);
	}
}
