package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMold;
import com.hbm.items.machine.ItemMold.Mold;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Thank god we have a base class now. Now with documentation and as little redundtand crap in the child classes as possible.
 * @author hbm
 *
 */
public abstract class TileEntityFoundryCastingBase extends TileEntityFoundryBase implements IInventory {

	public ItemStack slots[] = new ItemStack[2];
	public int cooloff = 100;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			Mold mold = this.getInstalledMold();
			
			if(mold != null && this.amount == this.getCapacity() && slots[1] == null) {
				cooloff--;
				
				if(cooloff <= 0) {
					this.amount = 0;
					
					for(String name : this.type.names) {
						String od = mold.shape.name().toLowerCase() + name;
						List<ItemStack> ores = OreDictionary.getOres(od);
						
						if(!ores.isEmpty()) {
							slots[1] = ores.get(0).copy();
							slots[1].stackSize = mold.amount;
						}
					}
					
					cooloff = 100;
				}
				
			} else {
				cooloff = 100;
			}
		}
	}
	
	/** Checks slot 0 to see what mold type is installed. Returns null if no mold is found or an incorrect size was used. */
	public Mold getInstalledMold() {
		if(slots[0] == null) return null;
		
		if(slots[0].getItem() == ModItems.mold) {
			Mold mold = ((ItemMold) slots[0].getItem()).getMold(slots[0]);
			
			if(mold.size == this.getMoldSize())
				return mold;
		}
		
		return null;
	}

	/** Returns the amount of quanta this casting block can hold, depending on the installed mold or 0 if no mold is found. */
	@Override
	public int getCapacity() {
		Mold mold = this.getInstalledMold();
		return mold == null ? 0 : mold.getCost();
	}

	/** Standard check with no additional limitations added */
	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return this.standardCheck(world, x, y, z, side, stack);
	}
	
	/** Standard flow, no special handling required */
	@Override
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		return standardAdd(world, x, y, z, side, stack);
	}

	/** Standard check, but with the additional limitation that the only valid source direction is UP */
	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		if(side != ForgeDirection.UP) return false;
		return this.standardCheck(world, x, y, z, side, stack);
	}

	/** Standard flow, no special handling required */
	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		return standardAdd(world, x, y, z, side, stack);
	}
	
	/**
	 * Standard check for testing if this material stack can be added to the casting block. Checks:<br>
	 * - type matching<br>
	 * - amount being at max<br>
	 * - whether a mold is installed<br>
	 * - whether the mold can accept this type
	 */
	public boolean standardCheck(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		if(this.type != null && this.type != stack.material) return false; //reject if there's already a different material
		if(this.amount >= this.getCapacity()) return false; //reject if the buffer is already full
		if(this.slots[1] != null) return false; //reject if a freshly casted item is still present
		Mold mold = this.getInstalledMold();
		if(mold == null) return false;
		
		for(String name : stack.material.names) {
			String od = mold.shape.name().toLowerCase() + name;
			
			if(!OreDictionary.getOres(od).isEmpty()) {
				return true; //at least one block for this material? return TRUE
			}
		}
		
		return false; //no OD match -> no pouring
	}
	
	/**
	 * Standardized adding of material via pouring or flowing. Does:<br>
	 * - sets material to match the input
	 * - adds the amount, not exceeding the maximum
	 * - returns the amount that cannot be added
	 */
	public MaterialStack standardAdd(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		
		if(this.type == null) {
			this.type = stack.material;
		}
		
		if(stack.amount + this.amount <= this.getCapacity()) {
			this.amount += stack.amount;
			return null;
		}
		
		int required = this.getCapacity() - this.amount;
		this.amount = this.getCapacity();
		
		stack.amount -= required;
		
		return stack;
	}
	
	/** Returns an integer determining the mold size, 0 for small molds and 1 for the basin */
	public abstract int getMoldSize();

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(slots[i] != null) {
			ItemStack itemStack = slots[i];
			slots[i] = null;
			return itemStack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(slots[slot] != null) {
			if(slots[slot].stackSize <= amount) {
				ItemStack itemStack = slots[slot];
				slots[slot] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[slot].splitStack(amount);
			if(slots[slot].stackSize == 0) {
				slots[slot] = null;
			}
			return itemStack1;
		} else {
			return null;
		}
	}

	@Override
	public String getInventoryName() {
		return "ntmFoundry";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}

	@Override public void openInventory() { }
	@Override public void closeInventory() { }
}
