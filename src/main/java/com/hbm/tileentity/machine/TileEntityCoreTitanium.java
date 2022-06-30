package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IFactory;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCoreTitanium extends TileEntityLoadedBase implements ISidedInventory, IFactory, IEnergyUser {
	
	public int progress = 0;
	public long power = 0;
	public int soundCycle = 0;
	public final static int processTime = 200;
	public final static int maxPower = (int)((ItemBattery)ModItems.factory_core_titanium).getMaxCharge();
	private ItemStack slots[];
	
	private String customName;

	public TileEntityCoreTitanium() {
		slots = new ItemStack[23];
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
		return this.hasCustomInventoryName() ? this.customName : "container.factoryTitanium";
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
		return new int[0];
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
		
		this.progress = nbt.getShort("cookTime");
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
		nbt.setShort("cookTime", (short) progress);
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

	@Spaghetti("2016 bobcode *shudders*")
	@Override
	public boolean isStructureValid(World world) {
		if(world.getBlock(this.xCoord, this.yCoord, this.zCoord) == ModBlocks.factory_titanium_core &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord) == ModBlocks.factory_titanium_hull &&
				(world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == ModBlocks.factory_titanium_conductor || world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == ModBlocks.factory_titanium_hull) &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord + 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord, this.yCoord, this.zCoord - 1) == ModBlocks.factory_titanium_furnace &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord) == ModBlocks.factory_titanium_furnace &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord) == ModBlocks.factory_titanium_furnace &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord, this.yCoord, this.zCoord + 1) == ModBlocks.factory_titanium_furnace &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord) == ModBlocks.factory_titanium_hull &&
				(world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) == ModBlocks.factory_titanium_conductor || world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) == ModBlocks.factory_titanium_hull) &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord + 1) == ModBlocks.factory_titanium_hull &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 1) == ModBlocks.factory_titanium_hull)
		{
			return true;
		}
		return false;
	}

	@Override
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	@Override
	public int getProgressScaled(int i) {
		return (progress * i) / processTime;
	}
	
	@Override
	public boolean isProcessable(ItemStack item) {
		if(item != null)
		{
			return FurnaceRecipes.smelting().getSmeltingResult(item) != null;
		} else {
			return false;
		}
	}

	@Override
	public void updateEntity() { }
	
	@Override
	public void setPower(long i) { }
	
	@Override
	public long getPower() {
		return power;
	}
	@Override
	public long getMaxPower() {
		return maxPower;
	}

}
