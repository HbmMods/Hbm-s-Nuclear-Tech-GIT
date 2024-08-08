package com.hbm.tileentity.machine;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerDriveProcessor;
import com.hbm.inventory.gui.GUIMachineDriveProcessor;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineDriveProcessor extends TileEntityMachineBase implements IGUIProvider, IControlReceiver {

	public TileEntityMachineDriveProcessor() {
		super(4);
	}

	@Override
	public void updateEntity() {

	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return isUseableByPlayer(player);
	}

	private int getProcessingTier() {
		return 3;
	}

	private void processDrive() {
		if(slots[0] == null || slots[0].getItem() != ModItems.full_drive) return;
		if(ItemVOTVdrive.getProcessed(slots[0])) return;

		// Check that our installed upgrade is a high enough tier
		if(getProcessingTier() >= ItemVOTVdrive.getProcessingTier(slots[0])) {
			ItemVOTVdrive.setProcessed(slots[0], true);
		}
	}

	private void cloneDrive() {
		if(slots[0] == null || slots[0].getItem() != ModItems.full_drive) return;
		if(slots[1] == null || slots[1].getItem() != ModItems.hard_drive) return;

		slots[1] = slots[0].copy();
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("process")) {
			processDrive();
		}

		if(data.hasKey("clone")) {
			cloneDrive();
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerDriveProcessor(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineDriveProcessor(player.inventory, this);
	}

	@Override
	public String getName() {
		return "container.machineDriveProcessor";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
				xCoord - 1,
				yCoord,
				zCoord - 1,
				xCoord + 2,
				yCoord + 1,
				zCoord + 2
			);
		}
		
		return bb;
	}
	
}
