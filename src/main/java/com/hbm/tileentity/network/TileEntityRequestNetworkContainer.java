package com.hbm.tileentity.network;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.NBTPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * "Multiple inheritance is bad because...uhhhh...i guess if you do it wrong then it can lead to bad things"
 * ~ genuinely retarded people on StackOverflow
 * like yeah, doing things wrong can lead to bad things
 * no shit
 * just like how java operates already
 * you fucking dork
 * 
 * this class has to extend TileEntityRequestNetwork for all the network stuff to work
 * but it also needs slots and all the container boilerplate crap
 * since multiple inheritance is a sin punishable by stoning, i had to cram the entire contents of TileEntityMachineBase into this class
 * is this good code? is this what you wanted? was it worth avoiding those hypothetical scenarios where multiple inheritance is le bad?
 * i believe that neither heaven nor hell awaits me when all is said and done
 * saint peter will send me to southend
 * 
 * @author hbm
 */
public abstract class TileEntityRequestNetworkContainer extends TileEntityRequestNetwork implements ISidedInventory {

	public ItemStack slots[];
	
	private String customName;
	
	public TileEntityRequestNetworkContainer(int scount) {
		slots = new ItemStack[scount];
	}

	@Override public int getSizeInventory() { return slots.length; }
	@Override public ItemStack getStackInSlot(int i) { return slots[i]; }
	@Override public void openInventory() { }
	@Override public void closeInventory() { }
	@Override public boolean isItemValidForSlot(int slot, ItemStack itemStack) { return false; }
	@Override public boolean canInsertItem(int slot, ItemStack itemStack, int side) { return this.isItemValidForSlot(slot, itemStack); }
	@Override public boolean canExtractItem(int slot, ItemStack itemStack, int side) { return false; }
	@Override public int[] getAccessibleSlotsFromSide(int side) { return new int[] { }; }
	
	public void markChanged() {
		this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
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

	@Override public String getInventoryName() { return this.hasCustomInventoryName() ? this.customName : getName(); }
	public abstract String getName();
	@Override public boolean hasCustomInventoryName() { return this.customName != null && this.customName.length() > 0; }
	public void setCustomName(String name) { this.customName = name; }
	@Override public int getInventoryStackLimit() { return 64; }

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 128;
		}
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(slots[slot] != null) {
			
			if(slots[slot].stackSize <= amount) {
				ItemStack itemStack = slots[slot];
				slots[slot] = null;
				return itemStack;
			}
			
			ItemStack itemStack1 = slots[slot].splitStack(amount);
			if(slots[slot].stackSize == 0) {
				slots[slot] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}
	
	public void networkPack(NBTTagCompound nbt, int range) {
		if(!worldObj.isRemote) PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, xCoord, yCoord, zCoord), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
	}
	
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
}
