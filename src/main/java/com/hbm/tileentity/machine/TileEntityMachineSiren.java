package com.hbm.tileentity.machine;

import java.util.Arrays;

import com.hbm.inventory.container.ContainerMachineSiren;
import com.hbm.inventory.gui.GUIMachineSiren;
import com.hbm.items.machine.ItemCassette;
import com.hbm.items.machine.ItemCassette.SoundType;
import com.hbm.items.machine.ItemCassette.TrackType;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.TESirenPacket;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityMachineSiren extends TileEntity implements ISidedInventory, IGUIProvider {

	private ItemStack slots[];
	
	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0 };
	private static final int[] slots_side = new int[] { 0 };
	
	public boolean lock = false;
	
	private String customName;
	
	public TileEntityMachineSiren() {
		slots = new ItemStack[1];
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
		return this.hasCustomInventoryName() ? this.customName : "container.siren";
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
		
		return false;
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

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			int id = Arrays.asList(TrackType.values()).indexOf(getCurrentType());
			
			if(getCurrentType().name().equals(TrackType.NULL.name())) {
				PacketDispatcher.wrapper.sendToAllAround(new TESirenPacket(xCoord, yCoord, zCoord, id, false), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 1500));
				return;
			}
			
			boolean active = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
			
			if(getCurrentType().getType().name().equals(SoundType.LOOP.name())) {
				
				PacketDispatcher.wrapper.sendToAllAround(new TESirenPacket(xCoord, yCoord, zCoord, id, active), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 1500));
			} else {
				
				if(!lock && active) {
					lock = true;
					PacketDispatcher.wrapper.sendToAllAround(new TESirenPacket(xCoord, yCoord, zCoord, id, false), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 1500));
					PacketDispatcher.wrapper.sendToAllAround(new TESirenPacket(xCoord, yCoord, zCoord, id, true), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 1500));
				}
				
				if(lock && !active) {
					lock = false;
				}
			}
		}
	}
	
	public TrackType getCurrentType() {
		if(slots[0] != null && slots[0].getItem() instanceof ItemCassette) {
			return TrackType.getEnum(slots[0].getItemDamage());
		}
		
		return TrackType.NULL;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineSiren(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineSiren(player.inventory, this);
	}
}
