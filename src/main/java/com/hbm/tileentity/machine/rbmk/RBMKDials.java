package com.hbm.tileentity.machine.rbmk;

import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class RBMKDials {
	
	public static final String KEY_PASSIVE_COOLING = "dialPassiveCooling";
	
	public static void createDials(World world) {
		GameRules rules = world.getGameRules();
		
		if(!rules.getGameRuleStringValue(KEY_PASSIVE_COOLING).isEmpty())
			rules.setOrCreateGameRule(KEY_PASSIVE_COOLING, "5.0");
	}

	public static double getPassiveCooling(World world) {
		return shittyWorkaroundParseDouble(world.getGameRules().getGameRuleStringValue(KEY_PASSIVE_COOLING), 5.0D);
	}
	
	//why make the double representation accessible in a game rule when you can just force me to add a second pointless parsing operation?
	public static double shittyWorkaroundParseDouble(String s, double def) {
		
		try {
			return Double.parseDouble(s);
		} catch(Exception ex) { }
		
		return def;
	}
}
