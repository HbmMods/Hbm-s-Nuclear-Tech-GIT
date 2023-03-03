package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.annotations.Beta;
import com.hbm.config.VersatileConfig;
import com.hbm.interfaces.Untested;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
	public Class<? extends IRadioisotopeFuel> getDesiredClass();
	
	public default boolean isItemValid(Item itemIn)
	{
		return itemIn instanceof IRadioisotopeFuel && getDesiredClass().isAssignableFrom(itemIn.getClass());
	}
	
	public static short getPower(IRadioisotopeFuel fuel, ItemStack stack)
	{
		return VersatileConfig.scaleRTGPower() ? IRadioisotopeFuel.getScaledPower(fuel, stack) : fuel.getHeat();
	}
	
	/**
	 * Update all of the RTG pellets in a section of a machine
	 * @param inventory - Total inventory
	 * @param rtgIn - Slot numbers for the RTG section
	 * @return The total heat level
	 */
	default int updateRTGs(ItemStack[] inventory, int[] rtgIn) {
		
		int newHeat = 0;
		for(int slot : rtgIn) {
			
			if(inventory[slot] == null)
				continue;
			
			if(!isItemValid(inventory[slot].getItem()))
				continue;
			
			final IRadioisotopeFuel pellet = (IRadioisotopeFuel) inventory[slot].getItem();
			newHeat += getPower(pellet, inventory[slot]);
			inventory[slot] = IRadioisotopeFuel.handleDecay(inventory[slot], pellet);
		}
		
		return newHeat;
	}
	/**
	 * Update all of the RTG pellets in the entire machine's inventory
	 * @param inventory - Total inventory
	 * @return The total heat level
	 */
	default int updateRTGs(ItemStack[] inventory)
	{
		int newHeat = 0;
		for (int slot = 0; slot < inventory.length; slot++)
		{
			if (inventory[slot] == null)
				continue;
			if (!isItemValid(inventory[slot].getItem()))
				continue;
			final IRadioisotopeFuel pellet = (IRadioisotopeFuel) inventory[slot].getItem();
			newHeat += getPower(pellet, inventory[slot]);
			inventory[slot] = IRadioisotopeFuel.handleDecay(inventory[slot], pellet);
		}
		return newHeat;
	}
	/**
	 * Update all of the RTG pellets in a list of a machine
	 * @param rtgList - The list of RTGs the machine uses
	 * @return The total heat level
	 */
	@Untested
	@Beta
	default int updateRTGs(ArrayList<ItemStack> rtgList)
	{
		int newHeat = 0;
		
		for (ItemStack pellet : rtgList)
		{
			final IRadioisotopeFuel fuel = (IRadioisotopeFuel) pellet.getItem();
			newHeat += getPower(fuel, pellet);
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
	 * @return The total heat level
	 */
	@Untested
	@Beta
	default int updateRTGs(ArrayList<ItemStack> rtgList, ArrayList<ItemStack> deplList)
	{
		int newHeat = 0;
		
		for (ItemStack pellet : rtgList)
		{
			final IRadioisotopeFuel fuel = (IRadioisotopeFuel) pellet.getItem();
			newHeat += getPower(fuel, pellet);
			if (fuel.getDoesDecay())
			{
				if (fuel.getLifespan(pellet) <= 0)
				{
					rtgList.remove(pellet);
					boolean alreadyExists = false;
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
	@Untested
	@Beta
	default int updateRTGs(ItemStack[] inventory, HashMap<Item, IRadioisotopeFuel> fuelMap)
	{
		int newHeat = 0;
		
		for (int slot = 0; slot < inventory.length; slot++)
		{
			if (inventory[slot] == null || IRadioisotopeFuel.getInstance(inventory[slot]) == null || !fuelMap.containsKey(inventory[slot].getItem()))
				continue;
			
			final IRadioisotopeFuel fuel = IRadioisotopeFuel.getInstance(inventory[slot]);
			newHeat += getPower(fuel, inventory[slot]);
			inventory[slot] = IRadioisotopeFuel.handleDecay(inventory[slot], fuel);
		}
		
		return newHeat;
	}
}
