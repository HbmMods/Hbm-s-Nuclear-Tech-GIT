package com.hbm.tileentity.machine;

import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMold;
import com.hbm.items.machine.ItemMold.Mold;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Thank god we have a base class now. Now with documentation and as little redundant crap in the child classes as possible.
 * @author hbm
 *
 */
public abstract class TileEntityFoundryCastingBase extends TileEntityFoundryBase implements ISidedInventory {
	public ItemStack[] slots;
	public TileEntityFoundryCastingBase(int slotCount) {
		slots = new ItemStack[slotCount];
	}
	public int cooloff = 100;

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			
			if(this.amount > this.getCapacity()) {
				this.amount = this.getCapacity();
			}
			
			if(this.amount == 0) {
				this.type = null;
			}
			
			Mold mold = this.getInstalledMold();
			
			if(mold != null && this.amount == this.getCapacity() && slots[1] == null) {
				cooloff--;
				
				if(cooloff <= 0) {
					this.amount = 0;
					
					ItemStack out = mold.getOutput(type);
					
					if(out != null) {
						slots[1] = out.copy();
					}
					
					cooloff = 200;
					this.markDirty();
				}
				
			} else {
				cooloff = 200;
			}
		}
	}

	@Override
	protected boolean shouldClientReRender() {
		return false;
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
	
	/**
	 * Standard check for testing if this material stack can be added to the casting block. Checks:<br>
	 * - type matching<br>
	 * - amount being at max<br>
	 * - whether a mold is installed<br>
	 * - whether the mold can accept this type
	 */
	public boolean standardCheck(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		if(!super.standardCheck(world, x, y, z, side, stack)) return false; //reject if base conditions are not met
		if(this.slots[1] != null) return false; //reject if a freshly casted item is still present
		Mold mold = this.getInstalledMold();
		if(mold == null) return false;
		
		return mold.getOutput(stack.material) != null; //no OD match -> no pouring
	}
	
	/** Returns an integer determining the mold size, 0 for small molds and 1 for the basin */
	public abstract int getMoldSize();

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
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public String getInventoryName() {
		return "ntmFoundry";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override public boolean hasCustomInventoryName() { return false; }
	@Override public boolean isUseableByPlayer(EntityPlayer player) { return false; }
	@Override public boolean isItemValidForSlot(int i, ItemStack stack) { return false; }

	@Override public void openInventory() { }
	@Override public void closeInventory() { }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		NBTTagList list = nbt.getTagList("items", 10);
		slots = new ItemStack[getSizeInventory()];

		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 1 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 1;
	}
}
