package com.hbm.items.weapon.grenade;

import com.hbm.items.ItemEnumMulti;

public class ItemGrenadeShell extends ItemEnumMulti {
	
	/*
	 *  __________
	 * |          | ________ SHELL COLOR - Changes based on filling
	 * |   _____  |
	 * |  |    _____________ LABEL COLOR - Changes based on filling
	 * |  |_____| |
	 *  \________/__________ FUZE INDICATOR RING - Changes based on filling
	 *   \______/
	 *    |    |
	 *    |    |
	 *    |   ______________ MISC - Remains static for this shell
	 *    |    |
	 *    |    |
	 *    |    |
	 *    |    |
	 *    |    |
	 *    /    \
	 *   |______|
	 *     {__}
	 */

	public ItemGrenadeShell() {
		super(EnumGrenadeShell.class, true, true);
	}
	
	public static enum EnumGrenadeShell {
		FRAG(4, 20, 0.5D),		// bonus fragmentation
		STICK(4, 20, 0.25D),	// thrown farther
		TECH(2, 20, 0.5D),		// casing with electronics for EMP/plasma
		NUKE(1, 20, 0.25D);		// nuka grenade casing for high yield grenades
		
		private int stackLimit;
		private int drawDuration;
		private double bounceModifier = 1D;
		
		private EnumGrenadeShell(int stackLimit, int drawDuration, double bounceModifier) {
			this.stackLimit = stackLimit;
			this.drawDuration = drawDuration;
			this.bounceModifier = bounceModifier;
		}

		public int getStackLimit() { return stackLimit; }
		public int getDrawDuration() { return drawDuration; }
		public double getBounce() { return bounceModifier; }
	}
}
