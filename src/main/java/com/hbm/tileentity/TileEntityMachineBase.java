package com.hbm.tileentity;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.Spaghetti;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTank;

public abstract class TileEntityMachineBase extends TileEntity implements ISidedInventory, INBTPacketReceiver {

	public ItemStack slots[];
	
	private String customName;
	
	public TileEntityMachineBase(int scount) {
		slots = new ItemStack[scount];
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
		return this.hasCustomInventoryName() ? this.customName : getName();
	}
	
	public abstract String getName();

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
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=128;
		}
	}
	
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(slots[slot] != null)
		{
			if(slots[slot].stackSize <= amount)
			{
				ItemStack itemStack = slots[slot];
				slots[slot] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[slot].splitStack(amount);
			if (slots[slot].stackSize == 0)
			{
				slots[slot] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] { };
	}
	
	public int getGaugeScaled(int i, FluidTank tank) {
		return tank.getFluidAmount() * i / tank.getCapacity();
	}

	//abstracting this method forces child classes to implement it
	//so i don't have to remember the fucking method name
	//was it update? onUpdate? updateTile? did it have any args?
	//shit i don't know man
	@Override
    public abstract void updateEntity();
	
	@Deprecated
	public void updateGauge(int val, int id, int range) {

		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, val, id), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
	}

	@Deprecated
	public void processGauge(int val, int id) { }
	
	public void networkPack(NBTTagCompound nbt, int range) {

		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, xCoord, yCoord, zCoord), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
	}
	
	public void networkUnpack(NBTTagCompound nbt) { }
	
	public void handleButtonPacket(int value, int meta) { }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
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
	
	public int countMufflers() {
		
		int count = 0;
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			if(worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) == ModBlocks.muffler)
				count++;
		
		return count;
	}
	
	public float getVolume(int toSilence) {
		
		float volume = 1 - (countMufflers() / (float)toSilence);
		
		return Math.max(volume, 0);
	}
}
