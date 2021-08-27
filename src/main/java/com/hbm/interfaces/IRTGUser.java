package com.hbm.interfaces;

import java.util.List;

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
	/** Get the heat provided by the RTGs **/
	public int getHeat();
	/**
	 * Update all of the RTG pellets in a section of a machine
	 * @param inventory - Total inventory
	 * @param rtgIn - Slot numbers for the RTG section
	 * @param worldIn - The machine's world object
	 * @return The total heat level
	 */
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
	default int updateRTGs(ItemStack[] inventory, World worldIn)
	{
		int newHeat = 0;
		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] == null)
				continue;
			if (!(inventory[i].getItem() instanceof ItemRTGPellet))
				continue;
			ItemRTGPellet pellet = (ItemRTGPellet) inventory[i].getItem();
			newHeat += pellet.heat;
			if (pellet.doesDecay)
				if (worldIn.rand.nextInt(pellet.decayChance) == 0)
					inventory[i] = pellet.decayItem;
		}
		return newHeat;
	}
	/**
	 * Update all of the RTG pellets in a list of a machine
	 * @param rtgList - The list of RTGs the machine uses
	 * @param worldIn - The machine's world object
	 * @return The total heat level
	 */
	@Untested
	default int updateRTGs(List<ItemRTGPellet> rtgList, World worldIn)
	{
		int newHeat = 0;
		
		for (ItemRTGPellet pellet : rtgList)
		{
			newHeat += pellet.heat;
			if (pellet.doesDecay)
				if (worldIn.rand.nextInt(pellet.decayChance) == 0)
					rtgList.remove(pellet);
		}
		
		return newHeat;
	}
	/**
	 * Update all of the RTG pellets in a list of a machine and allows for decay items to accumulate
	 * @param rtgList - The list of RTGs the machine uses
	 * @param deplList - The list of depleted items
	 * @param worldIn - The machine's world object
	 * @return The total heat level
	 */
	@Untested
	default int updateRTGs(List<ItemRTGPellet> rtgList, List<ItemStack> deplList, World worldIn)
	{
		int newHeat = 0;
		
		for (ItemRTGPellet pellet : rtgList)
		{
			newHeat += pellet.heat;
			if (pellet.doesDecay)
			{
				if (worldIn.rand.nextInt(pellet.decayChance) == 0)
				{
					rtgList.remove(pellet);
					boolean alreadyExists = false;
					int index;
					for (int i = 0; i < deplList.size(); i++)
					{
						if (deplList.get(i).getItem() == pellet.decayItem.getItem() && deplList.get(i).getItemDamage() == pellet.decayItem.getItemDamage() && deplList.get(i).stackSize + pellet.decayItem.stackSize <= 64)
							alreadyExists = true;
						else if (deplList.get(i).stackSize + pellet.decayItem.stackSize > 64)
							continue;
						
						if (alreadyExists)
							break;
					}
					if (alreadyExists)
					{
						for (int i = 0; i < deplList.size(); i++)
							if (deplList.get(i).getItem() == pellet.decayItem.getItem() && deplList.get(i).getItemDamage() == pellet.decayItem.getItemDamage() && deplList.get(i).stackSize + pellet.decayItem.stackSize <= 64)
								deplList.get(i).stackSize += pellet.decayItem.stackSize;
					}
					else
						deplList.add(pellet.decayItem);
				}
			}
		}
		
		return newHeat;
	}
}
