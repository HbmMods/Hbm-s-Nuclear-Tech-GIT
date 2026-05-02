package com.hbm.tileentity.network.pneumatic;

import com.hbm.inventory.container.ContainerPneumoStorageClutter;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIPneumoStorageClutter;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import api.hbm.ntl.ISlotMonitorProvider;
import api.hbm.ntl.SlotMonitor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityPneumoStorageClutter extends TileEntityMachineBase implements IFluidStandardReceiverMK2, ISlotMonitorProvider, IGUIProvider {
	
	public FluidTank compair;
	public SlotMonitor[] monitors;

	public TileEntityPneumoStorageClutter() {
		super(6 * 9);
		this.compair = new FluidTank(Fluids.AIR, 4_000).withPressure(1);
		this.monitors = new SlotMonitor[6 * 9];
		
		for(int i = 0; i < monitors.length; i++) this.monitors[i] = new SlotMonitor(i, this);
	}

	@Override
	public String getName() {
		return "container.pneumoStorageClutter";
	}

	@Override
	public void updateEntity() {
		
	}

	@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return true; }

	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {compair}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {compair}; }

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPneumoStorageClutter(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPneumoStorageClutter(player.inventory, this);
	}

	@Override public SlotMonitor[] getMonitors() { return monitors; }
	@Override public ItemStack getSlotAt(int index) { return this.getStackInSlot(index); }

	@Override
	public boolean isAvailableToTerminal(int termX, int termY, int termZ) {
		return true;
	}
}
