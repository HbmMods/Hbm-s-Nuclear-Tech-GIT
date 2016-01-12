package com.hbm.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.hbm.items.ModItems;

public class TileEntityMachineCoal extends TileEntity implements ISidedInventory {

	private ItemStack slots[];
	
	public int power;
	public int water;
	public int burnTime;
	public static final int maxPower = 10000;
	public static final int maxWater = 10000;
	
	private static final int[] slots_top = new int[] {1};
	private static final int[] slots_bottom = new int[] {2};
	private static final int[] slots_side = new int[] {0};
	
	private String customName;
	
	public TileEntityMachineCoal() {
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
		return this.hasCustomInventoryName() ? this.customName : "container.machineCoal";
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
			return player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <=64;
		}
	}
	
	//You scrubs aren't needed for anything (right now)
	public void openInventory() {}
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}
	
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
	
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		this.power = nbt.getShort("powerTime");
		this.water = nbt.getShort("water");
		slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = (NBTTagCompound) list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length)
			{
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("powerTime", (short) power);
		nbt.setShort("water", (short) water);
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
	
	public int getWaterScaled(int i) {
		return (water * i) / maxWater;
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public void updateEntity() {
		//Water
		if(slots[0] != null && slots[0].getItem() == Items.water_bucket && this.water + 2500 <= maxWater)
		{
			this.slots[0].stackSize--;
			this.water += 2500;
			if(this.slots[0].stackSize == 0)
			{
				this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
			}
		}
		if(slots[0] != null && slots[0].getItem() == ModItems.rod_water && this.water + 2500 <= maxWater)
		{
			this.slots[0].stackSize--;
			this.water += 2500;
			if(this.slots[0].stackSize == 0)
			{
				this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
			}
		}
		if(slots[0] != null && slots[0].getItem() == ModItems.rod_dual_water && this.water + 5000 <= maxWater)
		{
			this.slots[0].stackSize--;
			this.water += 5000;
			if(this.slots[0].stackSize == 0)
			{
				this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
			}
		}
		if(slots[0] != null && slots[0].getItem() == ModItems.rod_quad_water && this.water + 10000 <= maxWater)
		{
			this.slots[0].stackSize--;
			this.water += 10000;
			if(this.slots[0].stackSize == 0)
			{
				this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
			}
		}

		//Battery Item
		if(power - 100 >= 0 && slots[2] != null && slots[2].getItem() == ModItems.battery_generic && slots[2].getItemDamage() > 0)
		{
			power -= 100;
			slots[2].setItemDamage(slots[2].getItemDamage() - 1);
		}
		if(power - 100 >= 0 && slots[2] != null && slots[2].getItem() == ModItems.battery_advanced && slots[2].getItemDamage() > 0)
		{
			power -= 100;
			slots[2].setItemDamage(slots[2].getItemDamage() - 1);
		}
		if(power - 100 >= 0 && slots[2] != null && slots[2].getItem() == ModItems.battery_schrabidium && slots[2].getItemDamage() > 0)
		{
			power -= 100;
			slots[2].setItemDamage(slots[2].getItemDamage() - 1);
		}
		
		//Electric Furnace
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
		}
		if(worldObj.getBlock(xCoord, yCoord, zCoord - 1) instanceof LaunchPad)
		{
			TileEntityLaunchPad entity = (TileEntityLaunchPad) worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
			if(entity.power + 100 <= entity.maxPower && this.power - 100 >= 0)
			{
				entity.power += 100;
				this.power -= 100;
			}
		}

		boolean flag = this.burnTime > 0;
		boolean flag1 = false;
		
		if(!worldObj.isRemote)
		{
			
			boolean trigger = true;
			
			if(isItemValid() && this.burnTime == 0)
			{
				trigger = false;
			}
			
			if(trigger)
            {
                flag1 = true;
                MachineCoal.updateBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
		}
		
		generate();
	}
	
	public void generate() {
		if(slots[1] != null && slots[1].getItem() == Items.coal && burnTime <= 0)
		{
			slots[1].stackSize -= 1;
			burnTime = 200;
			if(slots[1].stackSize == 0)
			{
				slots[1] = null;
			}
		}
		if(slots[1] != null && slots[1].getItem() == Item.getItemFromBlock(Blocks.coal_block) && burnTime <= 0)
		{
			slots[1].stackSize -= 1;
			burnTime = 2000;
			if(slots[1].stackSize == 0)
			{
				slots[1] = null;
			}
		}
		
		if(burnTime > 0)
		{
			burnTime--;
			
			if(water > 0)
			{
				water -= 1;
				
				if(power + 1 <= maxPower)
				{
					power += 10;
				}
			}
		}
	}
	
	public boolean isItemValid() {
		
		if(slots[1] != null && slots[1].getItem() == Items.coal)
		{
			return true;
		}
		if(slots[1] != null && slots[1].getItem() == Item.getItemFromBlock(Blocks.coal_block))
		{
			return true;
		}
		
		return false;
	}
}
