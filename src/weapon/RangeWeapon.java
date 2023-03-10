package weapon;

public class RangeWeapon extends Weapon {
	public RangeWeapon() {
		super();
		this._damage = RANGE_WEAPON_DAMAGE;
		this._range = RANGE_WEAPON_RANGE;
	}
	
	public String toString() {
		return "Range";
	}
}
