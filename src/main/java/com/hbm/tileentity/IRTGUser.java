package com.hbm.tileentity;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.annotations.Beta;
import com.hbm.config.VersatileConfig;
import com.hbm.interfaces.Untested;
import com.hbm.inventory.RecipesCommon.AStack;

import api.hbm.Date;
import api.hbm.IHasAge;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
/**
 * For machines that use RTG pellets
 * @author UFFR
 *
 */
public interface IRTGUser extends IHasAge
{
	/** Get the heat provided by the RTGs **/
	public int getHeat();
	public int[] getSlots();
	public ItemStack[] getInventory();
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
//	default int updateRTGs(ItemStack[] inventory, int[] rtgIn) {
//		
//		int newHeat = 0;
//		for (int slot : rtgIn)
//		{
//			if(inventory[slot] == null)
//				continue;
//			
//			if(!isItemValid(inventory[slot].getItem()))
//				continue;
//			
//			final IRadioisotopeFuel pellet = (IRadioisotopeFuel) inventory[slot].getItem();
//			newHeat += getPower(pellet, inventory[slot]);
//			inventory[slot] = IRadioisotopeFuel.handleDecay(inventory[slot], pellet);
//		}
//		
//		return newHeat * 10;
//	}
	/**
	 * Update all of the RTG pellets in the entire machine's inventory
	 * @return The total heat level
	 */
	default int updateRTGs()
	{
		int newHeat = 0;
		for (int slot : getSlots())
		{
			if (getInventory()[slot] == null)
				continue;
			if (!isItemValid(getInventory()[slot].getItem()))
				continue;
			final IRadioisotopeFuel pellet = (IRadioisotopeFuel) getInventory()[slot].getItem();
			newHeat += getPower(pellet, getInventory()[slot]);
			getInventory()[slot] = IRadioisotopeFuel.handleDecay(getInventory()[slot], pellet);
		}
		return newHeat * 10;
	}
	/**
	 * Update all of the RTG pellets in a list of a machine
	 * @param rtgList - The list of RTGs the machine uses
	 * @return The total heat level
	 */
//	@Untested
//	@Beta
//	default int updateRTGs(ArrayList<ItemStack> rtgList)
//	{
//		int newHeat = 0;
//		
//		for (ItemStack pellet : rtgList)
//		{
//			final IRadioisotopeFuel fuel = (IRadioisotopeFuel) pellet.getItem();
//			newHeat += getPower(fuel, pellet);
//			if (fuel.getDoesDecay())
//			{
//				if (fuel.getLifespan(pellet) <= 0)
//					rtgList.remove(pellet);
//				else
//					fuel.decay(pellet);
//			}
//		}
//		
//		return newHeat * 10;
//	}
	/**
	 * Update all of the RTG pellets in a list of a machine and allows for decay items to accumulate
	 * @param rtgList - The list of RTGs the machine uses
	 * @param deplList - The list of depleted items
	 * @return The total heat level
	 */
//	@Untested
//	@Beta
//	default int updateRTGs(ArrayList<ItemStack> rtgList, ArrayList<ItemStack> deplList)
//	{
//		int newHeat = 0;
//		
//		for (ItemStack pellet : rtgList)
//		{
//			final IRadioisotopeFuel fuel = (IRadioisotopeFuel) pellet.getItem();
//			newHeat += getPower(fuel, pellet);
//			if (fuel.getDoesDecay())
//			{
//				if (fuel.getLifespan(pellet) <= 0)
//				{
//					rtgList.remove(pellet);
//					boolean alreadyExists = false;
//					final ItemStack decayItem = fuel.getDecayItem();
//					for (int i = 0; i < deplList.size(); i++)
//					{
//						if (deplList.get(i).getItem() == decayItem.getItem() && deplList.get(i).getItemDamage() == decayItem.getItemDamage() && deplList.get(i).stackSize + decayItem.stackSize <= decayItem.getMaxStackSize())
//							alreadyExists = true;
//						else if (deplList.get(i).stackSize + decayItem.stackSize > decayItem.getMaxStackSize())
//							continue;
//						
//						if (alreadyExists)
//							break;
//					}
//					if (alreadyExists)
//					{
//						for (int i = 0; i < deplList.size(); i++)
//							if (deplList.get(i).getItem() == decayItem.getItem() && deplList.get(i).getItemDamage() == decayItem.getItemDamage() && deplList.get(i).stackSize + decayItem.stackSize <= decayItem.getMaxStackSize())
//								deplList.get(i).stackSize += decayItem.stackSize;
//					}
//					else
//						deplList.add(fuel.getDecayItem());
//				}
//			}
//		}
//		
//		return newHeat * 10;
//	}
	@Untested
	@Beta
	default int updateRTGs(Map<AStack, IRadioisotopeFuel> fuelMap)
	{
		int newHeat = 0;
		
		for (int slot : getSlots())
		{
			boolean flag = false;
			for (Entry<AStack, IRadioisotopeFuel> fuelEntry : fuelMap.entrySet())
				if (fuelEntry.getKey().isApplicable(getInventory()[slot]))
					flag = true;
			
			if (!flag)
				continue;
			
			final IRadioisotopeFuel fuel = IRadioisotopeFuel.getInstance(getInventory()[slot]);
			newHeat += getPower(fuel, getInventory()[slot]);
			getInventory()[slot] = IRadioisotopeFuel.handleDecay(getInventory()[slot], fuel);
		}
		
		return newHeat * 10;
	}
	
	@Override
	default void notifyTimeUpdated(Date newTime)
	{
		final long difference = getInternalDate().subtract(newTime).getData().longValue();
		
		for (int slot : getSlots())
		{
			if (getInventory()[slot] == null)
				continue;
			if (!isItemValid(getInventory()[slot].getItem()))
				continue;
			final ItemStack stack = getInventory()[slot];
			final IRadioisotopeFuel pellet = (IRadioisotopeFuel) stack.getItem();
			pellet.setLifespan(stack, pellet.getLifespan(stack) - difference);
		}
	}
}
