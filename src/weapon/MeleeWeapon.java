package weapon;

public class MeleeWeapon extends Weapon {
	
	public MeleeWeapon() {
		super();
		this._damage = MELEE_WEAPON_DAMAGE;
		this._range = MELEE_WEAPON_RANGE;
	}
	
	public String toString() {
		return "Melee";
	}
}
