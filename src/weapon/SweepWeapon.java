package weapon;

import main.GamePanel;
import player.Player;

public class SweepWeapon extends Weapon {
	public SweepWeapon() {
		super();
		this._damage = SWEEP_WEAPON_DAMAGE;
		this._range = SWEEP_WEAPON_RANGE;
	}
	
	public String toString() {
		return "Sweep";
	}
	
	public void hitSweep(Player p, GamePanel gp) {
		for (int row = 0; row < gp.ROWS; ++row) {
			for (int column = 0; column < gp.COLUMNS; ++column) {
				if (!p.isThisTile(row, column) &&
					gp.isPlayerOnTile(row, column) &&
					p.inRange(row, column)) {
					p.hit(gp.getPlayerOnTile(row, column));
				}
			}
		}
	}
}
