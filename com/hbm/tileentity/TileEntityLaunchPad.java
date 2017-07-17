package com.hbm.tileentity;

import com.hbm.blocks.bomb.LaunchPad;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEFluidPipePacket;
import com.hbm.packet.TEMissilePacket;

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
import net.minecraft.world.World;

public class TileEntityLaunchPad extends TileEntity implements ISidedInventory, IConsumer {

	public ItemStack slots[];
	
	public int power;
	public final int maxPower = 100000;
	
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {2};
	private static final int[] slots_side = new int[] {1};
	public int state = 0;
	
	public int targetX = this.xCoord + 50;
	public int targetZ = this.zCoord;
	
	private String customName;
	
	public TileEntityLaunchPad() {
		slots = new ItemStack[3];
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
		return this.hasCustomInventoryName() ? this.customName : "container.launchPad";
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
	
	//You scrubs aren't needed for anything (right now)
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		power = nbt.getInteger("power");
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
		nbt.setInteger("power", power);
		
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
		return false;
	}

	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}

	@Override
	public void updateEntity() {

		
		power = Library.chargeTEFromItems(slots, 2, power, maxPower);
		
		ItemStack stack = slots[0];
		
		if(stack != null) {
			if(stack.getItem() == ModItems.missile_generic)
				state = 1;
			if(stack.getItem() == ModItems.missile_strong)
				state = 2;
			if(stack.getItem() == ModItems.missile_cluster)
				state = 3;
			if(stack.getItem() == ModItems.missile_nuclear)
				state = 4;
			if(stack.getItem() == ModItems.missile_incendiary)
				state = 5;
			if(stack.getItem() == ModItems.missile_buster)
				state = 6;
			if(stack.getItem() == ModItems.missile_incendiary_strong)
				state = 7;
			if(stack.getItem() == ModItems.missile_cluster_strong)
				state = 8;
			if(stack.getItem() == ModItems.missile_buster_strong)
				state = 9;
			if(stack.getItem() == ModItems.missile_burst)
				state = 10;
			if(stack.getItem() == ModItems.missile_inferno)
				state = 11;
			if(stack.getItem() == ModItems.missile_rain)
				state = 12;
			if(stack.getItem() == ModItems.missile_drill)
				state = 13;
			if(stack.getItem() == ModItems.missile_endo)
				state = 14;
			if(stack.getItem() == ModItems.missile_exo)
				state = 15;
			if(stack.getItem() == ModItems.missile_nuclear_cluster)
				state = 16;
			
			if(!worldObj.isRemote)
				PacketDispatcher.wrapper.sendToAll(new TEMissilePacket(xCoord, yCoord, zCoord, state));
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	public World getThatWorld() {
		return this.worldObj;
	}

	@Override
	public void setPower(int i) {
		power = i;
		
	}

	@Override
	public int getPower() {
		return power;
		
	}

	@Override
	public int getMaxPower() {
		return maxPower;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

}
