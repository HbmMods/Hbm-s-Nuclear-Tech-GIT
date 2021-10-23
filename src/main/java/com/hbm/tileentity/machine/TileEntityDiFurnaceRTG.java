package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.MachineDiFurnaceRTG;
import com.hbm.interfaces.IRTGUser;
import com.hbm.interfaces.IRadioisotopeFuel;
import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityDiFurnaceRTG extends TileEntityMachineBase implements IRTGUser
{
	public short progress;
	private short processSpeed = 0;
	// Edit as needed
	private static final short timeRequired = 1200;
	private static final int[] rtgIn = new int[] {3, 4, 5, 6, 7, 8};
	private String name;
	
	public TileEntityDiFurnaceRTG()
	{
		super(9);
	}

	public boolean canProcess()
	{
		if ((slots[0] == null || slots[1] == null) && !hasPower())
			return false;
		
		ItemStack recipeResult = MachineRecipes.getFurnaceProcessingResult(slots[0], slots[1]);
		if (recipeResult == null)
			return false;
		else if (slots[2] == null)
			return true;
		else if (!slots[2].isItemEqual(recipeResult))
			return false;
		else if (slots[2].stackSize + recipeResult.stackSize > getInventoryStackLimit())
			return false;
		else if (slots[2].stackSize < getInventoryStackLimit() && slots[2].stackSize < slots[2].getMaxStackSize())
			return true;
		else
			return slots[2].stackSize < recipeResult.getMaxStackSize();
	}
	
	@Override
	public void updateEntity()
	{
		if (canProcess() && hasPower())
		{
			progress += processSpeed;
			if (progress >= timeRequired)
			{
				processItem();
				progress = 0;
			}
		}
		else
			progress = 0;
		MachineDiFurnaceRTG.updateBlockState(isProcessing() || canProcess(), getWorldObj(), xCoord, yCoord, zCoord);
		
		NBTTagCompound data = new NBTTagCompound();
		data.setShort("progress", progress);
		data.setShort("speed", processSpeed);
		networkPack(data, 10);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt)
	{
		progress = nbt.getShort("progress");
		processSpeed = nbt.getShort("speed");
	}
	
	private void processItem()
	{
		if (canProcess())
		{
			ItemStack recipeOut = MachineRecipes.getFurnaceProcessingResult(slots[0], slots[1]);
			if (slots[2] == null)
				slots[2] = recipeOut.copy();
			else if (slots[2].isItemEqual(recipeOut))
				slots[2].stackSize += recipeOut.stackSize;
			
			for (int i = 0; i < 2; i++)
			{
				if (slots[i].stackSize <= 0)
					slots[i] = new ItemStack(slots[i].getItem().setFull3D());
				else
					slots[i].stackSize--;
				if (slots[i].stackSize <= 0)
					slots[i] = null;
			}
			markDirty();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		progress = nbt.getShort("progress");
		processSpeed = nbt.getShort("speed");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setShort("progress", progress);
		nbt.setShort("speed", processSpeed);
	}
	
	public int getDiFurnaceProgressScaled(int i)
	{
		return (progress * i) / timeRequired;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack)
	{
		slots[i] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit())
		{
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public void setCustomName(String name)
	{
		this.name = name;
	}
	
	public boolean hasPower()
	{
//		updateRTGs();
		processSpeed = (short) updateRTGs(slots, rtgIn);
		return processSpeed >= 15;
	}
	
	public int getPower()
	{
		return processSpeed;
	}
	
	public boolean isProcessing()
	{
		return progress > 0;
	}
	
	@Override
	public String getInventoryName()
	{
		return this.hasCustomInventoryName() ? this.name : "container.diFurnaceRTG";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return this.name != null && this.name.length() > 0;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack)
	{
		if (i == 2)
		{
			return false;
		}
		return true;
	}

	@Override
	public String getName()
	{
		return "container.diFurnaceRTG";
	}

	@Override
	public int getHeat()
	{
		return processSpeed;
	}

	@Override
	public Class<? extends IRadioisotopeFuel> getDesiredClass()
	{
		return ItemRTGPellet.class;
	}

}
