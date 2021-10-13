package com.hbm.tileentity.machine;

<<<<<<< HEAD
import com.hbm.inventory.BreederRecipes;
import com.hbm.inventory.BreederRecipes.BreederRecipe;
=======
import com.hbm.inventory.recipes.BreederRecipes;
import com.hbm.inventory.recipes.BreederRecipes.BreederRecipe;
>>>>>>> master
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineReactor extends TileEntityMachineBase {

	public int progress;
	public int charge;
	public int heat;
	public static final int maxPower = 1000;
	public static final int processingSpeed = 1000;

	private static final int[] slots_top = new int[] { 1 };
	private static final int[] slots_bottom = new int[] { 2, 0 };
	private static final int[] slots_side = new int[] { 0 };

	public TileEntityMachineReactor() {
		super(3);
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public String getName() {
		return "container.reactor";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			boolean markDirty = false;
			
			if(charge == 0) {
				heat = 0;
			}
			
			if(hasItemPower(slots[0]) && charge == 0) {
				
				charge += getItemPower(slots[0]);
				heat = getItemHeat(slots[0]);
				
				if(slots[0] != null) {
					
					slots[0].stackSize--;
					
					if(slots[0].stackSize == 0) {
						slots[0] = slots[0].getItem().getContainerItem(slots[0]);
					}
					
					markDirty = true;
				}
			}

			if(hasPower() && canProcess()) {
				
				progress++;

				if(this.progress == TileEntityMachineReactor.processingSpeed) {
					this.progress = 0;
					this.charge--;
					this.processItem();
					markDirty = true;
				}
			} else {
				progress = 0;
			}

			boolean trigger = true;

			if(hasPower() && canProcess() && this.progress == 0)
				trigger = false;

			if(trigger) {
				markDirty = true;
			}

			if(markDirty)
				this.markDirty();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("charge", (short)charge);
			data.setShort("progress", (short)progress);
			data.setByte("heat", (byte)heat);
			this.networkPack(data, 20);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {

		charge = data.getShort("charge");
		progress = data.getShort("progress");
		heat = data.getByte("heat");
	}

	public boolean canProcess() {
		
		if(slots[1] == null) {
			return false;
		}
		
		BreederRecipe recipe = BreederRecipes.getOutput(slots[1]);
		
		if(recipe == null)
			return false;
		
		if(this.heat < recipe.heat)
			return false;

		if(slots[2] == null)
			return true;

		if(!slots[2].isItemEqual(recipe.output))
			return false;

		if(slots[2].stackSize < getInventoryStackLimit() && slots[2].stackSize < slots[2].getMaxStackSize())
			return true;
		else
			return slots[2].stackSize < recipe.output.getMaxStackSize();
	}

	private void processItem() {
		
		if(canProcess()) {
			
			BreederRecipe rec = BreederRecipes.getOutput(slots[1]);
			
			if(rec == null)
				return;
			
			ItemStack itemStack = rec.output;

			if(slots[2] == null) {
				slots[2] = itemStack.copy();
			} else if(slots[2].isItemEqual(itemStack)) {
				slots[2].stackSize += itemStack.stackSize;
			}

			for(int i = 1; i < 2; i++) {
				if(slots[i].stackSize <= 0) {
					slots[i] = new ItemStack(slots[i].getItem().setFull3D());
				} else {
					slots[i].stackSize--;
				}
				if(slots[i].stackSize <= 0) {
					slots[i] = null;
				}
			}
		}
	}

	public boolean hasItemPower(ItemStack stack) {
		return BreederRecipes.getFuelValue(stack) != null;
	}

	private static int getItemPower(ItemStack stack) {
		
		int[] power = BreederRecipes.getFuelValue(stack);
		
		if(power == null)
			return 0;
		
		return power[1];
	}

	private static int getItemHeat(ItemStack stack) {
		
		int[] power = BreederRecipes.getFuelValue(stack);
		
		if(power == null)
			return 0;
		
		return power[0];
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slots_bottom : (side == 1 ? slots_top : slots_side);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 2 ? false : (i == 0 ? hasItemPower(itemStack) : true);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		
		if(i == 0) {
			if(!hasItemPower(slots[0])) {
				return true;
			}
			
			return false;
		}

		return true;
	}

	public int getProgressScaled(int i) {
		return (progress * i) / processingSpeed;
	}

	public int getHeatScaled(int i) {
		return (heat * i) / 4;
	}

	public boolean hasPower() {
		return charge > 0;
	}

	public boolean isProcessing() {
		return this.progress > 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		charge = nbt.getShort("charge");
		heat = nbt.getShort("heat");
		progress = nbt.getShort("progress");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setShort("charge", (short) charge);
		nbt.setShort("heat", (short) heat);
		nbt.setShort("progress", (short) progress);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord,
					yCoord,
					zCoord,
					xCoord + 1,
					yCoord + 3,
					zCoord + 1
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}