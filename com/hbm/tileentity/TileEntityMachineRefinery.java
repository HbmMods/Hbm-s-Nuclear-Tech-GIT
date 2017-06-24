package com.hbm.tileentity;

import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IOilAcceptor;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.lib.Library;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineRefinery extends TileEntity implements ISidedInventory, IConsumer, IOilAcceptor {

	private ItemStack slots[];

	public int power = 0;
	public int oil = 0;
	public int fuel = 0;
	public int lubricant = 0;
	public int diesel = 0;
	public int kerosene = 0;
	public int sulfur = 0;
	public static final int maxPower = 100000;
	public static final int maxOil = 640;
	public static final int maxFuel = 64 * 100;
	public static final int maxLubricant =  64 * 100;
	public static final int maxDiesel = 64 * 100;
	public static final int maxKerosene = 64 * 100;
	public static final int maxSulfur = 100;
	public int age = 0;

	private static final int[] slots_top = new int[] { 1 };
	private static final int[] slots_bottom = new int[] { 0, 2, 4, 6, 8, 10, 11};
	private static final int[] slots_side = new int[] { 0, 3, 5, 7, 9 };
	
	private String customName;
	
	public TileEntityMachineRefinery() {
		slots = new ItemStack[12];
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
		return this.hasCustomInventoryName() ? this.customName : "container.machineRefinery";
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
		
		if(i == 0 && stack.getItem() instanceof ItemBattery)
			return true;
		if(i == 1 && stack.getItem() == ModItems.canister_oil)
			return true;
		if(stack.getItem() == ModItems.canister_empty) {
			if(i == 3)
				return true;
			if(i == 5)
				return true;
			if(i == 7)
				return true;
			if(i == 9)
				return true;
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
		oil = nbt.getInteger("oil");
		fuel = nbt.getInteger("fuel");
		lubricant = nbt.getInteger("lubricant");
		diesel = nbt.getInteger("diesel");
		kerosene = nbt.getInteger("kerosene");
		sulfur = nbt.getInteger("sulfur");
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
		nbt.setInteger("oil", oil);
		nbt.setInteger("fuel", fuel);
		nbt.setInteger("lubricant", lubricant);
		nbt.setInteger("diesel", diesel);
		nbt.setInteger("kerosene", kerosene);
		nbt.setInteger("sulfur", sulfur);
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
			if (itemStack.getItem() instanceof ItemBattery && ItemBattery.getCharge(itemStack) == 0)
				return true;
		if(i == 2)
			return true;
		if(i == 4)
			return true;
		if(i == 6)
			return true;
		if(i == 8)
			return true;
		if(i == 10)
			return true;
		if(i == 11)
			return true;
		
		return false;
	}
	
	@Override
	public void updateEntity() {
		
		int timer = 20;
		age++;
		if(age >= timer)
			age -= timer;

		if (!worldObj.isRemote) {
			
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			if(slots[1] != null && slots[1].getItem() == ModItems.canister_oil && oil + 5 <= maxOil) {
				if(slots[2] == null) {
					oil += 5;
					slots[1].stackSize--;
					if(slots[1].stackSize <= 0)
						slots[1] = null;
					slots[2] = new ItemStack(ModItems.canister_empty);
				}else if(slots[2] != null && slots[2].getItem() == ModItems.canister_empty && slots[2].stackSize < slots[2].getMaxStackSize()) {
					oil += 5;
					slots[1].stackSize--;
					if(slots[1].stackSize <= 0)
						slots[1] = null;
					slots[2].stackSize++;
				}
			}
			
			if(age == 0)
			if(power >= 100 && oil - 5 >= 0 && fuel + 45 <= maxFuel && 
					lubricant + 30 <= maxLubricant && 
					diesel + 20 <= maxDiesel && 
					kerosene + 5 <= maxKerosene) {
				
				oil -= 5;
				fuel += 45;
				lubricant += 30;
				diesel += 20;
				kerosene += 5;
				sulfur += 1;
				power -= 100;
			}
			
			if(slots[3] != null && slots[3].getItem() == ModItems.canister_empty && fuel - 100 >= 0) {
				if(slots[4] == null) {
					slots[4] = new ItemStack(ModItems.canister_smear);
					fuel -= 100;
					slots[3].stackSize--;
					if(slots[3].stackSize <= 0)
						slots[3] = null;
				} else if(slots[4] != null && slots[4].getItem() == ModItems.canister_smear && slots[4].stackSize < slots[4].getMaxStackSize()) {
					slots[4].stackSize++;
					fuel -= 100;
					slots[3].stackSize--;
					if(slots[3].stackSize <= 0)
						slots[3] = null;
				}
			}
			
			if(slots[5] != null && slots[5].getItem() == ModItems.canister_empty && lubricant - 100 >= 0) {
				if(slots[6] == null) {
					slots[6] = new ItemStack(ModItems.canister_canola);
					lubricant -= 100;
					slots[5].stackSize--;
					if(slots[5].stackSize <= 0)
						slots[5] = null;
				} else if(slots[6] != null && slots[6].getItem() == ModItems.canister_canola && slots[6].stackSize < slots[6].getMaxStackSize()) {
					slots[6].stackSize++;
					lubricant -= 100;
					slots[5].stackSize--;
					if(slots[5].stackSize <= 0)
						slots[5] = null;
				}
			}
			
			if(slots[7] != null && slots[7].getItem() == ModItems.canister_empty && diesel - 100 >= 0) {
				if(slots[8] == null) {
					slots[8] = new ItemStack(ModItems.canister_fuel);
					diesel -= 100;
					slots[7].stackSize--;
					if(slots[7].stackSize <= 0)
						slots[7] = null;
				} else if(slots[8] != null && slots[8].getItem() == ModItems.canister_fuel && slots[8].stackSize < slots[8].getMaxStackSize()) {
					slots[8].stackSize++;
					diesel -= 100;
					slots[7].stackSize--;
					if(slots[7].stackSize <= 0)
						slots[7] = null;
				}
			}
			
			if(slots[9] != null && slots[9].getItem() == ModItems.canister_empty && kerosene - 100 >= 0) {
				if(slots[10] == null) {
					slots[10] = new ItemStack(ModItems.canister_kerosene);
					kerosene -= 100;
					slots[9].stackSize--;
					if(slots[9].stackSize <= 0)
						slots[9] = null;
				} else if(slots[10] != null && slots[10].getItem() == ModItems.canister_kerosene && slots[10].stackSize < slots[10].getMaxStackSize()) {
					slots[10].stackSize++;
					kerosene -= 100;
					slots[9].stackSize--;
					if(slots[9].stackSize <= 0)
						slots[9] = null;
				}
			}
			
			if(sulfur >= maxSulfur) {
				if(slots[11] == null) {
					slots[11] = new ItemStack(ModItems.sulfur);
					sulfur -= maxSulfur;
				} else if(slots[11] != null && slots[11].getItem() == ModItems.sulfur && slots[11].stackSize < slots[11].getMaxStackSize()) {
					slots[11].stackSize++;
					sulfur -= maxSulfur;
					
				}
			}
		}
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public int getOilScaled(int i) {
		return (int)((oil * i) / maxOil);
	}
	
	public int getSmearScaled(int i) {
		return (fuel * i) / maxFuel;
	}
	
	public int getLubricantScaled(int i) {
		return (lubricant * i) / maxLubricant;
	}
	
	public int getDieselScaled(int i) {
		return (diesel * i) / maxDiesel;
	}
	
	public int getKeroseneScaled(int i) {
		return (kerosene * i) / maxKerosene;
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

	@Override
	public void setFill(int i) {
		this.oil = i;
	}

	@Override
	public int getFill() {
		return this.oil;
	}

	@Override
	public int getMaxFill() {
		return this.maxOil;
	}
}
