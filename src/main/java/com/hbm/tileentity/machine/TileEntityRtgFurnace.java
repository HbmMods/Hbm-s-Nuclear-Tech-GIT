package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.MachineRtgFurnace;
import com.hbm.inventory.container.ContainerRtgFurnace;
import com.hbm.inventory.gui.GUIRtgFurnace;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.RTGUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityRtgFurnace extends TileEntity implements ISidedInventory, IGUIProvider {

	private ItemStack slots[];
	
	public int dualCookTime;
	public static final int processingSpeed = 1000;
	
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {4};
	private static final int[] slots_side = new int[] {1, 2, 3};
	
	private String customName;
	
	public TileEntityRtgFurnace() {
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
		return this.hasCustomInventoryName() ? this.customName : "container.rtgFurnace";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
		markDirty();
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
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}
	
	public boolean isLoaded() {
		return RTGUtil.hasHeat(slots, slots_side);
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		dualCookTime = nbt.getShort("CookTime");
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

		customName = nbt.getString("name");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("cookTime", (short) dualCookTime);
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
		
		if (customName != null) {
			nbt.setString("name", customName);
		}
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return j != 0 || i != 1 || itemStack.getItem() == Items.bucket;
	}
	
	public int getDiFurnaceProgressScaled(int i) {
		return (dualCookTime * i) / processingSpeed;
	}
	
	public boolean canProcess() {
		if(slots[0] == null)
		{
			return false;
		}
        ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
		if(itemStack == null)
		{
			return false;
		}
		
		if(slots[4] == null)
		{
			return true;
		}
		
		if(!slots[4].isItemEqual(itemStack)) {
			return false;
		}
		
		if(slots[4].stackSize < getInventoryStackLimit() && slots[4].stackSize < slots[4].getMaxStackSize()) {
			return true;
		}else{
			return slots[4].stackSize < itemStack.getMaxStackSize();
		}
	}
	
	private void processItem() {
		if(canProcess()) {
	        ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
			
			if(slots[4] == null)
			{
				slots[4] = itemStack.copy();
			}else if(slots[4].isItemEqual(itemStack)) {
				slots[4].stackSize += itemStack.stackSize;
			}
			
			for(int i = 0; i < 1; i++)
			{
				if(slots[i].stackSize <= 0)
				{
					slots[i] = new ItemStack(slots[i].getItem().setFull3D());
				}else{
					slots[i].stackSize--;
				}
				if(slots[i].stackSize <= 0)
				{
					slots[i] = null;
				}
			}
		}
	}
	
	public boolean hasPower() {
		return isLoaded();
	}
	
	public boolean isProcessing() {
		return this.dualCookTime > 0;
	}
	
	@Override
	public void updateEntity() {
		this.hasPower();
		boolean flag1 = false;
		
		if(!worldObj.isRemote)
		{
			if(hasPower() && canProcess())
			{
				dualCookTime += RTGUtil.updateRTGs(slots, slots_side);
				
				if(this.dualCookTime >= TileEntityRtgFurnace.processingSpeed)
				{
					this.dualCookTime = 0;
					this.processItem();
					flag1 = true;
				}
			}else{
				dualCookTime = 0;
				RTGUtil.updateRTGs(slots, slots_side);
			}
			
			boolean trigger = true;
			
			if(hasPower() && canProcess() && this.dualCookTime == 0)
			{
				trigger = false;
			}
			
			if(trigger)
            {
                flag1 = true;
                MachineRtgFurnace.updateBlockState(this.dualCookTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
		}
		
		if(flag1)
		{
			this.markDirty();
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRtgFurnace(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRtgFurnace(player.inventory, this);
	}
}
