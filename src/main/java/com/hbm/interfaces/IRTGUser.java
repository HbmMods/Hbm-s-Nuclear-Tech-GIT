package com.hbm.interfaces;

import com.hbm.items.machine.ItemRTGPellet;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
/**
 * For machines that use RTG pellets
 * @author UFFR
 *
 */
public interface IRTGUser
{
	/**
	 * Update all of the RTG pellets in a section of a machine
	 * @param inventory - Total inventory
	 * @param rtgIn - Slot numbers for the RTG section
	 * @param worldIn - The machine's world object
	 * @return The total heat level
	 */
	@Untested
	default int updateRTGs(ItemStack[] inventory, int[] rtgIn, World worldIn)
	{
		int newHeat = 0;
		for (int slot : rtgIn)
		{
			if (inventory[slot] == null)
				continue;
			if (!(inventory[slot].getItem() instanceof ItemRTGPellet))
				continue;
			ItemRTGPellet pellet = (ItemRTGPellet) inventory[slot].getItem();
			newHeat += pellet.heat;
			if (pellet.doesDecay)
				if (worldIn.rand.nextInt(pellet.decayChance) == 0)
					inventory[slot] = pellet.decayItem;
		}
		return newHeat;
	}
	/**
	 * Update all of the RTG pellets in a section of a machine
	 * @param inventory - Total inventory
	 * @param worldIn - The machine's world object
	 * @return The total heat level
	 */
	@Untested
	default int updateRTGs(ItemStack[] inventory, World worldIn)
	{
		int newHeat = 0;
		for (ItemStack stack : inventory)
		{
			if (stack == null)
				continue;
			if (!(stack.getItem() instanceof ItemRTGPellet))
				continue;
			ItemRTGPellet pellet = (ItemRTGPellet) stack.getItem();
			newHeat += pellet.heat;
			if (pellet.doesDecay)
				if (worldIn.rand.nextInt(pellet.decayChance) == 0)
					stack = pellet.decayItem;
		}
		return newHeat;
	}

}
