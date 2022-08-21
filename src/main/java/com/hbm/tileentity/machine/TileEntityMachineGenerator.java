package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.blocks.machine.MachineGenerator;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFuelRod;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

@Deprecated //y'know, the single block reactor
public class TileEntityMachineGenerator extends TileEntity implements ISidedInventory {

	private ItemStack slots[];
	
	public int heat;
	public final int heatMax = 100000;
	public long power;
	public final long powerMax = 100000;
	public boolean isLoaded = false;
	public FluidTank[] tanks;
	
	private static final int[] slots_top = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
	private static final int[] slots_bottom = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
	private static final int[] slots_side = new int[] {9, 10, 11};
	
	private String customName;
	
	public TileEntityMachineGenerator() {
		slots = new ItemStack[14];
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.WATER, 32000, 0);
		tanks[1] = new FluidTank(Fluids.COOLANT, 16000, 1);
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
			if(itemStack.getItem() == Items.water_bucket)
				return true;
		if(i == 10)
			if(itemStack.getItem() == ModItems.fluid_tank_full)
				return true;
		if(i == 11)
			if(itemStack.getItem() instanceof IBatteryItem)
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

		power = nbt.getLong("power");
		heat = nbt.getInteger("heat");
		slots = new ItemStack[getSizeInventory()];
		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "coolant");
		
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
		nbt.setLong("power", power);
		nbt.setInteger("heat", heat);
		NBTTagList list = new NBTTagList();
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "coolant");
		
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
			/*if(itemStack.getItem() == ModItems.rod_uranium_fuel_depleted ||
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
					itemStack.getItem() == ModItems.rod_quad_schrabidium_fuel_depleted)*/
				return true;
		if(i == 9 || i == 10)
			if(itemStack.getItem() == Items.bucket || itemStack.getItem() == ModItems.rod_empty || itemStack.getItem() == ModItems.rod_dual_empty || itemStack.getItem() == ModItems.rod_quad_empty)
				return true;
		if(i == 11)
			if (itemStack.getItem() instanceof IBatteryItem && ((IBatteryItem)itemStack.getItem()).getCharge(itemStack) == ((IBatteryItem)itemStack.getItem()).getMaxCharge())
				return true;
		
		return false;
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / powerMax;
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / heatMax;
	}
	
	public boolean hasPower() {
		return power > 0;
	}
	
	public boolean hasHeat() {
		return heat > 0;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote)
		{
			tanks[0].loadTank(9, 12, slots);
			tanks[1].loadTank(10, 13, slots);
			
			for(int i = 0; i < 2; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			//Batteries
			power = Library.chargeItemsFromTE(slots, 11, power, powerMax);
			
			/*for(int i = 0; i < 9; i++)
			{
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_uranium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(slots[i]);
					ItemFuelRod.setLifeTime(slots[i], j + 1);
					attemptHeat(1);
					attemptPower(100);
				
					if(ItemFuelRod.getLifeTime(slots[i]) == ((ItemFuelRod)slots[i].getItem()).lifeTime)
					{
						this.slots[i] = new ItemStack(ModItems.rod_uranium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_dual_uranium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(slots[i]);
					ItemFuelRod.setLifeTime(slots[i], j + 1);
					attemptHeat(1);
					attemptPower(100);

					if(ItemFuelRod.getLifeTime(slots[i]) == ((ItemFuelRod)slots[i].getItem()).lifeTime)
					{
						this.slots[i] = new ItemStack(ModItems.rod_dual_uranium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_quad_uranium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(slots[i]);
					ItemFuelRod.setLifeTime(slots[i], j + 1);
					attemptHeat(1);
					attemptPower(100);

					if(ItemFuelRod.getLifeTime(slots[i]) == ((ItemFuelRod)slots[i].getItem()).lifeTime)
					{
						this.slots[i] = new ItemStack(ModItems.rod_quad_uranium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_plutonium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(slots[i]);
					ItemFuelRod.setLifeTime(slots[i], j + 1);
					attemptHeat(2);
					attemptPower(150);

					if(ItemFuelRod.getLifeTime(slots[i]) == ((ItemFuelRod)slots[i].getItem()).lifeTime)
					{
						this.slots[i] = new ItemStack(ModItems.rod_plutonium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_dual_plutonium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(slots[i]);
					ItemFuelRod.setLifeTime(slots[i], j + 1);
					attemptHeat(2);
					attemptPower(150);

					if(ItemFuelRod.getLifeTime(slots[i]) == ((ItemFuelRod)slots[i].getItem()).lifeTime)
					{
						this.slots[i] = new ItemStack(ModItems.rod_dual_plutonium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_quad_plutonium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(slots[i]);
					ItemFuelRod.setLifeTime(slots[i], j + 1);
					attemptHeat(2);
					attemptPower(150);

					if(ItemFuelRod.getLifeTime(slots[i]) == ((ItemFuelRod)slots[i].getItem()).lifeTime)
					{
						this.slots[i] = new ItemStack(ModItems.rod_quad_plutonium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_mox_fuel)
				{
					int j = ItemFuelRod.getLifeTime(slots[i]);
					ItemFuelRod.setLifeTime(slots[i], j + 1);
					attemptHeat(1);
					attemptPower(50);

					if(ItemFuelRod.getLifeTime(slots[i]) == ((ItemFuelRod)slots[i].getItem()).lifeTime)
					{
						this.slots[i] = new ItemStack(ModItems.rod_mox_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_dual_mox_fuel)
				{
					int j = ItemFuelRod.getLifeTime(slots[i]);
					ItemFuelRod.setLifeTime(slots[i], j + 1);
					attemptHeat(1);
					attemptPower(50);

					if(ItemFuelRod.getLifeTime(slots[i]) == ((ItemFuelRod)slots[i].getItem()).lifeTime)
					{
						this.slots[i] = new ItemStack(ModItems.rod_dual_mox_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_quad_mox_fuel)
				{
					int j = ItemFuelRod.getLifeTime(slots[i]);
					ItemFuelRod.setLifeTime(slots[i], j + 1);
					attemptHeat(1);
					attemptPower(50);

					if(ItemFuelRod.getLifeTime(slots[i]) == ((ItemFuelRod)slots[i].getItem()).lifeTime)
					{
						this.slots[i] = new ItemStack(ModItems.rod_quad_mox_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_schrabidium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(slots[i]);
					ItemFuelRod.setLifeTime(slots[i], j + 1);
					attemptHeat(10);
					attemptPower(25000);

					if(ItemFuelRod.getLifeTime(slots[i]) == ((ItemFuelRod)slots[i].getItem()).lifeTime)
					{
						this.slots[i] = new ItemStack(ModItems.rod_schrabidium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_dual_schrabidium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(slots[i]);
					ItemFuelRod.setLifeTime(slots[i], j + 1);
					attemptHeat(10);
					attemptPower(25000);

					if(ItemFuelRod.getLifeTime(slots[i]) == ((ItemFuelRod)slots[i].getItem()).lifeTime)
					{
						this.slots[i] = new ItemStack(ModItems.rod_dual_schrabidium_fuel_depleted);
					}
				}
				if(slots[i] != null && slots[i].getItem() == ModItems.rod_quad_schrabidium_fuel)
				{
					int j = ItemFuelRod.getLifeTime(slots[i]);
					ItemFuelRod.setLifeTime(slots[i], j + 1);
					attemptHeat(10);
					attemptPower(25000);

					if(ItemFuelRod.getLifeTime(slots[i]) == ((ItemFuelRod)slots[i].getItem()).lifeTime)
					{
						this.slots[i] = new ItemStack(ModItems.rod_quad_schrabidium_fuel_depleted);
					}
				}
			}*/
			
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
				if(this.heat - 10 >= 0 && tanks[1].getFill() - 2 >= 0)
				{
					this.heat -= 10;
					this.tanks[1].setFill(tanks[1].getFill() - 2);
				}
				
				if(this.heat < 10 && heat != 0 && this.tanks[1].getFill() != 0)
				{
					this.heat--;
					this.tanks[1].setFill(tanks[1].getFill() - 1);
				}
				
				if(this.heat != 0 && this.tanks[1].getFill() == 0)
				{
					this.heat--;
				}
				
				if(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof MachineGenerator)
				this.isLoaded = false;
				
			} else {

				if(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof MachineGenerator)
				this.isLoaded = true;
			}
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
	}

	public void attemptPower(int i) {
		
		int j = (int) Math.ceil(i / 100);
		
		if(this.tanks[0].getFill() - j >= 0)
		{
			this.power += i;
			if(j > tanks[0].getMaxFill() / 25)
				j = tanks[0].getMaxFill() / 25;
			this.tanks[0].setFill(tanks[0].getFill() - j);
		}
	}
	
	public void attemptHeat(int i) {
		Random rand = new Random();
		
		int j = rand.nextInt(i + 1);
		
		if(this.tanks[1].getFill() - j >= 0)
		{
			this.tanks[1].setFill(tanks[1].getFill() - j);
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