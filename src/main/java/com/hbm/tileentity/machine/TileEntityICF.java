package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerICF;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIICF;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityICF extends TileEntityMachineBase implements IGUIProvider, IFluidStandardTransceiver {
	
	public FluidTank[] tanks;

	public TileEntityICF() {
		super(12);
		this.tanks = new FluidTank[3];
		this.tanks[0] = new FluidTank(Fluids.COOLANT, 256_000);
		this.tanks[1] = new FluidTank(Fluids.COOLANT_HOT, 256_000);
		this.tanks[2] = new FluidTank(Fluids.STELLAR_FLUX, 16_000);
	}

	@Override
	public String getName() {
		return "container.machineICF";
	}

	@Override
	public void updateEntity() {
		
		for(int i = 0; i < 3; i++) tanks[i].setFill(tanks[i].getMaxFill());
		
		if(!worldObj.isRemote) {
			
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot < 5;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot > 5;
	}

	public static final int[] io = new int[] {0, 1, 2, 3, 4, 6, 7, 8, 9, 10};

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return io;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 256;
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord + 0.5 - 8,
					yCoord,
					zCoord + 0.5 - 8,
					xCoord + 0.5 + 9,
					yCoord + 0.5 + 5,
					zCoord + 0.5 + 9
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[1], tanks[2]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0]};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerICF(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIICF(player.inventory, this);
	}
}
