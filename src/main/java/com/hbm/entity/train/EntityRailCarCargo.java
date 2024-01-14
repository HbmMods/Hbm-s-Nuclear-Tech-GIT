package com.hbm.entity.train;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public abstract class EntityRailCarCargo extends EntityRailCarBase implements IInventory {

	protected String entityName;
	protected ItemStack[] slots = new ItemStack[this.getSizeInventory()];

	public EntityRailCarCargo(World world) {
		super(world);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(10, new Integer(0));
	}
	
	public int countOccupiedSlots() {
		int slots = 0;
		
		for(int i = 0; i < this.getSizeInventory(); i++) {
			if(this.getStackInSlot(i) != null) slots++;
		}
		
		return slots;
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
	public void onUpdate() {
		super.onUpdate();
		if(!this.worldObj.isRemote) this.dataWatcher.updateObject(10, this.countOccupiedSlots());
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
		
		this.dataWatcher.updateObject(10, this.countOccupiedSlots());
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return this.entityName != null;
	}

	public String getEntityName() {
		return this.entityName;
	}
	
	public void setEntityName(String name) {
		this.entityName = name;
	}

	@Override
	public String getCommandSenderName() {
		return this.entityName != null ? this.entityName : super.getCommandSenderName();
	}
}
