package com.hbm.tileentity;

import com.hbm.blocks.machine.MachineElectricFurnace;
import com.hbm.gui.MachineRecipes;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineElectricFurnace extends TileEntity implements ISidedInventory, IConsumer {

	private ItemStack slots[];
	
	public int dualCookTime;
	public int power;
	public static final int maxPower = 10000;
	public static final int processingSpeed = 100;
	
	private static final int[] slots_top = new int[] {1};
	private static final int[] slots_bottom = new int[] {2, 0};
	private static final int[] slots_side = new int[] {0};
	
	private String customName;
	
	public TileEntityMachineElectricFurnace() {
		slots = new ItemStack[3];
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
		return this.hasCustomInventoryName() ? this.customName : "container.electricFurnace";
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
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i == 0)
			if(itemStack.getItem() instanceof ItemBattery)
				return true;
		
		if(i == 1)
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
		this.dualCookTime = nbt.getShort("cookTime");
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
		nbt.setShort("cookTime", (short) dualCookTime);
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
		if(i == 0)
			if(itemStack.getItemDamage() == itemStack.getMaxDamage())
				return true;
		if(i == 2)
			return true;
		
		return false;
	}
	
	public int getDiFurnaceProgressScaled(int i) {
		return (dualCookTime * i) / processingSpeed;
	}
	
	public int getPowerRemainingScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public boolean hasPower() {
		return power > 0;
	}
	
	public boolean isProcessing() {
		return this.dualCookTime > 0;
	}
	
	public boolean canProcess() {
		if(slots[1] == null)
		{
			return false;
		}
        ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[1]);
        //ItemStack itemStack = MachineRecipes.getShredderResult(this.slots[1]);
		if(itemStack == null)
		{
			return false;
		}
		
		if(slots[2] == null)
		{
			return true;
		}
		
		if(!slots[2].isItemEqual(itemStack)) {
			return false;
		}
		
		if(slots[2].stackSize < getInventoryStackLimit() && slots[2].stackSize < slots[2].getMaxStackSize()) {
			return true;
		}else{
			return slots[2].stackSize < itemStack.getMaxStackSize();
		}
	}
	
	private void processItem() {
		if(canProcess()) {
	        ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[1]);
	        //ItemStack itemStack = MachineRecipes.getShredderResult(this.slots[1]);
			
			if(slots[2] == null)
			{
				slots[2] = itemStack.copy();
			}else if(slots[2].isItemEqual(itemStack)) {
				slots[2].stackSize += itemStack.stackSize;
			}
			
			for(int i = 1; i < 2; i++)
			{
				if(slots[i].stackSize <= 0)
				{
					slots[i] = new ItemStack(slots[i].getItem().setFull3D());
				}else{
					slots[i].stackSize--;
				}
				if(slots[i].stackSize <= 0)
				{
					slots[i] = null;
				}
			}
		}
	}
	
	@Override
	public void updateEntity() {
		boolean flag = this.hasPower();
		boolean flag1 = false;
		
		if(!worldObj.isRemote)
		{			
			if(hasPower() && canProcess())
			{
				dualCookTime++;
				
				power -= 5;
				
				if(this.dualCookTime == TileEntityMachineElectricFurnace.processingSpeed)
				{
					this.dualCookTime = 0;
					this.processItem();
					flag1 = true;
				}
			}else{
				dualCookTime = 0;
			}
			
			boolean trigger = true;
			
			if(hasPower() && canProcess() && this.dualCookTime == 0)
			{
				trigger = false;
			}
			
			if(trigger)
            {
                flag1 = true;
                MachineElectricFurnace.updateBlockState(this.dualCookTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
		}
		
		if(/*power + 100 <= maxPower && */slots[0] != null && slots[0].getItem() == ModItems.battery_creative)
		{
			power = maxPower;
		}
		
		if(power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.battery_generic && slots[0].getItemDamage() < 50)
		{
			power += 100;
			slots[0].setItemDamage(slots[0].getItemDamage() + 1);
		}
		
		if(power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.battery_advanced && slots[0].getItemDamage() < 200)
		{
			power += 100;
			slots[0].setItemDamage(slots[0].getItemDamage() + 1);
		}
		
		if(power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.battery_schrabidium && slots[0].getItemDamage() < 10000)
		{
			power += 100;
			slots[0].setItemDamage(slots[0].getItemDamage() + 1);
		}
		
		if(power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.fusion_core && slots[0].getItemDamage() < 5000)
		{
			power += 100;
			slots[0].setItemDamage(slots[0].getItemDamage() + 1);
		}
		
		if(power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.energy_core && slots[0].getItemDamage() < 5000)
		{
			power += 100;
			slots[0].setItemDamage(slots[0].getItemDamage() + 1);
		}
		
		if(flag1)
		{
			this.markDirty();
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
