package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineGenerator;
import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.items.special.ItemFuelRod;
import com.hbm.lib.Library;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineGenerator extends TileEntity implements ISidedInventory, ISource {

	private ItemStack slots[];
	
	public int water;
	public final int waterMax = 1000000;
	public int cool;
	public final int coolMax = 1000000;
	public int heat;
	public final int heatMax = 100000;
	public int power;
	public final int powerMax = 100000;
	public boolean isLoaded = false;
	public int age = 0;
	public List<IConsumer> list = new ArrayList();
	
	private static final int[] slots_top = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
	private static final int[] slots_bottom = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	private static final int[] slots_side = new int[] {9, 10, 11};
	
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
		if(i == 0 || 
				i == 1 || 
				i == 2 || 
				i == 3 || 
				i == 4 || 
				i == 5 || 
				i == 6 || 
				i == 7 || 
				i == 8)
			if(itemStack.getItem() instanceof ItemFuelRod)
				return true;
		if(i == 9)
			if(itemStack.getItem() == ModItems.rod_water || itemStack.getItem() == ModItems.rod_dual_water || itemStack.getItem() == ModItems.rod_quad_water || itemStack.getItem() == Items.water_bucket)
				return true;
		if(i == 10)
			if(itemStack.getItem() == ModItems.rod_coolant || itemStack.getItem() == ModItems.rod_dual_coolant || itemStack.getItem() == ModItems.rod_quad_coolant)
				return true;
		if(i == 11)
			if(itemStack.getItem() instanceof ItemBattery)
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

		water = nbt.getInteger("water");
		cool = nbt.getInteger("cool");
		power = nbt.getInteger("power");
		heat = nbt.getInteger("heat");
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
		nbt.setInteger("water", water);
		nbt.setInteger("cool", cool);
		nbt.setInteger("power", power);
		nbt.setInteger("heat", heat);
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
		if(i == 0 ||
				i == 1 ||
				i == 2 ||
				i == 3 ||
				i == 4 ||
				i == 5 ||
				i == 6 ||
				i == 7 ||
				i == 8)
			if(itemStack.getItem() == ModItems.rod_uranium_fuel_depleted ||
					itemStack.getItem() == ModItems.rod_dual_uranium_fuel_depleted ||
					itemStack.getItem() == ModItems.rod_quad_uranium_fuel_depleted ||
					itemStack.getItem() == ModItems.rod_plutonium_fuel_depleted ||
					itemStack.getItem() == ModItems.rod_dual_plutonium_fuel_depleted ||
					itemStack.getItem() == ModItems.rod_quad_plutonium_fuel_depleted ||
					itemStack.getItem() == ModItems.rod_mox_fuel_depleted ||
					itemStack.getItem() == ModItems.rod_dual_mox_fuel_depleted ||
					itemStack.getItem() == ModItems.rod_quad_mox_fuel_depleted ||
					itemStack.getItem() == ModItems.rod_schrabidium_fuel_depleted ||
					itemStack.getItem() == ModItems.rod_dual_schrabidium_fuel_depleted ||
					itemStack.getItem() == ModItems.rod_quad_schrabidium_fuel_depleted)
				return true;
		if(i == 9 || i == 10)
			if(itemStack.getItem() == Items.bucket || itemStack.getItem() == ModItems.rod_empty || itemStack.getItem() == ModItems.rod_dual_empty || itemStack.getItem() == ModItems.rod_quad_empty)
				return true;
		if(i == 11)
			if (itemStack.getItem() instanceof ItemBattery && ItemBattery.getCharge(itemStack) == ItemBattery.getMaxChargeStatic(itemStack))
				return true;
		
		return false;
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

	@Override
	public void updateEntity() {

		age++;
		if(age >= 20)
		{
			age = 0;
		}
		
		if(age == 9 || age == 19)
			ffgeuaInit();
		
		//if(!worldObj.isRemote)
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
			if(slots[9] != null && slots[9].getItem() == ModItems.inf_water)
			{
				this.water = this.waterMax;
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
			
			if(slots[10] != null && slots[10].getItem() == ModItems.inf_coolant)
			{
				this.cool = coolMax;
			}
			
			
			
			for(int i = 0; i < 9; i++)
			{
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_uranium_fuel)
				{
					int j = slots[i].getItemDamage();
					this.slots[i].setItemDamage(j += 1);
					attemptHeat(1);
					attemptPower(100);
				
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
					attemptPower(100);

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
					attemptPower(100);

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
					attemptPower(150);

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
					attemptPower(150);

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
					attemptPower(150);

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
					attemptPower(50);

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
					attemptPower(50);

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
					attemptPower(50);

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
					attemptPower(25000);

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
					attemptPower(25000);

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
					attemptPower(25000);

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
				if(this.heat - 10 >= 0 && this.cool - 2 >= 0)
				{
					this.heat -= 10;
					this.cool -= 2;
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
				this.isLoaded = false;
				
			} else {

				if(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof MachineGenerator)
				this.isLoaded = true;
			}
		}
		
		//Batteries
		
		power = Library.chargeItemsFromTE(slots, 11, power, powerMax);
	}
	
	public void attemptPower(int i) {
		if(this.water - i >= 0)
		{
			this.power += i;
			this.water -= i;
		}
	}
	
	public void attemptHeat(int i) {
		Random rand = new Random();
		
		int j = rand.nextInt(i);
		
		if(this.cool - j >= 0)
		{
			this.cool -= j;
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

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord, this.yCoord + 1, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 1, this.zCoord, getTact());
		ffgeua(this.xCoord - 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord + 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord - 1, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord + 1, getTact());
	}
	
	public boolean getTact() {
		if(age >= 0 && age < 10)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public int getSPower() {
		return power;
	}

	@Override
	public void setSPower(int i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		this.list.clear();
	}
}