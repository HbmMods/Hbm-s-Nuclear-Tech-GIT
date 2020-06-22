package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
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

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityFusionMultiblock extends TileEntity implements ISidedInventory, IReactor, ISource, IFluidContainer, IFluidAcceptor {

	public long power;
	public final static long maxPower = 100000000;
	private ItemStack slots[];
	public int age = 0;
	public List<IConsumer> list = new ArrayList();
	public FluidTank tanks[];
	
	private String customName;

	public TileEntityFusionMultiblock() {
		slots = new ItemStack[12];
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(FluidType.WATER, 128000, 0);
		tanks[1] = new FluidTank(FluidType.DEUTERIUM, 64000, 1);
		tanks[2] = new FluidTank(FluidType.TRITIUM, 64000, 2);
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
		return this.hasCustomInventoryName() ? this.customName : "container.fusionMultiblock";
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
		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "deut");
		tanks[2].readFromNBT(nbt, "trit");
		
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
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "deut");
		tanks[2].writeToNBT(nbt, "trit");
		
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
		
		//...and I wrote all of this by hand! Ha!
		
		if(world.getBlock(this.xCoord + 5, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 8, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_center &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_motor &&
				
				world.getBlock(this.xCoord + 5, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 8, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_center &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_motor &&
				
				world.getBlock(this.xCoord + 6, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 6, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				
				world.getBlock(this.xCoord + 6, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 6, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				
				world.getBlock(this.xCoord + 8, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 7) == ModBlocks.fusion_heater &&
				
				world.getBlock(this.xCoord + 8, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 7) == ModBlocks.fusion_heater &&

				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) == ModBlocks.fusion_center &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == ModBlocks.fusion_center &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord + 6) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord - 6) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord, this.zCoord + 5) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				
				world.getBlock(this.xCoord + 8, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord, this.zCoord + 0) == ModBlocks.fusion_hatch &&
				world.getBlock(this.xCoord + 8, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord, this.zCoord + 0) == ModBlocks.fusion_hatch &&
				world.getBlock(this.xCoord - 8, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord, this.zCoord + 8) == ModBlocks.fusion_hatch &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord, this.zCoord - 8) == ModBlocks.fusion_hatch &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 7) == ModBlocks.fusion_heater &&

				world.getBlock(this.xCoord, this.yCoord, this.zCoord) == ModBlocks.fusion_core)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isCoatingValid(World world) {
		if(world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord, this.yCoord, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord, this.yCoord, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord - 3) == ModBlocks.block_tungsten &&
				
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord + 0) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord + 0) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord + 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord + 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 0, this.yCoord, this.zCoord + 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord + 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord + 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord - 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord - 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 0, this.yCoord, this.zCoord - 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord - 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord - 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord, this.zCoord - 4) == ModBlocks.block_tungsten)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean hasFuse() {
		return slots[8] != null && (slots[8].getItem() == ModItems.fuse || slots[8].getItem() == ModItems.screwdriver);
	}
	
	@Override
	public int getWaterScaled(int i) {
		return 0;
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
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
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
		
		if(!worldObj.isRemote)
		{
			tanks[0].loadTank(0, 9, slots);
			tanks[1].loadTank(2, 10, slots);
			tanks[2].loadTank(3, 11, slots);
			
			for(int i = 0; i < 3; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			if(slots[2] != null && slots[2].getItem() == ModItems.tritium_deuterium_cake)
			{
				this.slots[2].stackSize--;
				if(this.slots[2].stackSize == 0)
				{
					this.slots[2] = null;
				}

				tanks[1].setFill(tanks[1].getFill() + 10000);
				tanks[2].setFill(tanks[2].getFill() + 10000);
				
				if(tanks[1].getFill() > tanks[1].getMaxFill())
					tanks[1].setFill(tanks[1].getMaxFill());
				
				if(tanks[2].getFill() > tanks[2].getMaxFill())
					tanks[2].setFill(tanks[2].getMaxFill());
			}
			
			if(slots[3] != null && slots[3].getItem() == ModItems.tritium_deuterium_cake)
			{
				this.slots[3].stackSize--;
				if(this.slots[3].stackSize == 0)
				{
					this.slots[3] = null;
				}

				tanks[1].setFill(tanks[1].getFill() + 10000);
				tanks[2].setFill(tanks[2].getFill() + 10000);
				
				if(tanks[1].getFill() > tanks[1].getMaxFill())
					tanks[1].setFill(tanks[1].getMaxFill());
				
				if(tanks[2].getFill() > tanks[2].getMaxFill())
					tanks[2].setFill(tanks[2].getMaxFill());
			}
			
			if(!isRunning() &&
					slots[4] != null && (slots[4].getItem() == ModItems.fusion_core || slots[4].getItem() == ModItems.energy_core) && slots[4].getItemDamage() == 0 &&
					slots[5] != null && (slots[5].getItem() == ModItems.fusion_core || slots[5].getItem() == ModItems.energy_core) && slots[5].getItemDamage() == 0 &&
					slots[6] != null && (slots[6].getItem() == ModItems.fusion_core || slots[6].getItem() == ModItems.energy_core) && slots[6].getItemDamage() == 0 &&
					slots[7] != null && (slots[7].getItem() == ModItems.fusion_core || slots[7].getItem() == ModItems.energy_core) && slots[7].getItemDamage() == 0 &&
					hasFuse() &&
					tanks[1].getFill() > 0 && tanks[2].getFill() > 0)
			{
				slots[4] = null;
				slots[5] = null;
				slots[6] = null;
				slots[7] = null;
				fillPlasma();
			} else {
				if(isStructureValid(worldObj) && isRunning())
				{
					tanks[1].setFill(tanks[1].getFill() - 1);
					tanks[2].setFill(tanks[2].getFill() - 1);
					
					if(tanks[0].getFill() >= 20)
					{
						tanks[0].setFill(tanks[0].getFill() - 20);
						power += 100000;
						
						if(isCoatingValid(worldObj))
						{
							power += 100000;
						}
						
						if(power > maxPower)
						{
							power = maxPower;
						}
					}
					
					fillPlasma();
				} else {
					emptyPlasma();
				}
			}
			
			if(!isRunning())
			{
				emptyPlasma();
			}
			
			if(tanks[1].getFill() <= 0 || tanks[2].getFill() <= 0)
			{
				emptyPlasma();
			}
			
			power = Library.chargeItemsFromTE(slots, 1, power, maxPower);

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
	}
	
	public boolean isRunning() {
		if(hasFuse() && (
				worldObj.getBlock(xCoord + 4, yCoord, zCoord - 3) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 4, yCoord, zCoord - 2) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 4, yCoord, zCoord - 1) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 4, yCoord, zCoord + 0) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 4, yCoord, zCoord + 1) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 4, yCoord, zCoord + 2) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 4, yCoord, zCoord + 3) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 4, yCoord, zCoord - 3) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 4, yCoord, zCoord - 2) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 4, yCoord, zCoord - 1) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 4, yCoord, zCoord + 0) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 4, yCoord, zCoord + 1) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 4, yCoord, zCoord + 2) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 4, yCoord, zCoord + 3) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 3, yCoord, zCoord + 4) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 2, yCoord, zCoord + 4) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 1, yCoord, zCoord + 4) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 0, yCoord, zCoord + 4) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 1, yCoord, zCoord + 4) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 2, yCoord, zCoord + 4) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 3, yCoord, zCoord + 4) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 3, yCoord, zCoord - 4) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 2, yCoord, zCoord - 4) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 1, yCoord, zCoord - 4) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 0, yCoord, zCoord - 4) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 1, yCoord, zCoord - 4) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 2, yCoord, zCoord - 4) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 3, yCoord, zCoord - 4) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 3, yCoord, zCoord + 3) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord + 3, yCoord, zCoord - 3) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 3, yCoord, zCoord + 3) == ModBlocks.plasma ||
				worldObj.getBlock(xCoord - 3, yCoord, zCoord - 3) == ModBlocks.plasma))
		{
			return true;
		}
		return false;
	}
	
	public void fillPlasma() {
		setPlasma(xCoord + 4, yCoord, zCoord - 3);
		setPlasma(xCoord + 4, yCoord, zCoord - 2);
		setPlasma(xCoord + 4, yCoord, zCoord - 1);
		setPlasma(xCoord + 4, yCoord, zCoord + 0);
		setPlasma(xCoord + 4, yCoord, zCoord + 1);
		setPlasma(xCoord + 4, yCoord, zCoord + 2);
		setPlasma(xCoord + 4, yCoord, zCoord + 3);
		setPlasma(xCoord - 4, yCoord, zCoord - 3);
		setPlasma(xCoord - 4, yCoord, zCoord - 2);
		setPlasma(xCoord - 4, yCoord, zCoord - 1);
		setPlasma(xCoord - 4, yCoord, zCoord + 0);
		setPlasma(xCoord - 4, yCoord, zCoord + 1);
		setPlasma(xCoord - 4, yCoord, zCoord + 2);
		setPlasma(xCoord - 4, yCoord, zCoord + 3);
		setPlasma(xCoord - 3, yCoord, zCoord + 4);
		setPlasma(xCoord - 2, yCoord, zCoord + 4);
		setPlasma(xCoord - 1, yCoord, zCoord + 4);
		setPlasma(xCoord + 0, yCoord, zCoord + 4);
		setPlasma(xCoord + 1, yCoord, zCoord + 4);
		setPlasma(xCoord + 2, yCoord, zCoord + 4);
		setPlasma(xCoord + 3, yCoord, zCoord + 4);
		setPlasma(xCoord - 3, yCoord, zCoord - 4);
		setPlasma(xCoord - 2, yCoord, zCoord - 4);
		setPlasma(xCoord - 1, yCoord, zCoord - 4);
		setPlasma(xCoord + 0, yCoord, zCoord - 4);
		setPlasma(xCoord + 1, yCoord, zCoord - 4);
		setPlasma(xCoord + 2, yCoord, zCoord - 4);
		setPlasma(xCoord + 3, yCoord, zCoord - 4);
		setPlasma(xCoord + 3, yCoord, zCoord + 3);
		setPlasma(xCoord + 3, yCoord, zCoord - 3);
		setPlasma(xCoord - 3, yCoord, zCoord + 3);
		setPlasma(xCoord - 3, yCoord, zCoord - 3);
	}
	
	public void emptyPlasma() {
		removePlasma(xCoord + 4, yCoord, zCoord - 3);
		removePlasma(xCoord + 4, yCoord, zCoord - 2);
		removePlasma(xCoord + 4, yCoord, zCoord - 1);
		removePlasma(xCoord + 4, yCoord, zCoord + 0);
		removePlasma(xCoord + 4, yCoord, zCoord + 1);
		removePlasma(xCoord + 4, yCoord, zCoord + 2);
		removePlasma(xCoord + 4, yCoord, zCoord + 3);
		removePlasma(xCoord - 4, yCoord, zCoord - 3);
		removePlasma(xCoord - 4, yCoord, zCoord - 2);
		removePlasma(xCoord - 4, yCoord, zCoord - 1);
		removePlasma(xCoord - 4, yCoord, zCoord + 0);
		removePlasma(xCoord - 4, yCoord, zCoord + 1);
		removePlasma(xCoord - 4, yCoord, zCoord + 2);
		removePlasma(xCoord - 4, yCoord, zCoord + 3);
		removePlasma(xCoord - 3, yCoord, zCoord + 4);
		removePlasma(xCoord - 2, yCoord, zCoord + 4);
		removePlasma(xCoord - 1, yCoord, zCoord + 4);
		removePlasma(xCoord + 0, yCoord, zCoord + 4);
		removePlasma(xCoord + 1, yCoord, zCoord + 4);
		removePlasma(xCoord + 2, yCoord, zCoord + 4);
		removePlasma(xCoord + 3, yCoord, zCoord + 4);
		removePlasma(xCoord - 3, yCoord, zCoord - 4);
		removePlasma(xCoord - 2, yCoord, zCoord - 4);
		removePlasma(xCoord - 1, yCoord, zCoord - 4);
		removePlasma(xCoord + 0, yCoord, zCoord - 4);
		removePlasma(xCoord + 1, yCoord, zCoord - 4);
		removePlasma(xCoord + 2, yCoord, zCoord - 4);
		removePlasma(xCoord + 3, yCoord, zCoord - 4);
		removePlasma(xCoord + 3, yCoord, zCoord + 3);
		removePlasma(xCoord + 3, yCoord, zCoord - 3);
		removePlasma(xCoord - 3, yCoord, zCoord + 3);
		removePlasma(xCoord - 3, yCoord, zCoord - 3);
	}
	
	public void setPlasma(int x, int y, int z) {
		if(worldObj.getBlock(x, y, z) != ModBlocks.plasma)
			worldObj.setBlock(x, y, z, ModBlocks.plasma);
	}
	
	public void removePlasma(int x, int y, int z) {
		if(worldObj.getBlock(x, y, z) == ModBlocks.plasma)
			worldObj.setBlock(x, y, z, Blocks.air);
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord, this.yCoord + 3, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 3, this.zCoord, getTact());
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
		if(type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if(type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
		else if(type.name().equals(tanks[2].getTankType().name()))
			tanks[2].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		else if(type.name().equals(tanks[2].getTankType().name()))
			return tanks[2].getFill();
		else
			return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		else if(type.name().equals(tanks[1].getTankType().name()))
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
