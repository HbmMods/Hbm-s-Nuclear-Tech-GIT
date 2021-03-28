package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IReactor;
import com.hbm.interfaces.ISource;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.world.machine.FWatz;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityFWatzCore extends TileEntity implements ISidedInventory, IReactor, ISource, IFluidContainer, IFluidAcceptor {

	public long power;
	public final static long maxPower = 10000000000L;
	public boolean cooldown = false;

	public FluidTank tanks[];
	
	Random rand = new Random();
	
	private ItemStack slots[];
	public int age = 0;
	public List<IConsumer> list = new ArrayList();
	
	private String customName;

	public TileEntityFWatzCore() {
		slots = new ItemStack[7];
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(FluidType.COOLANT, 128000, 0);
		tanks[1] = new FluidTank(FluidType.AMAT, 64000, 1);
		tanks[2] = new FluidTank(FluidType.ASCHRAB, 64000, 2);
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

		power = nbt.getLong("power");
		tanks[0].readFromNBT(nbt, "cool");
		tanks[1].readFromNBT(nbt, "amat");
		tanks[2].readFromNBT(nbt, "aschrab");
		
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
		tanks[0].writeToNBT(nbt, "cool");
		tanks[1].writeToNBT(nbt, "amat");
		tanks[2].writeToNBT(nbt, "aschrab");
		
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
		return 0;
	}
	
	@Override
	public long getPowerScaled(long i) {
		return (power/100 * i) / (maxPower/100);
	}
	
	@Override
	public int getWaterScaled(int i) {
		return 0;
	}
	
	@Override
	public int getHeatScaled(int i) {
		return 0;
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
		if (this.isStructureValid(this.worldObj) && !worldObj.isRemote) {

			age++;
			if (age >= 20) {
				age = 0;
			}

			if (age == 9 || age == 19)
				ffgeuaInit();

			if (hasFuse() && getSingularityType() > 0) {
				if(cooldown) {
					
					int i = getSingularityType();

					if(i == 1)
						tanks[0].setFill(tanks[0].getFill() + 1500);
					if(i == 2)
						tanks[0].setFill(tanks[0].getFill() + 3000);
					if(i == 3)
						tanks[0].setFill(tanks[0].getFill() + 750);
					if(i == 4)
						tanks[0].setFill(tanks[0].getFill() + 7500);
					if(i == 5)
						tanks[0].setFill(tanks[0].getFill() + 15000);
					
					if(tanks[0].getFill() >= tanks[0].getMaxFill()) {
						cooldown = false;
						tanks[0].setFill(tanks[0].getMaxFill());
					}
					
				} else {
					int i = getSingularityType();
					
					if(i == 1 && tanks[1].getFill() - 75 >= 0 && tanks[2].getFill() - 75 >= 0) {
						tanks[0].setFill(tanks[0].getFill() - 150);
						tanks[1].setFill(tanks[1].getFill() - 75);
						tanks[2].setFill(tanks[2].getFill() - 75);
						power += 5000000;
					}
					if(i == 2 && tanks[1].getFill() - 75 >= 0 && tanks[2].getFill() - 35 >= 0) {
						tanks[0].setFill(tanks[0].getFill() - 75);
						tanks[1].setFill(tanks[1].getFill() - 35);
						tanks[2].setFill(tanks[2].getFill() - 30);
						power += 2500000;
					}
					if(i == 3 && tanks[1].getFill() - 75 >= 0 && tanks[2].getFill() - 140 >= 0) {
						tanks[0].setFill(tanks[0].getFill() - 300);
						tanks[1].setFill(tanks[1].getFill() - 75);
						tanks[2].setFill(tanks[2].getFill() - 140);
						power += 10000000;
					}
					if(i == 4 && tanks[1].getFill() - 100 >= 0 && tanks[2].getFill() - 100 >= 0) {
						tanks[0].setFill(tanks[0].getFill() - 100);
						tanks[1].setFill(tanks[1].getFill() - 100);
						tanks[2].setFill(tanks[2].getFill() - 100);
						power += 10000000;
					}
					if(i == 5 && tanks[1].getFill() - 15 >= 0 && tanks[2].getFill() - 15 >= 0) {
						tanks[0].setFill(tanks[0].getFill() - 150);
						tanks[1].setFill(tanks[1].getFill() - 15);
						tanks[2].setFill(tanks[2].getFill() - 15);
						power += 100000000;
					}
					
					if(power > maxPower)
						power = maxPower;
					
					if(tanks[0].getFill() <= 0) {
						cooldown = true;
						tanks[0].setFill(0);
					}
				}
			}
			
			if(power > maxPower)
				power = maxPower;
			
			power = Library.chargeItemsFromTE(slots, 0, power, maxPower);
			
			tanks[1].loadTank(3, 5, slots);
			tanks[2].loadTank(4, 6, slots);
			
			for(int i = 0; i < 3; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
		}
		
		if(this.isRunning() && (tanks[1].getFill() <= 0 || tanks[2].getFill() <= 0 || !hasFuse() || getSingularityType() == 0) || cooldown || !this.isStructureValid(worldObj))
			this.emptyPlasma();
		
		if(!this.isRunning() && tanks[1].getFill() >= 100 && tanks[2].getFill() >= 100 && hasFuse() && getSingularityType() > 0 && !cooldown && this.isStructureValid(worldObj))
			this.fillPlasma();

		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
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
		
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord + 10, this.yCoord - 11, this.zCoord, getTact());
		ffgeua(this.xCoord - 10, this.yCoord - 11, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 11, this.zCoord + 10, getTact());
		ffgeua(this.xCoord, this.yCoord - 11, this.zCoord - 10, getTact());
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
		if(index < 3 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		if(index < 3 && tanks[index] != null)
			tanks[index].setTankType(type);
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
		else if(type.name().equals(tanks[2].getTankType().name()))
			tanks[2].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		else if(type.name().equals(tanks[2].getTankType().name()))
			return tanks[2].getFill();
		else
			return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getMaxFill();
		else if(type.name().equals(tanks[2].getTankType().name()))
			return tanks[2].getMaxFill();
		else
			return 0;
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tanks[0]);
		list.add(tanks[1]);
		list.add(tanks[2]);
		
		return list;
	}
}
