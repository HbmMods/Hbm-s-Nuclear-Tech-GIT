package com.hbm.entity.cart;

import com.hbm.items.tool.ItemModMinecart.EnumCartBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

/**
 * Only exists because some absolute genius at mojang thought it'd be funny to have the base class' stack array be fixed at 36.
 * Yes, 36. No more, no less. Hopper carts use it too, it's just that IInventory caps the accessible slots at 5. Amazing, isn't it?
 * @author hbm
 *
 */
public abstract class EntityMinecartContainerBase extends EntityMinecartNTM implements IInventory {
	
	protected ItemStack[] slots = new ItemStack[this.getSizeInventory()];

	public EntityMinecartContainerBase(World world) {
		super(world);
	}

	public EntityMinecartContainerBase(World world, double x, double y, double z, EnumCartBase type) {
		super(world, x, y, z, type);
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slots[slot];
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(this.slots[slot] != null) {
			ItemStack itemstack;

			if(this.slots[slot].stackSize <= amount) {
				itemstack = this.slots[slot];
				this.slots[slot] = null;
				return itemstack;
			} else {
				itemstack = this.slots[slot].splitStack(amount);

				if(this.slots[slot].stackSize == 0) {
					this.slots[slot] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(this.slots[slot] != null) {
			ItemStack itemstack = this.slots[slot];
			this.slots[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.slots[slot] = stack;

		if(stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.func_95999_t() : "container.minecart";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() { }

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.isDead ? false : player.getDistanceSqToEntity(this) <= 64.0D;
	}

	@Override
	public void openInventory() { }

	@Override
	public void closeInventory() { }

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		NBTTagList nbttaglist = new NBTTagList();

		for(int i = 0; i < this.slots.length; ++i) {
			if(this.slots[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.slots[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("Items", nbttaglist);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		this.slots = new ItemStack[this.getSizeInventory()];

		for(int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if(j >= 0 && j < this.slots.length) {
				this.slots[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}
}
