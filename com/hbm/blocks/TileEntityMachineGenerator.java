package com.hbm.blocks;

import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.items.ItemFuelRod;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineGenerator extends TileEntity implements ISidedInventory {

	private ItemStack slots[];
	
	public int water;
	public final int waterMax = 1000000;
	public int cool;
	public final int coolMax = 1000000;
	public int heat;
	public final int heatMax = 100000;
	public int power;
	public final int powerMax = 100000;
	
	private static final int[] slots_top = new int[] {};
	private static final int[] slots_bottom = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	private static final int[] slots_side = new int[] {};
	
	private String customName;
	
	public TileEntityMachineGenerator() {
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
		return this.hasCustomInventoryName() ? this.customName : "container.generator";
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

		water = nbt.getShort("water");
		cool = nbt.getShort("cool");
		power = nbt.getShort("power");
		heat = nbt.getShort("heat");
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
		nbt.setShort("water", (short) water);
		nbt.setShort("cool", (short) cool);
		nbt.setShort("power", (short) power);
		nbt.setShort("heat", (short) heat);
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
		//return j != 0 || i != 1 || itemStack.getItem() == Items.bucket;
		return true;
	}
	
	public int getWaterScaled(int i) {
		return (water * i) / waterMax;
	}
	
	public int getCoolantScaled(int i) {
		return (cool * i) / coolMax;
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / powerMax;
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / heatMax;
	}
	
	public boolean hasWater() {
		return water > 0;
	}
	
	public boolean hasCoolant() {
		return cool > 0;
	}
	
	public boolean hasPower() {
		return power > 0;
	}
	
	public boolean hasHeat() {
		return heat > 0;
	}

	public void updateEntity() {
		
		if(!worldObj.isRemote)
		{
			if(slots[9] != null && slots[9].getItem() == Items.water_bucket && this.water + 250000 <= waterMax)
			{
				this.slots[9].stackSize--;
				this.water += 250000;
				if(this.slots[9].stackSize == 0)
				{
					this.slots[9] = this.slots[9].getItem().getContainerItem(this.slots[9]);
				}
			}
			if(slots[9] != null && slots[9].getItem() == ModItems.rod_water && this.water + 250000 <= waterMax)
			{
				this.slots[9].stackSize--;
				this.water += 250000;
				if(this.slots[9].stackSize == 0)
				{
					this.slots[9] = this.slots[9].getItem().getContainerItem(this.slots[9]);
				}
			}
			if(slots[9] != null && slots[9].getItem() == ModItems.rod_dual_water && this.water + 500000 <= waterMax)
			{
				this.slots[9].stackSize--;
				this.water += 500000;
				if(this.slots[9].stackSize == 0)
				{
					this.slots[9] = this.slots[9].getItem().getContainerItem(this.slots[9]);
				}
			}
			if(slots[9] != null && slots[9].getItem() == ModItems.rod_quad_water && this.water + 1000000 <= waterMax)
			{
				this.slots[9].stackSize--;
				this.water += 1000000;
				if(this.slots[9].stackSize == 0)
				{
					this.slots[9] = this.slots[9].getItem().getContainerItem(this.slots[9]);
				}
			}
			
			if(slots[10] != null && slots[10].getItem() == ModItems.rod_coolant && this.cool + 250000 <= coolMax)
			{
				this.slots[10].stackSize--;
				this.cool += 250000;
				if(this.slots[10].stackSize == 0)
				{
					this.slots[10] = this.slots[10].getItem().getContainerItem(this.slots[10]);
				}
			}
			
			if(slots[10] != null && slots[10].getItem() == ModItems.rod_dual_coolant && this.cool + 500000 <= coolMax)
			{
				this.slots[10].stackSize--;
				this.cool += 500000;
				if(this.slots[10].stackSize == 0)
				{
					this.slots[10] = this.slots[10].getItem().getContainerItem(this.slots[10]);
				}
			}
			
			if(slots[10] != null && slots[10].getItem() == ModItems.rod_quad_coolant && this.cool + 1000000 <= coolMax)
			{
				this.slots[10].stackSize--;
				this.cool += 1000000;
				if(this.slots[10].stackSize == 0)
				{
					this.slots[10] = this.slots[10].getItem().getContainerItem(this.slots[10]);
				}
			}
			
			
			
			for(int i = 0; i < 9; i++)
			{
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_uranium_fuel)
				{
					int j = slots[i].getItemDamage();
					this.slots[i].setItemDamage(j += 1);
					attemptHeat(1);
					attemptPower(10);
				
					if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
					{
						this.slots[i] = new ItemStack(ModItems.rod_uranium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_dual_uranium_fuel)
				{
					int j = slots[i].getItemDamage();
					this.slots[i].setItemDamage(j += 1);
					attemptHeat(1);
					attemptPower(10);

					if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
					{
						this.slots[i] = new ItemStack(ModItems.rod_dual_uranium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_quad_uranium_fuel)
				{
					int j = slots[i].getItemDamage();
					this.slots[i].setItemDamage(j += 1);
					attemptHeat(1);
					attemptPower(10);

					if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
					{
						this.slots[i] = new ItemStack(ModItems.rod_quad_uranium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_plutonium_fuel)
				{
					int j = slots[i].getItemDamage();
					this.slots[i].setItemDamage(j += 1);
					attemptHeat(2);
					attemptPower(15);

					if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
					{
						this.slots[i] = new ItemStack(ModItems.rod_plutonium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_dual_plutonium_fuel)
				{
					int j = slots[i].getItemDamage();
					this.slots[i].setItemDamage(j += 1);
					attemptHeat(2);
					attemptPower(15);

					if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
					{
						this.slots[i] = new ItemStack(ModItems.rod_dual_plutonium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_quad_plutonium_fuel)
				{
					int j = slots[i].getItemDamage();
					this.slots[i].setItemDamage(j += 1);
					attemptHeat(2);
					attemptPower(15);

					if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
					{
						this.slots[i] = new ItemStack(ModItems.rod_quad_plutonium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_mox_fuel)
				{
					int j = slots[i].getItemDamage();
					this.slots[i].setItemDamage(j += 1);
					attemptHeat(1);
					attemptPower(5);

					if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
					{
						this.slots[i] = new ItemStack(ModItems.rod_mox_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_dual_mox_fuel)
				{
					int j = slots[i].getItemDamage();
					this.slots[i].setItemDamage(j += 1);
					attemptHeat(1);
					attemptPower(5);

					if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
					{
						this.slots[i] = new ItemStack(ModItems.rod_dual_mox_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_quad_mox_fuel)
				{
					int j = slots[i].getItemDamage();
					this.slots[i].setItemDamage(j += 1);
					attemptHeat(1);
					attemptPower(5);

					if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
					{
						this.slots[i] = new ItemStack(ModItems.rod_quad_mox_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_schrabidium_fuel)
				{
					int j = slots[i].getItemDamage();
					this.slots[i].setItemDamage(j += 1);
					attemptHeat(10);
					attemptPower(25);

					if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
					{
						this.slots[i] = new ItemStack(ModItems.rod_schrabidium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_dual_schrabidium_fuel)
				{
					int j = slots[i].getItemDamage();
					this.slots[i].setItemDamage(j += 1);
					attemptHeat(10);
					attemptPower(25);

					if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
					{
						this.slots[i] = new ItemStack(ModItems.rod_dual_schrabidium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_quad_schrabidium_fuel)
				{
					int j = slots[i].getItemDamage();
					this.slots[i].setItemDamage(j += 1);
					attemptHeat(10);
					attemptPower(25);

					if(this.slots[i].getItemDamage() == this.slots[i].getMaxDamage())
					{
						this.slots[i] = new ItemStack(ModItems.rod_quad_schrabidium_fuel_depleted);
					}
				}
			}
			
			if(this.power > powerMax)
			{
				this.power = powerMax;
			}
			
			if(this.heat > heatMax)
			{
				this.explode();
			}
			
			if(((slots[0] != null && slots[0].getItem() instanceof ItemFuelRod) || slots[0] == null) && 
					((slots[1] != null && !(slots[1].getItem() instanceof ItemFuelRod)) || slots[1] == null) && 
					((slots[2] != null && !(slots[2].getItem() instanceof ItemFuelRod)) || slots[2] == null) && 
					((slots[3] != null && !(slots[3].getItem() instanceof ItemFuelRod)) || slots[3] == null) && 
					((slots[4] != null && !(slots[4].getItem() instanceof ItemFuelRod)) || slots[4] == null) && 
					((slots[5] != null && !(slots[5].getItem() instanceof ItemFuelRod)) || slots[5] == null) && 
					((slots[6] != null && !(slots[6].getItem() instanceof ItemFuelRod)) || slots[6] == null) && 
					((slots[7] != null && !(slots[7].getItem() instanceof ItemFuelRod)) || slots[7] == null) && 
					((slots[8] != null && !(slots[8].getItem() instanceof ItemFuelRod)) || slots[8] == null))
			{
				if(this.heat - 10 >= 0 && this.cool - 10 >= 0)
				{
					this.heat -= 10;
					this.cool -= 10;
				}
				
				if(this.heat < 10 && this.cool != 0)
				{
					this.heat--;
					this.cool--;
				}
				
				if(this.heat != 0 && this.cool == 0)
				{
					this.heat--;
				}
				
				if(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof MachineGenerator)
				((MachineGenerator)this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord)).isLoaded = false;
				
			} else {

				if(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof MachineGenerator)
				((MachineGenerator)this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord)).isLoaded = true;
			}
		}
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
				if(entity instanceof TileEntityMachineElectricFurnace && ((TileEntityMachineElectricFurnace)entity).power + 100 <= ((TileEntityMachineElectricFurnace)entity).maxPower)
				{
					((TileEntityMachineElectricFurnace)entity).power += 100;
					this.power -= 100;
				} else
				if(entity instanceof TileEntityWireCoated && ((TileEntityWireCoated)entity).power + 100 <= ((TileEntityWireCoated)entity).maxPower)
				{
					((TileEntityWireCoated)entity).power += 100;
					this.power -= 100;
				} else
				if(entity instanceof TileEntityMachineDeuterium && ((TileEntityMachineDeuterium)entity).power + 100 <= ((TileEntityMachineDeuterium)entity).maxPower)
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
		
		//Batteries
		if(power - 100 >= 0 && slots[11] != null && slots[11].getItem() == ModItems.battery_generic && slots[11].getItemDamage() > 0)
		{
			power -= 100;
			slots[11].setItemDamage(slots[11].getItemDamage() - 1);
		}
		if(power - 100 >= 0 && slots[11] != null && slots[11].getItem() == ModItems.battery_advanced && slots[11].getItemDamage() > 0)
		{
			power -= 100;
			slots[11].setItemDamage(slots[11].getItemDamage() - 1);
		}
		if(power - 100 >= 0 && slots[11] != null && slots[11].getItem() == ModItems.battery_schrabidium && slots[11].getItemDamage() > 0)
		{
			power -= 100;
			slots[11].setItemDamage(slots[11].getItemDamage() - 1);
		}
	}
	
	public void attemptPower(int i) {
		if(this.water - i >= 0)
		{
			this.power += i;
			this.water -= i;
		}
	}
	
	public void attemptHeat(int i) {
		if(this.cool - i >= 0)
		{
			this.cool -= i;
		} else {
			this.heat += i;
		}
	}
	
	public void explode() {
		for(int i = 0; i < slots.length; i++)
		{
			this.slots[i] = null;
		}
		
    	worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 18.0F, true);
    	ExplosionNukeGeneric.waste(worldObj, this.xCoord, this.yCoord, this.zCoord, 35);
    	worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.flowing_lava);
	}
}