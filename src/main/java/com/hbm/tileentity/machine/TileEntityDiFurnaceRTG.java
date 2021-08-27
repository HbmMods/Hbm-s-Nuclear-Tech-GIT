package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.MachineDiFurnaceRTG;
import com.hbm.interfaces.IRTGUser;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityDiFurnaceRTG extends TileEntityMachineBase implements IRTGUser
{
	public int progress;
	private int processSpeed = 0;
	private static int timeRequired = 800;
	private static final int[] rtgIn = new int[] {3, 4, 5, 6, 7, 8};
	private String name;
	
	public TileEntityDiFurnaceRTG()
	{
		super(9);
	}

	public boolean canProcess()
	{
		if (slots[0] == null || slots[1] == null)
		{
			return false;
		}
		if (!hasPower())
		{
			return false;
		}
		ItemStack recipeResult = MachineRecipes.getFurnaceProcessingResult(slots[0], slots[1]);
		if (recipeResult == null)
		{
			return false;
		}
		if (slots[2] == null)
		{
			return true;
		}
		if (!slots[2].isItemEqual(recipeResult))
		{
			return false;
		}
		if (slots[2].stackSize + recipeResult.stackSize > getInventoryStackLimit())
		{
			return false;
		}
		if (slots[2].stackSize < getInventoryStackLimit() && slots[2].stackSize < slots[2].getMaxStackSize())
		{
			return true;
		}
		else
		{
			return slots[2].stackSize < recipeResult.getMaxStackSize();
		}
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
		{
			progress = 0;
		}
		MachineDiFurnaceRTG.updateBlockState(isProcessing() || canProcess(), getWorldObj(), xCoord, yCoord, zCoord);
	}
	
	private void processItem()
	{
		if (canProcess())
		{
			ItemStack recipeOut = MachineRecipes.getFurnaceProcessingResult(slots[0], slots[1]);
			if (slots[2] == null)
			{
				slots[2] = recipeOut.copy();
			}
			else if (slots[2].isItemEqual(recipeOut))
			{
				slots[2].stackSize += recipeOut.stackSize;
			}
			
			for (int i = 0; i < 2; i++)
			{
				if (slots[i].stackSize <= 0)
				{
					slots[i] = new ItemStack(slots[i].getItem().setFull3D());
				}
				else
				{
					slots[i].stackSize--;
				}
				if (slots[i].stackSize <= 0)
				{
					slots[i] = null;
				}
			}
			markDirty();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		progress = nbt.getInteger("progress");
		processSpeed = nbt.getInteger("speed");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("progress", progress);
		nbt.setInteger("speed", processSpeed);
	}
	
	public int getDiFurnaceProgressScaled(int i)
	{
		return (progress * i) / timeRequired;
	}

	
	@Override
	public ItemStack decrStackSize(int i, int amount)
	{
		if (slots[i] != null)
		{
			if (slots[i].stackSize <= amount)
			{
				ItemStack stack1 = slots[i];
				slots[i] = null;
				return stack1;
			}
			ItemStack stack2 = slots[i].splitStack(amount);
			if (slots[i].stackSize == 0)
			{
				slots[i] = null;
			}
			return stack2;
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		if (slots[i] != null)
		{
			ItemStack stack = slots[i];
			slots[i] = null;
			return stack;
		}
		else
		{
			return null;
		}
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

	public void setCustomName(String name)
	{
		this.name = name;
	}
	
	@Deprecated
	private void updateRTGs()
	{
		int newSpeed = 0;
		for (int slot : rtgIn)
		{
			if (slots[slot] != null)
			{
				if (slots[slot].getItem() == ModItems.pellet_rtg)
					newSpeed += 5;
				if (slots[slot].getItem() == ModItems.pellet_rtg_weak)
					newSpeed += 1;
				if (slots[slot].getItem() == ModItems.pellet_rtg_polonium)
					newSpeed += 25;
				if (slots[slot].getItem() == ModItems.pellet_rtg_gold)
					if(worldObj.rand.nextInt(60*60*20) == 0)
						slots[slot] = new ItemStack(ModItems.nugget_mercury, 2);
					else
						newSpeed += 150;
			}
		}
		processSpeed = newSpeed;
	}
	
	public boolean hasPower()
	{
//		updateRTGs();
		processSpeed = IRTGUser.super.updateRTGs(slots, rtgIn, getWorldObj());
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
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}
		else
		{
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
		}
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

}
