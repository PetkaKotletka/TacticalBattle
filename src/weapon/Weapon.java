package weapon;

public abstract class Weapon implements WeaponConstants {
	protected double _damage;
	protected double _range;
	
	public Weapon() {
	}
	
	public int getRange(int tileSize) {
		return (int)(this._range * (double)tileSize);
	}
	
	public double getDamage() {
		return this._damage;
	}
	
	public String toString() {
		return "Not implemented";
	}
}
