package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.interfaces.IReactor;
import com.hbm.interfaces.ISource;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.special.WatzFuel;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityWatzCore extends TileEntity implements ISidedInventory, IReactor, ISource, IFluidContainer, IFluidSource {

	public long power;
	public final static long maxPower = 100000000;
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
	public List<IFluidAcceptor> list1 = new ArrayList();
	public FluidTank tank;
	
	private String customName;

	public TileEntityWatzCore() {
		slots = new ItemStack[40];
		tank = new FluidTank(FluidType.WATZ, 64000, 0);
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

		power = nbt.getLong("power");
		tank.readFromNBT(nbt, "watz");
		
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
		
		nbt.setLong("power", power);
		tank.writeToNBT(nbt, "watz");
		
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
		return 0;
	}
	
	@Override
	public long getPowerScaled(long i) {
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
		if (this.isStructureValid(this.worldObj) && !worldObj.isRemote) {

			age++;
			if (age >= 20) {
				age = 0;
			}

			if (age == 9 || age == 19) {
				ffgeuaInit();
				fillFluidInit(tank.getTankType());
			}

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

			tank.setFill(tank.getFill() + ((decayMultiplier * heat) / 100) / 100);
			
			if(power > maxPower)
				power = maxPower;
			
			//Gets rid of 1/4 of the total waste, if at least one access hatch is not occupied
			if(tank.getFill() > tank.getMaxFill())
				emptyWaste();
			
			power = Library.chargeItemsFromTE(slots, 37, power, maxPower);
			
			tank.updateTank(xCoord, yCoord, zCoord);
			tank.unloadTank(36, 39, slots);
			
			if(slots[36] != null && slots[36].getItem() == ModItems.titanium_filter && slots[36].getItemDamage() + 100 <= slots[36].getMaxDamage())
			{
				if(tank.getFill() - 10 >= 0)
				{
					tank.setFill(tank.getFill() - 10);
					slots[36].setItemDamage(slots[36].getItemDamage() + 100);
				} else {
					if(tank.getFill() > 0)
					{
						tank.setFill(0);
						slots[36].setItemDamage(slots[36].getItemDamage() + 100);
					}
				}
			}

			PacketDispatcher.wrapper.sendToAll(new AuxElectricityPacket(xCoord, yCoord, zCoord, power));
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
			WatzFuel.setLifeTime(slots[i], WatzFuel.getLifeTime(slots[i]) + this.decayMultiplier);
			WatzFuel.updateDamage(slots[i]);
			if(WatzFuel.getLifeTime(slots[i]) >= fuel.lifeTime)
			{
				if(slots[i].getItem() == ModItems.pellet_lead)
					slots[i] = new ItemStack(ModItems.powder_lead);
				else
					slots[i] = new ItemStack(ModItems.pellet_lead);
			}
		}
	}
	
	public void emptyWaste() {
		tank.setFill(tank.getFill() / 4);
		tank.setFill(tank.getFill() * 3);
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
					EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(worldObj);
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
		
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord, this.yCoord + 7, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 7, this.zCoord, getTact());
	}
	
	@Override
	public boolean getTact() {
		if(age >= 0 && age < 10)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
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

	@Override
	public void setFillstate(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tank.setTankType(type);
	}
	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 4, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord - 4, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 4, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 4, getTact(), type);
		
	}
	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	@Override
	public int getSFluidFill(FluidType type) {
		return tank.getFill();
	}
	@Override
	public void setSFluidFill(int i, FluidType type) {
		tank.setFill(i);
	}
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list1;
	}
	@Override
	public void clearFluidList(FluidType type) {
		list1.clear();
	}
}
