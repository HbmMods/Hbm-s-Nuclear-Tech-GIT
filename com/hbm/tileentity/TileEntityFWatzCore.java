package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.entity.logic.EntityNukeExplosionAdvanced;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IReactor;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.items.special.WatzFuel;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.world.FWatz;

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
import net.minecraft.world.World;

public class TileEntityFWatzCore extends TileEntity implements ISidedInventory, IReactor, ISource {

	public int cool;
	public final static int maxCool = 100000000;
	public int power;
	public final static int maxPower = 100000000;
	public int amat;
	public final static int maxAmat = 100000000;
	public int aSchrab;
	public final static int maxASchrab = 100000000;
	public boolean cooldown = false;
	
	Random rand = new Random();
	
	private ItemStack slots[];
	public int age = 0;
	public List<IConsumer> list = new ArrayList();
	
	private String customName;

	public TileEntityFWatzCore() {
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
		return this.hasCustomInventoryName() ? this.customName : "container.fusionaryWatzPlant";
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
			return true;
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
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return null;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		cool = nbt.getInteger("cool");
		power = nbt.getInteger("power");
		amat = nbt.getInteger("amat");
		aSchrab = nbt.getInteger("aSchrab");
		
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
		nbt.setInteger("cool", cool);
		nbt.setInteger("power", power);
		nbt.setInteger("amat", amat);
		nbt.setInteger("aSchrab", aSchrab);
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
	public boolean isStructureValid(World world) {
		return FWatz.checkHull(world, this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public boolean isCoatingValid(World world) {
		{
			return true;
		}
	}

	@Override
	public boolean hasFuse() {
		return slots[1] != null && (slots[1].getItem() == ModItems.fuse || slots[1].getItem() == ModItems.screwdriver);
	}
	
	@Override
	public int getCoolantScaled(int i) {
		return (cool/100 * i) / (maxCool/100);
	}
	
	@Override
	public int getPowerScaled(int i) {
		return (power/100 * i) / (maxPower/100);
	}
	
	@Override
	public int getWaterScaled(int i) {
		return (amat/100 * i) / (maxAmat/100);
	}
	
	@Override
	public int getHeatScaled(int i) {
		return (aSchrab/100 * i) / (maxASchrab/100);
	}
	
	public int getSingularityType() {
		
		if(slots[2] != null) {
			Item item = slots[2].getItem();

			if(item == ModItems.singularity)
				return 1;
			if(item == ModItems.singularity_counter_resonant)
				return 2;
			if(item == ModItems.singularity_super_heated)
				return 3;
			if(item == ModItems.black_hole)
				return 4;
			if(item == ModItems.overfuse)
				return 5;
		}
		
		return 0;
	}

	@Override
	public void updateEntity() {
		if (this.isStructureValid(this.worldObj)) {

			age++;
			if (age >= 20) {
				age = 0;
			}

			if (age == 9 || age == 19)
				ffgeuaInit();

			if (hasFuse() && getSingularityType() > 0 && isStructureValid(worldObj)) {
				if(cooldown) {
					
					int i = getSingularityType();

					if(i == 1)
						cool += 1500;
					if(i == 2)
						cool += 3000;
					if(i == 3)
						cool += 750;
					if(i == 4)
						cool += 7500;
					if(i == 5)
						cool += 150000;
					
					if(cool >= maxCool) {
						cooldown = false;
						cool = maxCool;
					}
					
				} else {
					int i = getSingularityType();
					
					boolean isWorking = false;

					if(i == 1 && amat - 750 >= 0 && aSchrab - 750 >= 0) {
						cool -= 150;
						amat -= 750;
						aSchrab -= 750;
						power += 500000;
						isWorking = true;
					}
					if(i == 2 && amat - 750 >= 0 && aSchrab - 350 >= 0) {
						cool -= 75;
						amat -= 350;
						aSchrab -= 300;
						power += 250000;
						isWorking = true;
					}
					if(i == 3 && amat - 750 >= 0 && aSchrab - 1400 >= 0) {
						cool -= 300;
						amat -= 750;
						aSchrab -= 1400;
						power += 1000000;
						isWorking = true;
					}
					if(i == 4 && amat - 1000 >= 0 && aSchrab - 1000 >= 0) {
						cool -= 100;
						amat -= 1000;
						aSchrab -= 1000;
						power += 1000000;
						isWorking = true;
					}
					if(i == 5 && amat - 150 >= 0 && aSchrab - 150 >= 0) {
						cool -= 150;
						amat -= 150;
						aSchrab -= 150;
						power += 10000000;
						isWorking = true;
					}
					
					if(power > maxPower)
						power = maxPower;
					
					if(cool <= 0) {
						cooldown = true;
						cool = 0;
					}
				}
			}
			
			if(power > maxPower)
				power = maxPower;
			
			power = Library.chargeItemsFromTE(slots, 0, power, maxPower);
			
			
			if(amat + 1000000 <= maxAmat && slots[3] != null && slots[3].getItem() == ModItems.cell_antimatter)
			{
				this.slots[3].stackSize--;
				this.amat += 1000000;
				if(this.slots[3].stackSize == 0)
				{
					this.slots[3] = null;
				}
			}
			if(aSchrab + 1000000 <= maxASchrab && slots[4] != null && slots[4].getItem() == ModItems.cell_anti_schrabidium)
			{
				this.slots[4].stackSize--;
				this.aSchrab += 1000000;
				if(this.slots[4].stackSize == 0)
				{
					this.slots[4] = null;
				}
			}

			if(slots[3] != null && slots[3].getItem() == ModItems.inf_antimatter)
			{
				this.amat = maxAmat;
			}
			if(slots[4] != null && slots[4].getItem() == ModItems.inf_antischrabidium)
			{
				this.aSchrab = maxASchrab;
			}
		}
		
		if(this.isRunning() && (amat <= 0 || aSchrab <= 0 || !hasFuse() || getSingularityType() == 0) || cooldown || !this.isStructureValid(worldObj))
			this.emptyPlasma();
		
		if(!this.isRunning() && amat >= 1000 && aSchrab >= 1000 && hasFuse() && getSingularityType() > 0 && !cooldown && this.isStructureValid(worldObj))
			this.fillPlasma();
	}
	
	public void fillPlasma() {
		if(!this.worldObj.isRemote)
			FWatz.fillPlasma(worldObj, this.xCoord, this.yCoord, this.zCoord);
	}
	
	public void emptyPlasma() {
		if(!this.worldObj.isRemote)
			FWatz.emptyPlasma(worldObj, this.xCoord, this.yCoord, this.zCoord);
	}
	
	public boolean isRunning() {
		return FWatz.getPlasma(worldObj, this.xCoord, this.yCoord, this.zCoord) && this.isStructureValid(worldObj);
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		Block block = this.worldObj.getBlock(x, y, z);
		TileEntity tileentity = this.worldObj.getTileEntity(x, y, z);

		if(block == ModBlocks.factory_titanium_conductor && this.worldObj.getBlock(x, y + 1, z) == ModBlocks.factory_titanium_core)
		{
			tileentity = this.worldObj.getTileEntity(x, y + 1, z);
		}
		if(block == ModBlocks.factory_titanium_conductor && this.worldObj.getBlock(x, y - 1, z) == ModBlocks.factory_titanium_core)
		{
			tileentity = this.worldObj.getTileEntity(x, y - 1, z);
		}
		if(block == ModBlocks.factory_advanced_conductor && this.worldObj.getBlock(x, y + 1, z) == ModBlocks.factory_advanced_core)
		{
			tileentity = this.worldObj.getTileEntity(x, y + 1, z);
		}
		if(block == ModBlocks.factory_advanced_conductor && this.worldObj.getBlock(x, y - 1, z) == ModBlocks.factory_advanced_core)
		{
			tileentity = this.worldObj.getTileEntity(x, y - 1, z);
		}
		
		if(tileentity instanceof IConductor)
		{
			if(tileentity instanceof TileEntityCable)
			{
				if(Library.checkUnionList(((TileEntityCable)tileentity).uoteab, this))
				{
					for(int i = 0; i < ((TileEntityCable)tileentity).uoteab.size(); i++)
					{
						if(((TileEntityCable)tileentity).uoteab.get(i).source == this)
						{
							if(((TileEntityCable)tileentity).uoteab.get(i).ticked != newTact)
							{
								((TileEntityCable)tileentity).uoteab.get(i).ticked = newTact;
								ffgeua(x, y + 1, z, getTact());
								ffgeua(x, y - 1, z, getTact());
								ffgeua(x - 1, y, z, getTact());
								ffgeua(x + 1, y, z, getTact());
								ffgeua(x, y, z - 1, getTact());
								ffgeua(x, y, z + 1, getTact());
							}
						}
					}
				} else {
					((TileEntityCable)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleans(this, newTact));
				}
			}
			if(tileentity instanceof TileEntityWireCoated)
			{
				if(Library.checkUnionList(((TileEntityWireCoated)tileentity).uoteab, this))
				{
					for(int i = 0; i < ((TileEntityWireCoated)tileentity).uoteab.size(); i++)
					{
						if(((TileEntityWireCoated)tileentity).uoteab.get(i).source == this)
						{
							if(((TileEntityWireCoated)tileentity).uoteab.get(i).ticked != newTact)
							{
								((TileEntityWireCoated)tileentity).uoteab.get(i).ticked = newTact;
								ffgeua(x, y + 1, z, getTact());
								ffgeua(x, y - 1, z, getTact());
								ffgeua(x - 1, y, z, getTact());
								ffgeua(x + 1, y, z, getTact());
								ffgeua(x, y, z - 1, getTact());
								ffgeua(x, y, z + 1, getTact());
							}
						}
					}
				} else {
					((TileEntityWireCoated)tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleans(this, newTact));
				}
			}
		}
		
		if(tileentity instanceof IConsumer && newTact && !(tileentity instanceof TileEntityMachineBattery && ((TileEntityMachineBattery)tileentity).conducts))
		{
			list.add((IConsumer)tileentity);
		}
		
		if(!newTact)
		{
			int size = list.size();
			if(size > 0)
			{
				int part = this.power / size;
				for(IConsumer consume : list)
				{
					if(consume.getPower() < consume.getMaxPower())
					{
						if(consume.getMaxPower() - consume.getPower() >= part)
						{
							this.power -= part;
							consume.setPower(consume.getPower() + part);
						} else {
							this.power -= consume.getMaxPower() - consume.getPower();
							consume.setPower(consume.getMaxPower());
						}
					}
				}
			}
			list.clear();
		}
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord + 10, this.yCoord - 11, this.zCoord, getTact());
		ffgeua(this.xCoord - 10, this.yCoord - 11, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 11, this.zCoord + 10, getTact());
		ffgeua(this.xCoord, this.yCoord - 11, this.zCoord - 10, getTact());
	}
	
	public boolean getTact() {
		if(age >= 0 && age < 10)
		{
			return true;
		}
		
		return false;
	}
}
