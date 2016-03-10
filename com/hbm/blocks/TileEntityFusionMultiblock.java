package com.hbm.blocks;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IReactor;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
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
import net.minecraft.world.World;

public class TileEntityFusionMultiblock extends TileEntity implements ISidedInventory, IReactor, ISource {

	public int water;
	public final static int waterMax = 10000000;
	public int deut;
	public final static int deutMax = 10000000;
	public int trit;
	public final static int tritMax = 10000000;
	public int power;
	public final static int maxPower = 10000000;
	private ItemStack slots[];
	public int age = 0;
	public List<IConsumer> list = new ArrayList();
	
	private String customName;

	public TileEntityFusionMultiblock() {
		slots = new ItemStack[9];
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
		
		water = nbt.getShort("water");
		deut = nbt.getShort("deut");
		power = nbt.getShort("power");
		trit = nbt.getShort("trit");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("water", (short) water);
		nbt.setShort("deut", (short) deut);
		nbt.setShort("power", (short) power);
		nbt.setShort("trit", (short) trit);
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
		return false;
	}
	
	@Override
	public int getWaterScaled(int i) {
		return (water * i) / waterMax;
	}
	
	@Override
	public int getCoolantScaled(int i) {
		return (deut * i) / deutMax;
	}
	
	@Override
	public int getHeatScaled(int i) {
		return (trit * i) / tritMax;
	}
	
	@Override
	public int getPowerScaled(int i) {
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
		
		//if(!worldObj.isRemote)
		{
			if(slots[0] != null && slots[0].getItem() == Items.water_bucket && this.water + 250000 <= waterMax)
			{
				this.slots[0].stackSize--;
				this.water += 250000;
				if(this.slots[0].stackSize == 0)
				{
					this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
				}
			}
			if(slots[0] != null && slots[0].getItem() == ModItems.rod_water && this.water + 250000 <= waterMax)
			{
				this.slots[0].stackSize--;
				this.water += 250000;
				if(this.slots[0].stackSize == 0)
				{
					this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
				}
			}
			if(slots[0] != null && slots[0].getItem() == ModItems.rod_dual_water && this.water + 500000 <= waterMax)
			{
				this.slots[0].stackSize--;
				this.water += 500000;
				if(this.slots[0].stackSize == 0)
				{
					this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
				}
			}
			if(slots[0] != null && slots[0].getItem() == ModItems.rod_quad_water && this.water + 1000000 <= waterMax)
			{
				this.slots[0].stackSize--;
				this.water += 1000000;
				if(this.slots[0].stackSize == 0)
				{
					this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
				}
			}

			if(slots[2] != null && slots[2].getItem() == ModItems.cell_deuterium && this.deut + 1000000 <= deutMax)
			{
				this.slots[2].stackSize--;
				this.deut += 1000000;
			}
			if(slots[3] != null && slots[3].getItem() == ModItems.cell_tritium && this.trit + 1000000 <= tritMax)
			{
				this.slots[3].stackSize--;
				this.trit += 1000000;
			}
			
			if(slots[0] != null && slots[0].getItem() == ModItems.inf_water)
			{
				this.water = waterMax;
			}
			if(slots[2] != null && slots[2].getItem() == ModItems.inf_deuterium)
			{
				this.deut = deutMax;
			}
			if(slots[3] != null && slots[3].getItem() == ModItems.inf_tritium)
			{
				this.trit = tritMax;
			}
			
			if(!isRunning() &&
					slots[4] != null && (slots[4].getItem() == ModItems.fusion_core || slots[4].getItem() == ModItems.energy_core) && slots[4].getItemDamage() == 0 &&
					slots[5] != null && (slots[5].getItem() == ModItems.fusion_core || slots[5].getItem() == ModItems.energy_core) && slots[5].getItemDamage() == 0 &&
					slots[6] != null && (slots[6].getItem() == ModItems.fusion_core || slots[6].getItem() == ModItems.energy_core) && slots[6].getItemDamage() == 0 &&
					slots[7] != null && (slots[7].getItem() == ModItems.fusion_core || slots[7].getItem() == ModItems.energy_core) && slots[7].getItemDamage() == 0 &&
					slots[8] != null && slots[8].getItem() == ModItems.fuse &&
					deut > 0 && trit > 0)
			{
				slots[4] = null;
				slots[5] = null;
				slots[6] = null;
				slots[7] = null;
				fillPlasma();
			} else {
				if(isStructureValid(worldObj) && isRunning())
				{
					deut -= 100;
					trit -= 100;
					
					if(water - 1000 >= 0)
					{
						water -= 1000;
						power += 10000;
						
						if(isCoatingValid(worldObj))
						{
							power += 10000;
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
			
			if(deut <= 0 || trit <= 0)
			{
				emptyPlasma();
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
		}
	}
	
	public boolean isRunning() {
		if(slots[8] != null && slots[8].getItem() == ModItems.fuse && (
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
		ffgeua(this.xCoord, this.yCoord + 3, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 3, this.zCoord, getTact());
	}
	
	public boolean getTact() {
		if(age >= 0 && age < 10)
		{
			return true;
		}
		
		return false;
	}

}
