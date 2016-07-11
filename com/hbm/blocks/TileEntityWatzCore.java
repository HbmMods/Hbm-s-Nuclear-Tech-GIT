package com.hbm.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.entity.EntityNukeExplosionAdvanced;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IReactor;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.items.WatzFuel;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityWatzCore extends TileEntity implements ISidedInventory, IReactor, ISource {

	public int waste;
	public final static int wasteMax = 10000000;
	public int power;
	public final static int maxPower = 100000000;
	public int heat;
	
	public int heatMultiplier;
	public int powerMultiplier;
	public int decayMultiplier;
	
	public int heatList;
	public int wasteList;
	public int powerList;
	
	Random rand = new Random();
	
	private ItemStack slots[];
	public int age = 0;
	public List<IConsumer> list = new ArrayList();
	
	private String customName;

	public TileEntityWatzCore() {
		slots = new ItemStack[39];
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
		return this.hasCustomInventoryName() ? this.customName : "container.watzPowerplant";
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

		waste = nbt.getShort("deut") * 1000;
		power = nbt.getShort("power") * 10000;
		
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
		nbt.setShort("deut", (short) (waste/1000));
		nbt.setShort("power", (short) (power/10000));
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
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 3, this.yCoord + i, this.zCoord - 1) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 3, this.yCoord + i, this.zCoord + 1) != ModBlocks.reinforced_brick)
				return false;
		}
		
		
		
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 2, this.yCoord + i, this.zCoord - 2) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 2, this.yCoord + i, this.zCoord - 1) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 2, this.yCoord + i, this.zCoord) != ModBlocks.watz_control)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 2, this.yCoord + i, this.zCoord + 1) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 2, this.yCoord + i, this.zCoord + 2) != ModBlocks.reinforced_brick)
				return false;
		}
		
		
		
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 1, this.yCoord + i, this.zCoord - 3) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 1, this.yCoord + i, this.zCoord - 2) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 1, this.yCoord + i, this.zCoord - 1) != ModBlocks.watz_control)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 1, this.yCoord + i, this.zCoord) != ModBlocks.watz_cooler)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 1, this.yCoord + i, this.zCoord + 1) != ModBlocks.watz_control)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 1, this.yCoord + i, this.zCoord + 2) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 1, this.yCoord + i, this.zCoord + 3) != ModBlocks.reinforced_brick)
				return false;
		}
		
		
		
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 0, this.yCoord + i, this.zCoord - 2) != ModBlocks.watz_control)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 0, this.yCoord + i, this.zCoord - 1) != ModBlocks.watz_cooler)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 0, this.yCoord + i, this.zCoord + 1) != ModBlocks.watz_cooler)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 0, this.yCoord + i, this.zCoord + 2) != ModBlocks.watz_control)
				return false;
		}
		
		
		
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 1, this.yCoord + i, this.zCoord - 3) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 1, this.yCoord + i, this.zCoord - 2) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 1, this.yCoord + i, this.zCoord - 1) != ModBlocks.watz_control)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 1, this.yCoord + i, this.zCoord) != ModBlocks.watz_cooler)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 1, this.yCoord + i, this.zCoord + 1) != ModBlocks.watz_control)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 1, this.yCoord + i, this.zCoord + 2) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 1, this.yCoord + i, this.zCoord + 3) != ModBlocks.reinforced_brick)
				return false;
		}
		
		
		
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 2, this.yCoord + i, this.zCoord - 2) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 2, this.yCoord + i, this.zCoord - 1) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 2, this.yCoord + i, this.zCoord) != ModBlocks.watz_control)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 2, this.yCoord + i, this.zCoord + 1) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 2, this.yCoord + i, this.zCoord + 2) != ModBlocks.reinforced_brick)
				return false;
		}
		
		
		
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 3, this.yCoord + i, this.zCoord - 1) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 3, this.yCoord + i, this.zCoord + 1) != ModBlocks.reinforced_brick)
				return false;
		}
		
		

		for(int i = -5; i <= -1; i++)
		{
			if(world.getBlock(this.xCoord, this.yCoord + i, this.zCoord) != ModBlocks.watz_conductor)
				return false;
		}
		for(int i = 1; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord, this.yCoord + i, this.zCoord) != ModBlocks.watz_conductor)
				return false;
		}

		for(int i = -5; i <= -1; i++)
		{
			if(world.getBlock(this.xCoord + 3, this.yCoord + i, this.zCoord) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = 1; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 3, this.yCoord + i, this.zCoord) != ModBlocks.reinforced_brick)
				return false;
		}

		for(int i = -5; i <= -1; i++)
		{
			if(world.getBlock(this.xCoord - 3, this.yCoord + i, this.zCoord) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = 1; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 3, this.yCoord + i, this.zCoord) != ModBlocks.reinforced_brick)
				return false;
		}

		for(int i = -5; i <= -1; i++)
		{
			if(world.getBlock(this.xCoord, this.yCoord + i, this.zCoord + 3) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = 1; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord, this.yCoord + i, this.zCoord + 3) != ModBlocks.reinforced_brick)
				return false;
		}

		for(int i = -5; i <= -1; i++)
		{
			if(world.getBlock(this.xCoord, this.yCoord + i, this.zCoord - 3) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = 1; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord, this.yCoord + i, this.zCoord - 3) != ModBlocks.reinforced_brick)
				return false;
		}

		if(world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord) != ModBlocks.watz_hatch)
			return false;
		
		if(world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord) != ModBlocks.watz_hatch)
			return false;
		
		if(world.getBlock(this.xCoord, this.yCoord, this.zCoord + 3) != ModBlocks.watz_hatch)
			return false;
		
		if(world.getBlock(this.xCoord, this.yCoord, this.zCoord - 3) != ModBlocks.watz_hatch)
			return false;

		for(int i = -3; i <= 3; i++)
		{
			for(int j = -3; j <= 3; j++)
			{
				if(world.getBlock(this.xCoord + i, this.yCoord + 6, this.zCoord + j) != ModBlocks.watz_end && world.getBlock(this.xCoord + i, this.yCoord + 6, this.zCoord + j) != ModBlocks.watz_conductor)
					return false;
			}
		}
		for(int i = -3; i <= 3; i++)
		{
			for(int j = -3; j <= 3; j++)
			{
				if(world.getBlock(this.xCoord + i, this.yCoord - 6, this.zCoord + j) != ModBlocks.watz_end && world.getBlock(this.xCoord + i, this.yCoord - 6, this.zCoord + j) != ModBlocks.watz_conductor)
					return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean isCoatingValid(World world) {
		{
			return true;
		}
		
		//return false;
	}

	@Override
	public boolean hasFuse() {
		return slots[38] != null && slots[38].getItem() == ModItems.titanium_filter && slots[38].getItemDamage() < slots[38].getMaxDamage();
	}
	
	@Override
	public int getWaterScaled(int i) {
		return (waste * i) / wasteMax;
	}
	
	@Override
	public int getPowerScaled(int i) {
		return (power/100 * i) / (maxPower/100);
	}
	
	@Override
	public int getCoolantScaled(int i) {
		return 0;
	}
	
	@Override
	public int getHeatScaled(int i) {
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

			powerMultiplier = 100;
			heatMultiplier = 100;
			decayMultiplier = 100;
			powerList = 0;
			heatList = 0;
			heat = 0;

			if (hasFuse()) {
				
				//Adds power and heat
				for (int i = 0; i < 36; i++) {
					surveyPellet(slots[i]);
				}
				//Calculates modifiers
				for (int i = 0; i < 36; i++) {
					surveyPelletAgain(slots[i]);
				}
				//Decays pellet by (DECAYMULTIPLIER * DEFAULTDECAY=100)/100 ticks
				for (int i = 0; i < 36; i++) {
					decayPellet(i);
				}
			}

			//Only damages filter when heat is present (thus waste being created)
			if (heatList > 0) {
				slots[38].setItemDamage(slots[38].getItemDamage() + 1);
			}

			heatList *= heatMultiplier;
			heatList /= 100;
			heat = heatList;

			powerList *= powerMultiplier;
			powerList /= 100;
			power += powerList;

			waste += ((decayMultiplier * heat) / 100);
			
			if(power > maxPower)
				power = maxPower;
			
			//Gets rid of 1/4 of the total waste, if at least one access hatch is not occupied
			if(waste > wasteMax)
				emptyWaste();
			
			if(power - 100 >= 0 && slots[37] != null && slots[37].getItem() == ModItems.battery_generic && slots[37].getItemDamage() > 0)
			{
				power -= 100;
				slots[37].setItemDamage(slots[37].getItemDamage() - 1);
			}
			if(power - 100 >= 0 && slots[37] != null && slots[37].getItem() == ModItems.battery_advanced && slots[37].getItemDamage() > 0)
			{
				power -= 100;
				slots[37].setItemDamage(slots[37].getItemDamage() - 1);
			}
			if(power - 100 >= 0 && slots[37] != null && slots[37].getItem() == ModItems.battery_schrabidium && slots[37].getItemDamage() > 0)
			{
				power -= 100;
				slots[37].setItemDamage(slots[37].getItemDamage() - 1);
			}
			if(power - 100 >= 0 && slots[37] != null && slots[37].getItem() == ModItems.factory_core_titanium && slots[37].getItemDamage() > 0)
			{
				power -= 100;
				slots[37].setItemDamage(slots[37].getItemDamage() - 1);
			}
			if(power - 100 >= 0 && slots[37] != null && slots[37].getItem() == ModItems.factory_core_advanced && slots[37].getItemDamage() > 0)
			{
				power -= 100;
				slots[37].setItemDamage(slots[37].getItemDamage() - 1);
			}
			
			if(waste - 2500000 >= 0 && slots[36] != null && slots[36].getItem() == ModItems.tank_waste && slots[36].getItemDamage() < 8)
			{
				waste -= 2500000;
				slots[36].setItemDamage(slots[36].getItemDamage() + 1);
			}
			
			if(waste - 2500000 >= 0 && slots[36] != null && slots[36].getItem() == Items.bucket)
			{
				waste -= 2500000;
				slots[36] = new ItemStack(ModItems.bucket_mud).copy();
			}
			
			if(slots[36] != null && slots[36].getItem() == ModItems.titanium_filter && slots[36].getItemDamage() + 100 <= slots[36].getMaxDamage())
			{
				if(waste - 10000 >= 0)
				{
					waste -= 10000;
					slots[36].setItemDamage(slots[36].getItemDamage() + 100);
				} else {
					if(waste > 0)
					{
						waste = 0;
						slots[36].setItemDamage(slots[36].getItemDamage() + 100);
					}
				}
			}
		}
	}
	
	public void surveyPellet(ItemStack stack) {
		if(stack != null && stack.getItem() instanceof WatzFuel)
		{
			WatzFuel fuel = (WatzFuel)stack.getItem();
			this.powerList += fuel.power;
			this.heatList += fuel.heat;
		}
	}
	
	public void surveyPelletAgain(ItemStack stack) {
		if(stack != null && stack.getItem() instanceof WatzFuel)
		{
			WatzFuel fuel = (WatzFuel)stack.getItem();
			this.powerMultiplier *= fuel.powerMultiplier;
			this.heatMultiplier *= fuel.heatMultiplier;
			this.decayMultiplier *= fuel.decayMultiplier;
		}
	}
	
	public void decayPellet(int i) {
		if(slots[i] != null && slots[i].getItem() instanceof WatzFuel)
		{
			WatzFuel fuel = (WatzFuel)slots[i].getItem();
			slots[i].setItemDamage(slots[i].getItemDamage() + this.decayMultiplier);
			if(slots[i].getItemDamage() >= slots[i].getMaxDamage())
			{
				if(slots[i].getItem() == ModItems.pellet_lead)
					slots[i] = null;
				else
					slots[i] = new ItemStack(ModItems.pellet_lead);
			}
		}
	}
	
	public void emptyWaste() {
		this.waste /= 4;
		this.waste *= 3;
		if (!worldObj.isRemote) {
			if (this.worldObj.getBlock(this.xCoord + 4, this.yCoord, this.zCoord) == Blocks.air)
			{
				this.worldObj.setBlock(this.xCoord + 4, this.yCoord, this.zCoord, ModBlocks.mud_block);
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 3.0F, 0.5F);
			}
			else if (this.worldObj.getBlock(this.xCoord - 4, this.yCoord, this.zCoord) == Blocks.air)
			{
				this.worldObj.setBlock(this.xCoord - 4, this.yCoord, this.zCoord, ModBlocks.mud_block);
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 3.0F, 0.5F);
			}
			else if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord + 4) == Blocks.air)
			{
				this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord + 4, ModBlocks.mud_block);
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 3.0F, 0.5F);
			}
			else if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord - 4) == Blocks.air)
			{
				this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord - 4, ModBlocks.mud_block);
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 3.0F, 0.5F);
			}
			else {
				if (rand.nextInt(10) != 0) {
					for (int i = -3; i <= 3; i++)
						for (int j = -5; j <= 5; j++)
							for (int k = -3; k <= 3; k++)
								if (rand.nextInt(2) == 0)
									this.worldObj.setBlock(this.xCoord + i, this.yCoord + j, this.zCoord + k,
											ModBlocks.mud_block);
					this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.mud_block);
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 3.0F, 0.5F);
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "random.explode", 3.0F, 0.75F);
				} else {
					EntityNukeExplosionAdvanced entity = new EntityNukeExplosionAdvanced(worldObj);
					entity.posX = this.xCoord;
					entity.posY = this.yCoord;
					entity.posZ = this.zCoord;
					entity.destructionRange = MainRegistry.fleijaRadius;
					entity.speed = 25;
					entity.coefficient = 1.0F;
					entity.waste = false;
	    	
					worldObj.spawnEntityInWorld(entity);
				}
			}
		}
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
		ffgeua(this.xCoord, this.yCoord + 7, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 7, this.zCoord, getTact());
	}
	
	public boolean getTact() {
		if(age >= 0 && age < 10)
		{
			return true;
		}
		
		return false;
	}
}
