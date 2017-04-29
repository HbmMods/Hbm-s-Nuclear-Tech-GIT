package com.hbm.tileentity;

import com.hbm.interfaces.IConsumer;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineCMBFactory extends TileEntity implements ISidedInventory, IConsumer {

	private ItemStack slots[];
	
	public int power = 0;
	public int waste = 0;
	public int process = 0;
	public int soundCycle = 0;
	public static final int maxFill = 1000;
	public static final int maxPower = 100000;
	public static final int processSpeed = 200;

	private static final int[] slots_top = new int[] {1, 3};
	private static final int[] slots_bottom = new int[] {0, 2, 4};
	private static final int[] slots_side = new int[] {0, 2};
	
	private String customName;
	
	public TileEntityMachineCMBFactory() {
		slots = new ItemStack[5];
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
		return this.hasCustomInventoryName() ? this.customName : "container.machineCMB";
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
	
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		switch(i)
		{
		case 0:
			if(stack.getItem() instanceof ItemBattery)
				return true;
			break;
		case 1:
			if(stack.getItem() == ModItems.ingot_magnetized_tungsten || stack.getItem() == ModItems.powder_magnetized_tungsten)
				return true;
			break;
		case 2:
			if(stack.getItem() == ModItems.bucket_mud || (stack.getItem() == ModItems.tank_waste && stack.getItemDamage() > 0))
				return true;
			break;
		case 3:
			if(stack.getItem() == ModItems.ingot_advanced_alloy || stack.getItem() == ModItems.powder_advanced_alloy)
				return true;
			break;
		}
		
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
		
		power = nbt.getInteger("power");
		waste = nbt.getInteger("waste");
		process = nbt.getShort("process");
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
		nbt.setInteger("power", power);
		nbt.setInteger("waste", waste);
		nbt.setShort("process", (short) process);
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
		if(i == 4)
			return true;
		if(i == 0 && itemStack.getItem() instanceof ItemBattery)
			if(itemStack.getItemDamage() == itemStack.getMaxDamage())
				return true;
		if(i == 2)
			if(itemStack.getItem() == Items.bucket || (itemStack.getItem() == ModItems.tank_waste && itemStack.getItemDamage() <= 0))
				return true;
		
		return false;
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public int getWasteScaled(int i) {
		return (waste * i) / maxFill;
	}
	
	public int getProgressScaled(int i) {
		return (process * i) / processSpeed;
	}
	
	public boolean canProcess() {
		
		boolean b = false;
		
		if(waste > 0 && power > 0 && slots[1] != null && slots[3] != null && (slots[4] == null || slots[4].stackSize <= 60))
		{
			boolean flag0 = slots[1].getItem() == ModItems.ingot_magnetized_tungsten || slots[1].getItem() == ModItems.powder_magnetized_tungsten;
			boolean flag1 = slots[3].getItem() == ModItems.ingot_advanced_alloy || slots[3].getItem() == ModItems.powder_advanced_alloy;
			
			b = flag0 && flag1;
		}
		
		return  b;
	}
	
	public boolean isProcessing() {
		return process > 0;
	}
	
	public void process() {
		waste -= 1;
		power -= 15;
		
		process++;
		
		if(process >= processSpeed) {
			
			slots[1].stackSize--;
			if (slots[1].stackSize == 0) {
				slots[1] = null;
			}

			slots[3].stackSize--;
			if (slots[3].stackSize == 0) {
				slots[3] = null;
			}
			
			if(slots[4] == null)
			{
				slots[4] = new ItemStack(ModItems.ingot_combine_steel, 4);
			} else {
				
				slots[4].stackSize += 4;
			}
			
			process = 0;
		}
	}
	
	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {

			if (slots[0] != null && slots[0].getItem() == ModItems.battery_creative) {
				power = maxPower;
			}

			if (power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.battery_generic
					&& slots[0].getItemDamage() < 50) {
				power += 100;
				slots[0].setItemDamage(slots[0].getItemDamage() + 1);
			}

			if (power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.battery_advanced
					&& slots[0].getItemDamage() < 200) {
				power += 100;
				slots[0].setItemDamage(slots[0].getItemDamage() + 1);
			}

			if (power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.battery_schrabidium
					&& slots[0].getItemDamage() < 1000) {
				power += 100;
				slots[0].setItemDamage(slots[0].getItemDamage() + 1);
			}

			if (power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.fusion_core
					&& slots[0].getItemDamage() < 5000) {
				power += 100;
				slots[0].setItemDamage(slots[0].getItemDamage() + 1);
			}

			if (power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.energy_core
					&& slots[0].getItemDamage() < 5000) {
				power += 100;
				slots[0].setItemDamage(slots[0].getItemDamage() + 1);
			}
			
			if(waste + 500 <= maxFill && slots[2] != null && slots[2].getItem() == ModItems.bucket_mud) {
				waste += 500;
				slots[2] = new ItemStack(slots[2].getItem().getContainerItem());
			}
			
			if(waste + 500 <= maxFill && slots[2] != null && slots[2].getItem() == ModItems.tank_waste && slots[2].getItemDamage() > 0) {
				waste += 500;
				slots[2].setItemDamage(slots[2].getItemDamage() - 1);
			}

			if (canProcess()) {
				process();
				if(soundCycle == 0)
			        this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "minecart.base", 1.0F, 1.5F);
				soundCycle++;
					
				if(soundCycle >= 25)
					soundCycle = 0;
			} else {
				process = 0;
			}
		}
	}

	@Override
	public void setPower(int i) {
		power = i;
		
	}

	@Override
	public int getPower() {
		return power;
		
	}

	@Override
	public int getMaxPower() {
		return maxPower;
	}
}
