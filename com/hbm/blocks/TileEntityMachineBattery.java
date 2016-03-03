package com.hbm.blocks;

import com.hbm.interfaces.IConductor;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineBattery extends TileEntity implements ISidedInventory, IConductor {

	private ItemStack slots[];
	
	public int power = 0;
	public final int maxPower = 100000;
	
	public boolean conducts = false;
	
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {0, 1};
	private static final int[] slots_side = new int[] {1};
	
	private String customName;
	
	public TileEntityMachineBattery() {
		slots = new ItemStack[2];
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
		return this.hasCustomInventoryName() ? this.customName : "container.battery";
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
		return 1;
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
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
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
		
		this.power = nbt.getShort("power");
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
		nbt.setShort("power", (short) power);
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
		return j != 0 || i != 1 || itemStack.getItem() == Items.bucket;
	}

	public int getPowerRemainingScaled(int i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void updateEntity() {
		if(this.conducts)
		{
		/*//Electric Furnace
		if(worldObj.getBlock(xCoord + 1, yCoord, zCoord) instanceof MachineElectricFurnace)
		{
			TileEntityMachineElectricFurnace entity = (TileEntityMachineElectricFurnace) worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord - 1, yCoord, zCoord) instanceof MachineElectricFurnace)
		{
			TileEntityMachineElectricFurnace entity = (TileEntityMachineElectricFurnace) worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord + 1, zCoord) instanceof MachineElectricFurnace)
		{
			TileEntityMachineElectricFurnace entity = (TileEntityMachineElectricFurnace) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) instanceof MachineElectricFurnace)
		{
			TileEntityMachineElectricFurnace entity = (TileEntityMachineElectricFurnace) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord + 1) instanceof MachineElectricFurnace)
		{
			TileEntityMachineElectricFurnace entity = (TileEntityMachineElectricFurnace) worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord - 1) instanceof MachineElectricFurnace)
		{
			TileEntityMachineElectricFurnace entity = (TileEntityMachineElectricFurnace) worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		
		//Wire
		if(worldObj.getBlock(xCoord + 1, yCoord, zCoord) instanceof WireCoated)
		{
			TileEntityWireCoated entity = (TileEntityWireCoated) worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord - 1, yCoord, zCoord) instanceof WireCoated)
		{
			TileEntityWireCoated entity = (TileEntityWireCoated) worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord + 1, zCoord) instanceof WireCoated)
		{
			TileEntityWireCoated entity = (TileEntityWireCoated) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) instanceof WireCoated)
		{
			TileEntityWireCoated entity = (TileEntityWireCoated) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord + 1) instanceof WireCoated)
		{
			TileEntityWireCoated entity = (TileEntityWireCoated) worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord - 1) instanceof WireCoated)
		{
			TileEntityWireCoated entity = (TileEntityWireCoated) worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		
		//Deuterium
		if(worldObj.getBlock(xCoord + 1, yCoord, zCoord) instanceof MachineDeuterium)
		{
			TileEntityMachineDeuterium entity = (TileEntityMachineDeuterium) worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord - 1, yCoord, zCoord) instanceof MachineDeuterium)
		{
			TileEntityMachineDeuterium entity = (TileEntityMachineDeuterium) worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord + 1, zCoord) instanceof MachineDeuterium)
		{
			TileEntityMachineDeuterium entity = (TileEntityMachineDeuterium) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) instanceof MachineDeuterium)
		{
			TileEntityMachineDeuterium entity = (TileEntityMachineDeuterium) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord + 1) instanceof MachineDeuterium)
		{
			TileEntityMachineDeuterium entity = (TileEntityMachineDeuterium) worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord - 1) instanceof MachineDeuterium)
		{
			TileEntityMachineDeuterium entity = (TileEntityMachineDeuterium) worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		
		//Batteries
		if(worldObj.getBlock(xCoord + 1, yCoord, zCoord) instanceof MachineBattery)
		{
			TileEntityMachineBattery entity = (TileEntityMachineBattery) worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord - 1, yCoord, zCoord) instanceof MachineBattery)
		{
			TileEntityMachineBattery entity = (TileEntityMachineBattery) worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord + 1, zCoord) instanceof MachineBattery)
		{
			TileEntityMachineBattery entity = (TileEntityMachineBattery) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) instanceof MachineBattery)
		{
			TileEntityMachineBattery entity = (TileEntityMachineBattery) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord + 1) instanceof MachineBattery)
		{
			TileEntityMachineBattery entity = (TileEntityMachineBattery) worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord - 1) instanceof MachineBattery)
		{
			TileEntityMachineBattery entity = (TileEntityMachineBattery) worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}*/
			
			
			//Energy distributing algorithm V2.1                                                                                                                     
			TileEntity entity0 = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
			TileEntity entity1 = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
			TileEntity entity2 = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
			TileEntity entity3 = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
			TileEntity entity4 = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
			TileEntity entity5 = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
			
			TileEntity entity;
			
			for(int i = 0; i < 6; i++)
			{
				entity = entity0;
				if(i == 1) entity = entity1;
				if(i == 2) entity = entity2;
				if(i == 3) entity = entity3;
				if(i == 4) entity = entity4;
				if(i == 5) entity = entity5;
				
				if(entity instanceof TileEntityMachineElectricFurnace ||
						entity instanceof TileEntityWireCoated ||
						entity instanceof TileEntityMachineDeuterium ||
						entity instanceof TileEntityMachineBattery ||
						entity instanceof TileEntityLaunchPad)
				{
					if(entity instanceof TileEntityMachineElectricFurnace && ((TileEntityMachineElectricFurnace)entity).power + 100 <= TileEntityMachineElectricFurnace.maxPower)
					{
						((TileEntityMachineElectricFurnace)entity).power += 100;
						this.power -= 100;
					} else
					if(entity instanceof TileEntityWireCoated && TileEntityWireCoated.power + 100 <= TileEntityWireCoated.maxPower)
					{
						TileEntityWireCoated.power += 100;
						this.power -= 100;
					} else
					if(entity instanceof TileEntityMachineDeuterium && ((TileEntityMachineDeuterium)entity).power + 100 <= TileEntityMachineDeuterium.maxPower)
					{
						((TileEntityMachineDeuterium)entity).power += 100;
						this.power -= 100;
					} else
					if(entity instanceof TileEntityMachineBattery && ((TileEntityMachineBattery)entity).power + 100 <= ((TileEntityMachineBattery)entity).maxPower && !((TileEntityMachineBattery)entity).conducts)
					{
						((TileEntityMachineBattery)entity).power += 100;
						this.power -= 100;
					}
				}
			}
	}

		if(power - 100 >= 0 && slots[1] != null && slots[1].getItem() == ModItems.battery_generic && slots[1].getItemDamage() > 0)
		{
			power -= 100;
			slots[1].setItemDamage(slots[1].getItemDamage() - 1);
		}
		if(power - 100 >= 0 && slots[1] != null && slots[1].getItem() == ModItems.battery_advanced && slots[1].getItemDamage() > 0)
		{
			power -= 100;
			slots[1].setItemDamage(slots[1].getItemDamage() - 1);
		}
		if(power - 100 >= 0 && slots[1] != null && slots[1].getItem() == ModItems.battery_schrabidium && slots[1].getItemDamage() > 0)
		{
			power -= 100;
			slots[1].setItemDamage(slots[1].getItemDamage() - 1);
		}
		if(power - 100 >= 0 && slots[1] != null && slots[1].getItem() == ModItems.factory_core_titanium && slots[1].getItemDamage() > 0)
		{
			power -= 100;
			slots[1].setItemDamage(slots[1].getItemDamage() - 1);
		}
		if(power - 100 >= 0 && slots[1] != null && slots[1].getItem() == ModItems.factory_core_advanced && slots[1].getItemDamage() > 0)
		{
			power -= 100;
			slots[1].setItemDamage(slots[1].getItemDamage() - 1);
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
		
		if(power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.battery_schrabidium && slots[0].getItemDamage() < 1000)
		{
			power += 100;
			slots[0].setItemDamage(slots[0].getItemDamage() + 1);
		}
		
		if(power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.fusion_core && slots[0].getItemDamage() < 5000)
		{
			power += 100;
			slots[0].setItemDamage(slots[0].getItemDamage() + 1);
		}
	}

}
