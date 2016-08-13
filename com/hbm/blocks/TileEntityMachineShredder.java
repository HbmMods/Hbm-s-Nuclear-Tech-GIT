package com.hbm.blocks;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.gui.MachineRecipes;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.ItemBattery;
import com.hbm.items.ItemBlades;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineShredder extends TileEntity implements ISidedInventory, IConsumer {

	private ItemStack slots[];

	public int power;
	public int progress;
	public int soundCycle = 0;
	public static final int maxPower = 10000;
	public static final int processingSpeed = 60;
	
	private static final int[] slots_top = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
	private static final int[] slots_bottom = new int[] {9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29};
	private static final int[] slots_side = new int[] {27, 28, 29};
	
	private String customName;
	
	public TileEntityMachineShredder() {
		slots = new ItemStack[30];
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
	public ItemStack getStackInSlotOnClosing(int i) {
		if(slots[i] != null)
		{
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
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.machineShredder";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=64;
		}
	}
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i == 0)
				return true;
		if(i == 2)
			if(stack.getItem() instanceof ItemBattery || stack.getItem() instanceof ItemBlades)
				return true;
		
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(slots[i] != null)
		{
			if(slots[i].stackSize <= j)
			{
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0)
			{
				slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		this.power = nbt.getShort("powerTime");
		slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length)
			{
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("powerTime", (short) power);
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < slots.length; i++)
		{
			if(slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		if(i >= 9 && i <= 28)
				return true;
		if(i >= 27 && i <= 29)
			if(itemStack.getItemDamage() == itemStack.getMaxDamage())
				return true;
		
		return false;
	}
	
	public int getDiFurnaceProgressScaled(int i) {
		return (progress * i) / processingSpeed;
	}
	
	public boolean hasPower() {
		return power > 0;
	}
	
	public boolean isProcessing() {
		return this.progress > 0;
	}
	
	@Override
	public void updateEntity() {
		boolean flag = this.hasPower();
		boolean flag1 = false;
		
		if(!worldObj.isRemote)
		{			
			if(hasPower() && canProcess())
			{
				progress++;
				
				power -= 5;

				this.slots[27].setItemDamage(this.slots[27].getItemDamage() + 1);
				this.slots[28].setItemDamage(this.slots[28].getItemDamage() + 1);
				
				if(this.progress == TileEntityMachineShredder.processingSpeed)
				{
					this.progress = 0;
					this.processItem();
					flag1 = true;
				}
				if(soundCycle == 0)
		        	this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "minecart.base", 1.0F, 0.75F);
				soundCycle++;
				
				if(soundCycle >= 50)
					soundCycle = 0;
			}else{
				progress = 0;
			}
			
			boolean trigger = true;
			
			if(hasPower() && canProcess() && this.progress == 0)
			{
				trigger = false;
			}
			
			if(trigger)
            {
                flag1 = true;
            }
		}
		
		if(/*power + 100 <= maxPower && */slots[29] != null && slots[29].getItem() == ModItems.battery_creative)
		{
			power = maxPower;
		}
		
		if(power + 100 <= maxPower && slots[29] != null && slots[29].getItem() == ModItems.battery_generic && slots[29].getItemDamage() < 50)
		{
			power += 100;
			slots[29].setItemDamage(slots[29].getItemDamage() + 1);
		}
		
		if(power + 100 <= maxPower && slots[29] != null && slots[29].getItem() == ModItems.battery_advanced && slots[29].getItemDamage() < 200)
		{
			power += 100;
			slots[29].setItemDamage(slots[29].getItemDamage() + 1);
		}
		
		if(power + 100 <= maxPower && slots[29] != null && slots[29].getItem() == ModItems.battery_schrabidium && slots[29].getItemDamage() < 1000)
		{
			power += 100;
			slots[29].setItemDamage(slots[29].getItemDamage() + 1);
		}
		
		if(power + 100 <= maxPower && slots[29] != null && slots[29].getItem() == ModItems.fusion_core && slots[29].getItemDamage() < 5000)
		{
			power += 100;
			slots[29].setItemDamage(slots[29].getItemDamage() + 1);
		}
		
		if(power + 100 <= maxPower && slots[29] != null && slots[29].getItem() == ModItems.energy_core && slots[29].getItemDamage() < 5000)
		{
			power += 100;
			slots[29].setItemDamage(slots[29].getItemDamage() + 1);
		}
		
		if(flag1)
		{
			this.markDirty();
		}
	}
	
	/*public void processItem() {
		
		boolean flag = false;
		
		for(int i = 0; i < 9; i++)
		{
			ItemStack result = MachineRecipes.getResult(slots[i]);
			if(slots[i] != null && slots[i].stackSize > 0 && hasSpace(result));
				flag = true;
		}
		
		if(!flag) {
			return;
		}
		
		for(int i = 0; i < 9; i++)
		{
			ItemStack result = MachineRecipes.getResult(slots[i]);
			
			if(slots[i] != null && slots[i].stackSize > 0 && hasSpace(result)) {
				slots[i].stackSize -= 1;
				if(slots[i].stackSize < 0)
				{
					slots[i] = null;
				}
				
				boolean flag1 = false;
				
				for(int j = 9; j < 27; j++)
				{
					if(slots[j] != null && slots[j].getItem() == result.getItem() && slots[j].stackSize + result.stackSize <= result.getMaxStackSize())
					{
						slots[j].stackSize += result.stackSize;
						flag1 = true;
						break;
					}
				}
				
				if(!flag1)
				{
					for(int j = 9; j < 27; j++)
					{
						if(slots[j] == null)
						{
							slots[j] = result;
							break;
						}
					}
				}
			}
		}
	}*/
	
	public void processItem() {
		for(int i = 0; i < 9; i++)
		{
			if(slots[i] != null && hasSpace(slots[i]))
			{
				ItemStack inp = slots[i].copy();
				ItemStack outp = MachineRecipes.getShredderResult(inp);
				boolean flag = false;
				
				for (int j = 9; j < 27; j++)
				{
					if (slots[j] != null && slots[j].getItem().equals(outp.getItem()) && slots[j].stackSize + outp.stackSize <= outp.getMaxStackSize()) {
						slots[j].stackSize += outp.stackSize;
						slots[i].stackSize -= 1;
						flag = true;
						break;
					}
				}
				
				if(!flag)
					for (int j = 9; j < 27; j++)
					{
						if (slots[j] == null) {
							slots[j] = outp.copy();
							slots[i].stackSize -= 1;
							break;
						}
					}
				
				if(slots[i].stackSize <= 0)
					slots[i] = null;
			}
		}
	}
	
	public boolean canProcess() {
		if(slots[27] != null && slots[28] != null && 
				slots[27].getItem() instanceof ItemBlades && slots[28].getItem() instanceof ItemBlades && 
				slots[27].getItemDamage() < slots[27].getMaxDamage() && slots[28].getItemDamage() < slots[28].getMaxDamage())
		for(int i = 0; i < 9; i++)
		{
			if(slots[i] != null && slots[i].stackSize > 0 && hasSpace(slots[i]))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasSpace(ItemStack stack) {
		
		ItemStack result = MachineRecipes.getShredderResult(stack);
		
		if (result != null)
			for (int i = 9; i < 27; i++) {
				if (slots[i] == null) {
					return true;
				}

				if (slots[i] != null && slots[i].getItem().equals(result.getItem())
						&& slots[i].stackSize + result.stackSize <= result.getMaxStackSize()) {
					return true;
				}
			}
		
		return false;
	}

	@Override
	public void setPower(int i) {
		this.power = i;
		
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}

	@Override
	public int getPower() {
		return this.power;
	}

	@Override
	public int getMaxPower() {
		return this.maxPower;
	}
	
	public int getGearLeft() {
		
		if(slots[27] != null && slots[27].getItem() instanceof ItemBlades)
		{
			if(slots[27].getItemDamage() < slots[27].getItem().getMaxDamage()/2)
			{
				return 1;
			} else if(slots[27].getItemDamage() != slots[27].getItem().getMaxDamage()) {
				return 2;
			} else {
				return 3;
			}
		}
		
		return 0;
	}
	
	public int getGearRight() {
		
		if(slots[28] != null && slots[28].getItem() instanceof ItemBlades)
		{
			if(slots[28].getItemDamage() < slots[28].getItem().getMaxDamage()/2)
			{
				return 1;
			} else if(slots[28].getItemDamage() != slots[28].getItem().getMaxDamage()) {
				return 2;
			} else {
				return 3;
			}
		}
		
		return 0;
	}
}
