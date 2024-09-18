package com.hbm.tileentity.bomb;

import com.hbm.inventory.container.ContainerNukeGadget;
import com.hbm.inventory.gui.GUINukeGadget;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityNukeGadget extends TileEntity implements ISidedInventory, IGUIProvider {

	private ItemStack slots[];
	private String customName;
	
	public TileEntityNukeGadget() {
		slots = new ItemStack[6];
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
		return this.hasCustomInventoryName() ? this.customName : "container.nukeGadget";
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
		return 1;
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
		return new int[0];
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
	
	/*public int getNukeTier() {
		if(this.slots[0] != null && this.slots[1] != null && this.slots[2] != null && this.slots[3] != null && this.slots[4] != null)
		{
			if(this.slots[0].getItem() == ModItems.test_nuke_tier1_shielding && this.slots[1].getItem() == ModItems.test_nuke_tier1_target && this.slots[2].getItem() == ModItems.test_nuke_tier1_bullet && this.slots[3].getItem() == ModItems.test_nuke_propellant && this.slots[4].getItem() == ModItems.test_nuke_igniter)
			{
				return 1;
			}
			if(this.slots[0].getItem() == ModItems.test_nuke_tier2_shielding && this.slots[1].getItem() == ModItems.test_nuke_tier2_target && this.slots[2].getItem() == ModItems.test_nuke_tier2_bullet && this.slots[3].getItem() == ModItems.test_nuke_propellant && this.slots[4].getItem() == ModItems.test_nuke_igniter)
			{
				return 2;
			}
			if(this.slots[0].getItem() == Item.getItemFromBlock(Blocks.obsidian) && this.slots[1].getItem() == Items.nether_star && this.slots[2].getItem() == Items.diamond && this.slots[3].getItem() == Item.getItemFromBlock(Blocks.tnt) && this.slots[4].getItem() == Items.repeater)
			{
				return 999;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			return 0;
		}
	}*/
	
	public boolean exp1() {
		if(this.slots[1] != null && this.slots[1].getItem() == ModItems.early_explosive_lenses)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean exp2() {
		if(this.slots[2] != null && this.slots[2].getItem() == ModItems.early_explosive_lenses)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean exp3() {
		if(this.slots[3] != null && this.slots[3].getItem() == ModItems.early_explosive_lenses)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean exp4() {
		if(this.slots[4] != null && this.slots[4].getItem() == ModItems.early_explosive_lenses)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean isReady() {
		if(this.exp1() == true && this.exp2() == true && this.exp3() == true && this.exp4() == true)
		{
			if(this.slots[0] != null && this.slots[5] != null && this.slots[0].getItem() == ModItems.gadget_wireing && slots[5].getItem() == ModItems.gadget_core)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void clearSlots() {
		for(int i = 0; i < slots.length; i++)
		{
			slots[i] = null;
		}
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

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerNukeGadget(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUINukeGadget(player.inventory, this);
	}
}
