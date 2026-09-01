package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerSatDock;
import com.hbm.inventory.gui.GUISatDock;
import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.SatelliteBase;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineSatDock extends TileEntityMachineBase implements IGUIProvider {
	
	private static final int[] access = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };

	private AxisAlignedBB aabb;

	public TileEntityMachineSatDock() {
		super(16);
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
	}

	@Override public int[] getAccessibleSlotsFromSide(int p_94128_1_) { return access; }
	@Override public boolean isItemValidForSlot(int i, ItemStack itemStack) { return i == 15; }
	@Override public boolean canInsertItem(int i, ItemStack itemStack, int j) { return this.isItemValidForSlot(i, itemStack); }
	@Override public boolean canExtractItem(int i, ItemStack itemStack, int j) { return true; }

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			long time = worldObj.getTotalWorldTime() + BlockPos.getIdentity(xCoord, yCoord, zCoord);

			if(time % 20 == 0 && slots[15] != null && slots[15].getItem() == ModItems.sat_chip) {
				int freq = ISatChip.getFreqS(slots[15]);
				
				SatelliteSavedData data = SatelliteSavedData.getData(worldObj);
				SatelliteBase sat = data.getSatFromFreq(freq);
				
				if(sat != null) sat.tryRequestItems(worldObj, xCoord, yCoord, zCoord);
			}
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(aabb == null) aabb = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 1, zCoord + 2);
		return aabb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerSatDock(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUISatDock(player.inventory, this); }
}
