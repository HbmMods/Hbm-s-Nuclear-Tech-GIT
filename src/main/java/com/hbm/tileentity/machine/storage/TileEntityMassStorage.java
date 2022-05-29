package com.hbm.tileentity.machine.storage;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.items.ModItems;
import com.hbm.tileentity.INBTPacketReceiver;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class TileEntityMassStorage extends TileEntityCrateBase implements INBTPacketReceiver, IControlReceiver {
	
	private int stack = 0;
	public boolean output = false;
	
	public TileEntityMassStorage() {
		super(3);
	}

	@Override
	public String getInventoryName() {
		return "container.massStorage";
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(slots[0] != null && slots[0].getItem() == ModItems.fluid_barrel_infinite) {
				this.stack = this.getCapacity();
			}
			
			if(this.getType() == null)
				this.stack = 0;
			
			if(getType() != null && getStockpile() < getCapacity() && slots[0] != null && slots[0].isItemEqual(getType()) && ItemStack.areItemStackTagsEqual(slots[0], getType())) {
				
				int remaining = getCapacity() - getStockpile();
				int toRemove = Math.min(remaining, slots[0].stackSize);
				this.decrStackSize(0, toRemove);
				this.stack += toRemove;
				this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
			}
			
			if(output) {
				
				if(slots[2] != null && !(slots[2].isItemEqual(getType()) && ItemStack.areItemStackTagsEqual(slots[2], getType()))) {
					return;
				}
				
				int amount = Math.min(getStockpile(), getType().getMaxStackSize());
				
				if(slots[2] == null) {
					slots[2] = slots[1].copy();
					slots[2].stackSize = amount;
					this.stack -= amount;
				} else {
					amount = Math.min(amount, slots[2].getMaxStackSize() - slots[2].stackSize);
					slots[2].stackSize += amount;
					this.stack -= amount;
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("stack", getStockpile());
			data.setBoolean("output", output);
			INBTPacketReceiver.networkPack(this, data, 15);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.stack = nbt.getInteger("stack");
		this.output = nbt.getBoolean("output");
	}
	
	public int getCapacity() {
		return 10_000;
	}
	
	public ItemStack getType() {
		return slots[1] == null ? null : slots[1].copy();
	}
	
	public int getStockpile() {
		return stack;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.stack = nbt.getInteger("stack");
		this.output = nbt.getBoolean("output");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("stack", stack);
		nbt.setBoolean("output", output);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("provide") && slots[1] != null) {
			
			if(this.getStockpile() == 0) {
				return;
			}
			
			int amount = data.getBoolean("provide") ? slots[1].getMaxStackSize() : 1;
			amount = Math.min(amount, getStockpile());
			
			if(slots[2] != null && !(slots[2].isItemEqual(getType()) && ItemStack.areItemStackTagsEqual(slots[2], getType()))) {
				return;
			}
			
			if(slots[2] == null) {
				slots[2] = slots[1].copy();
				slots[2].stackSize = amount;
				this.stack -= amount;
			} else {
				amount = Math.min(amount, slots[2].getMaxStackSize() - slots[2].stackSize);
				slots[2].stackSize += amount;
				this.stack -= amount;
			}
		}
		
		if(data.hasKey("toggle")) {
			this.output = !output;
		}
	}
}
