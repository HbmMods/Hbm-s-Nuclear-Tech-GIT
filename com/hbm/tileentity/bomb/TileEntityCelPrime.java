package com.hbm.tileentity.bomb;

import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityCelPrime extends TileEntity implements ISidedInventory {

	public ItemStack slots[];
	private String customName;
	public String[] cmd;
	private int bootStatus = 0;
	private int bootRequired = 10;
	private int bootDelay;
	
	public TileEntityCelPrime() {
		slots = new ItemStack[2];
		cmd = new String[18];
		bootDelay = 100;
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
		return this.hasCustomInventoryName() ? this.customName : "container.celPrime";
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
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return null;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return j != 0 || i != 1 || itemStack.getItem() == Items.bucket;
	}
	
	@Override
	public void updateEntity() {
		
		if(!hasBooted()) {
			if(bootDelay > 0) {
				bootDelay--;
			} else {
				bootDelay = 100;
				printMsgFromBoot();
				bootStatus++;
			}
		}
	}
	
	private boolean hasBooted() {
		return this.bootStatus == this.bootRequired;
	}
	
	private void printMsgFromBoot() {
		
		switch(bootStatus) {
		case 0:
			appendText("*** Crusader Mainframe Mark 3 ***");
			appendText("(C) 2077 CMC International");
			break;
		case 1:
			appendText("");
			appendText("ReiOS v6.24");
			appendText("64k RAM System");
			appendText("334077 KB free space");
			break;
		case 2:
			appendText("No external PCI devices found!");
			appendText("");
			appendText("Self-test in progress...");
			break;
		case 3:
			appendText("Hardware self-test successful!");
			appendText("");
			appendText("Establishing network uplink...");
			break;
		case 4:
			appendText("Uplink established!");
			appendText("Connecting to TOR circuit...");
			break;
		case 5:
			appendText("Connected nodes: 1/3");
			break;
		case 6:
			appendText("Connected nodes: 2/3");
			break;
		case 7:
			appendText("Connected nodes: 3/3");
			appendText("Connection successful!");
			appendText("");
			appendText("Checking for updates...");
			break;
		case 8:
			appendText("Software already up-to-date.");
			break;
		case 9:
			appendText("All systems ready!");
			appendText("");
			appendText("");
			appendText("");
			appendText("==============================");
			appendText("CEL PRIME is a WIP feature and not");
			appendText("yet functional. please don't ask me how");
			appendText("it works because it DOESN'T!");
			appendText("==============================");
			break;
		}
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
	
	private void fillCmd() {
		
		for(int i = 0; i < cmd.length; i++) {
			if(cmd[i] == null)
				cmd[i] = "";
		}
	}
	
	private void appendText(String text) {

		for(int i = 0; i < cmd.length; i++) {
			if(cmd[i] == null) {
				cmd[i] = text;
				return;
			}
		}

		for(int i = 1; i < cmd.length; i++) {
			cmd[i - 1] = cmd[i];
		}
		
		cmd[cmd.length - 1] = text;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
