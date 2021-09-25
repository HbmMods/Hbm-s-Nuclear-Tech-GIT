package com.hbm.interfaces;

import java.util.ArrayList;

import com.google.common.annotations.Beta;

import net.minecraft.item.Item;
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
	/** Item class that only works in the machine, simply return {@link#IRadioisotopeFuel} to accept all **/
	public Class getDesiredClass();
	
	public default boolean isItemValid(Item itemIn)
	{
		return itemIn instanceof IRadioisotopeFuel && getDesiredClass().isAssignableFrom(itemIn.getClass());
	}
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
			if (!isItemValid(inventory[slot].getItem()))
				continue;
			final IRadioisotopeFuel pellet = (IRadioisotopeFuel) inventory[slot].getItem();
			newHeat += pellet.getPower();
			IRadioisotopeFuel.handleDecay(inventory[slot], pellet);
		}
		return newHeat;
	}
	/**
	 * Update all of the RTG pellets in the entire machine's inventory
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
			if (!isItemValid(inventory[i].getItem()))
				continue;
			final IRadioisotopeFuel pellet = (IRadioisotopeFuel) inventory[i].getItem();
			newHeat += pellet.getPower();
			IRadioisotopeFuel.handleDecay(inventory[i], pellet);
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
	@Beta
	default int updateRTGs(ArrayList<ItemStack> rtgList, World worldIn)
	{
		int newHeat = 0;
		
		for (ItemStack pellet : rtgList)
		{
			final IRadioisotopeFuel fuel = (IRadioisotopeFuel) pellet.getItem();
			newHeat += fuel.getPower();
			if (fuel.getDoesDecay())
			{
				if (fuel.getLifespan(pellet) <= 0)
					rtgList.remove(pellet);
				else
					fuel.decay(pellet);
			}
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
	@Beta
	default int updateRTGs(ArrayList<ItemStack> rtgList, ArrayList<ItemStack> deplList, World worldIn)
	{
		int newHeat = 0;
		
		for (ItemStack pellet : rtgList)
		{
			final IRadioisotopeFuel fuel = (IRadioisotopeFuel) pellet.getItem();
			newHeat += fuel.getPower();
			if (fuel.getDoesDecay())
			{
				if (fuel.getLifespan(pellet) <= 0)
				{
					rtgList.remove(pellet);
					boolean alreadyExists = false;
					int index;
					final ItemStack decayItem = fuel.getDecayItem();
					for (int i = 0; i < deplList.size(); i++)
					{
						if (deplList.get(i).getItem() == decayItem.getItem() && deplList.get(i).getItemDamage() == decayItem.getItemDamage() && deplList.get(i).stackSize + decayItem.stackSize <= decayItem.getMaxStackSize())
							alreadyExists = true;
						else if (deplList.get(i).stackSize + decayItem.stackSize > decayItem.getMaxStackSize())
							continue;
						
						if (alreadyExists)
							break;
					}
					if (alreadyExists)
					{
						for (int i = 0; i < deplList.size(); i++)
							if (deplList.get(i).getItem() == decayItem.getItem() && deplList.get(i).getItemDamage() == decayItem.getItemDamage() && deplList.get(i).stackSize + decayItem.stackSize <= decayItem.getMaxStackSize())
								deplList.get(i).stackSize += decayItem.stackSize;
					}
					else
						deplList.add(fuel.getDecayItem());
				}
			}
		}
		
		return newHeat;
	}
}
