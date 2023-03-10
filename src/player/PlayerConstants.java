package player;

import java.awt.Color;

public interface PlayerConstants {
	String NAME_FONT = "Arial";
	int NAME_SIZE = 17;
	Color NAME_COLOR = Color.DARK_GRAY;
	int HEALTH_BAR_OFFSET = 50;
	int NAME_OFFSET = 5;
	
	int MOVES = 2;
	
	double MAX_HEALTH = 60.;    // absolute
	int HEALTH_BAR_STATES = 12; // number of images (don't change)
	double HEALTH_IN_ONE_STATE = MAX_HEALTH / (double)HEALTH_BAR_STATES;
	String HEALTH_BAR_DIR_PATH = "/healthBar/";
	String SHIELD_ACTIVE_IMAGE_PATH = "/shield/shieldActive.png";
	String SHIELD_SELECTED_IMAGE_PATH = "/shield/shieldSelected.png";
	double SHIELD_TOUGHNESS_INCREASE = 0.1;
	int SHIELD_ACTIVE_STATE = 2;
	int SHIELD_SELECTED_STATE = 1;
	int SHIELD_DISABLED_STATE = 0;
	String PNG_EXTENTION = ".png";
	
	double STRONG_STRENGTH = 1.5;   // hittingDamage * strength
	double STRONG_TOUGHNESS = 1.2;  // takenDamage * toughness
	String STRONG_UNARMED_IMAGE_PATH = "/strong/strongUnarmed.png";
	String STRONG_MELEE_IMAGE_PATH = "/strong/strongUnarmed.png";
	String STRONG_RANGE_IMAGE_PATH = "/strong/strongRange.png";
	String STRONG_SWEEP_IMAGE_PATH = "/strong/strongSweep.png";
	
	double FAST_STRENGTH = 0.8;
	double FAST_TOUGHNESS = 1.;
	String FAST_UNARMED_IMAGE_PATH = "/fast/fastUnarmed.png";
	String FAST_MELEE_IMAGE_PATH = "/fast/fastMelee.png";
	String FAST_RANGE_IMAGE_PATH = "/fast/fastRange.png";
	String FAST_SWEEP_IMAGE_PATH = "/fast/fastSweep.png";
	
	double TOUGH_STRENGTH = 1.;
	double TOUGH_TOUGHNESS = 0.6;
	String TOUGH_UNARMED_IMAGE_PATH = "/tough/toughUnarmed.png";
	String TOUGH_MELEE_IMAGE_PATH = "/tough/toughMelee.png";
	String TOUGH_RANGE_IMAGE_PATH = "/tough/toughRange.png";
	String TOUGH_SWEEP_IMAGE_PATH = "/tough/toughSweep.png";
}
