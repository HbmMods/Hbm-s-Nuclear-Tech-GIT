package com.hbm.util;

import com.hbm.config.VersatileConfig;
import com.hbm.interfaces.ICustomWarhead.SaltedFuel.HalfLifeType;
import com.hbm.items.machine.ItemRTGPellet;
import net.minecraft.item.ItemStack;

public class RTGUtil {

	public static short getPower(ItemRTGPellet fuel, ItemStack stack) {
		return VersatileConfig.scaleRTGPower() ? ItemRTGPellet.getScaledPower(fuel, stack) : fuel.getHeat();
	}

	public static boolean hasHeat(ItemStack[] inventory, int[] rtgSlots) {
		for(int slot : rtgSlots) {

			if(inventory[slot] == null)
				continue;

			if(inventory[slot].getItem() instanceof ItemRTGPellet)
				return true;
		}

		return false;
	}

	public static int updateRTGs(ItemStack[] inventory, int[] rtgSlots) {
		int newHeat = 0;
		for(int slot : rtgSlots) {

			if(inventory[slot] == null)
				continue;

			if(!(inventory[slot].getItem() instanceof ItemRTGPellet))
				continue;

			final ItemRTGPellet pellet = (ItemRTGPellet) inventory[slot].getItem();
			newHeat += getPower(pellet, inventory[slot]);
			inventory[slot] = ItemRTGPellet.handleDecay(inventory[slot], pellet);
		}

		return newHeat;

	}

	/**
	 * Gets the lifespan of an RTG based on half-life
	 * @author UFFR
	 * @param halfLife The half-life
	 * @param type Half-life units: {@link#HalfLifeType}
	 * @param realYears Whether or not to use 365 days per year instead of 100 to calculate time
	 * @return The half-life calculated into Minecraft ticks
	 */
	public static long getLifespan(float halfLife, HalfLifeType type, boolean realYears) {
		float life = 0;
		switch (type)
		{
		case LONG:
			life = (48000 * (realYears ? 365 : 100) * 100) * halfLife;
			break;
		case MEDIUM:
			life = (48000 * (realYears ? 365 : 100)) * halfLife;
			break;
		case SHORT:
			life = 48000 * halfLife;
			break;
		}
		return (long) life;
	}
}
